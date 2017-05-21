package com.ks.hihi.haha.map;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.ks.hihi.haha.R;
import com.ks.hihi.haha.detail.DetailActivity;
import com.ks.hihi.haha.items.BaseObj;
import com.ks.hihi.haha.items.Item;
import com.ks.hihi.haha.request.RequestList;
import com.ks.hihi.haha.request.RequestOne;

import java.util.List;

import retrofit2.Call;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, ClusterManager.OnClusterClickListener<Item>, ClusterManager.OnClusterInfoWindowClickListener<Item>, ClusterManager.OnClusterItemClickListener<Item>, ClusterManager.OnClusterItemInfoWindowClickListener<Item>, GoogleMap.OnMyLocationButtonClickListener, View.OnClickListener {

    private final static int MY_LOCATION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private ClusterManager<Item> mClusterManager;
    private UiSettings uiSettings;
    private Context context;

    private ImageButton btnBack;

    private Button btnLoss;
    private Button btnFind;

    private LatLng centerLocation;
    private int FLAG_LOSS_OR_FIND = 0;  //0 = loss, 1 = find

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        context = getApplicationContext();


        btnLoss = (Button) findViewById(R.id.btn_loss);
        btnLoss.setOnClickListener(this);
        //btnLoss.performClick();

        btnFind = (Button) findViewById(R.id.btn_find);
        btnFind.setOnClickListener(this);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setToggleBtn();

        //gps check
        chkGpsService();

    }
    
    private void reloadMap(){

        mMap.clear();
        mClusterManager.clearItems();

        getMarkerData();

        mClusterManager.cluster();
    }

    private void setUpClusterer(GoogleMap googleMap) {

        mClusterManager = new ClusterManager<Item>(this, googleMap);

        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        getMarkerData();
    }

    private void getMarkerData(){
        new AsyncTask<Void, Void, List<BaseObj>>() {
            @Override
            protected List<BaseObj> doInBackground(Void... params) {
                List<BaseObj> result = null;
                try {
                    RequestList.selectList service = RequestList.retrofitHttp.create(RequestList.selectList.class);
                    Call<List<BaseObj>> call = null;

                    if(FLAG_LOSS_OR_FIND == 0){
                        call = service.createTask("loss");
                    } else {
                        call = service.createTask("find");
                    }

                    result = call.execute().body();

                    //for (BaseObj mo : result) {
                    for(int i=0; i<result.size(); i++){
                        mClusterManager.addItem(new Item(result.get(i).get_id(), result.get(i).getLat(), result.get(i).getLng() ));
                    }

                } catch (Exception e) {
                    Log.d("Request Error", e.toString());
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<BaseObj> result) {
                mClusterManager.cluster();
            }
        }.execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap = googleMap;
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition arg0) {
                centerLocation = arg0.target;
            }
        });
        uiSettings = mMap.getUiSettings();

        uiSettings.setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

        setUpClusterer(googleMap);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(35.13843153258832, 129.09894332315264))
                .zoom(15)
                .bearing(0)
                .tilt(30)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onClusterClick(Cluster<Item> cluster) {
        Toast.makeText(this, cluster.getSize() + "개들어있으여", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Item> cluster) {

    }

    @Override
    public boolean onClusterItemClick(final Item item) {
        new AsyncTask<Void, Void, BaseObj>() {
            @Override
            protected BaseObj doInBackground(Void... params) {
                RequestOne.selectOne service = RequestOne.retrofitHttp.create(RequestOne.selectOne.class);
                Call<BaseObj> call = null;

                BaseObj result = null;

                try {

                    if(FLAG_LOSS_OR_FIND == 0){
                        call = service.createTask("loss", item.getKey());
                    } else {
                        call = service.createTask("find", item.getKey());
                    }
                    result = call.execute().body();
                    //Toast.makeText(context, result.getLoss_title(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.d("Request Error", e.toString());
                }
                return result;
            }

            @Override
            protected void onPostExecute(BaseObj result) {
                loadDialog(result);
            }
        }.execute();

        return false;
    }

    private void loadDialog(final BaseObj item){
        BottomDialog bottomDialog = new BottomDialog.Builder(this)
                .setTitle(item.getTitle())
                .setContent(item.getProcess())
                //.setIcon(R.mipmap.ic_launcher)

                .setNegativeText("뒤로가기")
                .setNegativeTextColorResource(R.color.colorPrimary)

                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        dialog.dismiss();
                    }
                })

                .setPositiveText("자세히보기")
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(R.color.colorWhite)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        Intent it = new Intent(context, DetailActivity.class);
                        it.putExtra("ITEM", item);
                        startActivity(it);
                    }
                }).build();

        bottomDialog.show();
    }

    @Override
    public void onClusterItemInfoWindowClick(Item item) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 && Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Have not permissions!!!", Toast.LENGTH_LONG).show();
                } else {
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMyLocationButtonClickListener(this);
                }
            } else {
                Toast.makeText(this, "Have not permissions!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean chkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("위치 서비스 설정");
            gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    }).create().show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        chkGpsService();
        return false;
    }

    private void setToggleBtn(){
        if(FLAG_LOSS_OR_FIND == 0){
            btnLoss.setSelected(true);
            btnFind.setSelected(false);
        }else {
            btnLoss.setSelected(false);
            btnFind.setSelected(true);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnBack.getId()){
            this.finish();
        } else if(v.getId() == btnLoss.getId()){
            FLAG_LOSS_OR_FIND = 0;
            setToggleBtn();
            reloadMap();
        } else if(v.getId() == btnFind.getId()){
            FLAG_LOSS_OR_FIND = 1;
            setToggleBtn();
            reloadMap();
        }
    }

}
