package com.ks.hihi.haha.regist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ks.hihi.haha.R;
import com.ks.hihi.haha.items.Code;
import com.ks.hihi.haha.request.RequestList;
import com.ks.hihi.haha.utill.CustomScrollView;
import com.ks.hihi.haha.utill.SysUtill;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

/**
 * Created by jojo on 2017. 5. 21..
 */



public class LossRegistActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, OnMapReadyCallback, GoogleMap.OnMapClickListener{

    private CustomScrollView svWrap;
    private ImageButton btnBack;

    private MaterialEditText etTitle;

    private LinearLayout llSex;
    private TextView tvSex;

    private LinearLayout llAge;
    private TextView tvAge;

    private LinearLayout llDate;
    private TextView tvDate;

    private ImageView ivImage;
    private Button btnImage;

    private MaterialEditText etColor;
    private MaterialEditText etFeature;
    private MaterialEditText etProcess;

    private Button btnKind1;
    private Button btnKind2;

    private String KIND_CODE = "";
    private String KIND_DETAIL_CODE = "";
    private int PICK_IMAGE_REQUEST = 1;

    private Uri filePath;
    private Bitmap bitmap = null;

    private TextView tvPlace;
    private MaterialEditText etPlaceDetial;

    private GoogleMap mMap = null;
    private UiSettings uiSettings;
    private Geocoder geocoder;

    private CardView cvRegist;

    private Context context;

    private LatLng pointLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_regist);

        context = getApplicationContext();

        svWrap = (CustomScrollView) findViewById(R.id.sv_wrap);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        etTitle = (MaterialEditText) findViewById(R.id.et_title);

        llSex = (LinearLayout) findViewById(R.id.ll_sex);
        llSex.setOnClickListener(this);

        tvSex = (TextView) findViewById(R.id.tv_sex);

        llAge = (LinearLayout) findViewById(R.id.ll_age);
        llAge.setOnClickListener(this);

        tvAge = (TextView) findViewById(R.id.tv_age);

        llDate = (LinearLayout) findViewById(R.id.ll_date);
        llDate.setOnClickListener(this);

        tvDate = (TextView) findViewById(R.id.tv_date);

        ivImage = (ImageView) findViewById(R.id.iv_image);
        btnImage = (Button) findViewById(R.id.btn_image);
        btnImage.setOnClickListener(this);
        //ivImage.setOnClickListener(this);

        etColor = (MaterialEditText) findViewById(R.id.et_color);
        etFeature = (MaterialEditText) findViewById(R.id.et_feature);
        etProcess = (MaterialEditText) findViewById(R.id.et_process);

        btnKind1 = (Button) findViewById(R.id.btn_kind_1);
        btnKind1.setOnClickListener(this);

        btnKind2 = (Button) findViewById(R.id.btn_kind_2);
        btnKind2.setOnClickListener(this);

        tvPlace = (TextView) findViewById(R.id.tv_place);
        etPlaceDetial = (MaterialEditText) findViewById(R.id.et_place_detail);

        cvRegist = (CardView) findViewById(R.id.cv_regist);
        cvRegist.setOnClickListener(this);

        geocoder = new Geocoder(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnBack.getId()){
            this.finish();
        } else if(v.getId() == llSex.getId()){
            loadSexDialog();
        } else if(v.getId() == llAge.getId()){
            loadAgeDialog();
        } else if(v.getId() == llDate.getId()){
            loadCanlenderDialog();
        } else if(v.getId() == btnImage.getId()){
            showFileChooser();
        } else if(v.getId() == btnKind1.getId()){
            loadKindFisrtDialog();
        } else if(v.getId() == btnKind2.getId()){
            loadKindSecond();
        } else if(v.getId() == cvRegist.getId()){
            doRegist();
        }
    }

    private void doRegist(){

        String title = etTitle.getText().toString();
        String sex = tvSex.getText().toString();
        String age = tvAge.getText().toString();
        String date = tvDate.getText().toString();
        String place = tvPlace.getText().toString();
        String place_datail = etPlaceDetial.getText().toString();

        Bitmap img = this.bitmap;
        String color = etColor.getText().toString();
        String kind = KIND_CODE;
        String kind_detail = KIND_DETAIL_CODE;
        String feature = etFeature.getText().toString();
        String process = etProcess.getText().toString();

        String lat = SysUtill.doubleToStr(pointLocation.latitude);
        String lng = SysUtill.doubleToStr(pointLocation.longitude);

        String reg_date = SysUtill.getCurrentTodatTime();


        if("".equals(title) || title == null){
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_LONG).show();
            etTitle.requestFocus();
            return;
        }

        if("선택".equals(sex) || "".equals(sex) || sex == null){
            Toast.makeText(this, "성별을 선택해 주세요.", Toast.LENGTH_LONG).show();
            svWrap.smoothScrollTo(tvSex.getLeft(), tvSex.getTop());
            return;
        }

        if("선택".equals(age) || "".equals(age) || age == null){
            Toast.makeText(this, "나이를 선택해 주세요.", Toast.LENGTH_LONG).show();
            svWrap.smoothScrollTo(tvAge.getLeft(), tvAge.getTop());
            return;
        }

        if("선택".equals(date) || "".equals(date) || date == null){
            Toast.makeText(this, "발생시간을 선택해 주세요.", Toast.LENGTH_LONG).show();
            svWrap.smoothScrollTo(tvDate.getLeft(), tvDate.getTop());
            return;
        }

        //여기서 등록 행위하셈


    }

    private void loadKindFisrtDialog(){
        final ArrayList<Code> list = new ArrayList<Code>();
        list.add(new Code("강아지", "dog"));
        list.add(new Code("고양이", "cat"));

        CodeAdapter adapter = new CodeAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        btnKind1.setText(list.get(position).getKind());
                        btnKind2.setText("선택");
                        KIND_CODE = list.get(position).getKind_num();
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setMargin(150, 160, 150, 160)
                .setExpanded(false)
                .create();
        dialog.show();
    }

    private void loadKindSecond(){
        if(!"".equals(KIND_CODE)) {
            try{
                new AsyncTask<Void, Void, ArrayList<Code>>() {
                    @Override
                    protected ArrayList<Code> doInBackground(Void... params) {
                        RequestList.selectList service = RequestList.retrofitHttp.create(RequestList.selectList.class);
                        Call<ArrayList<Code>> call = null;

                        ArrayList<Code> result = null;

                        try {
                            call = service.createCodeListTask(KIND_CODE);
                            result = call.execute().body();
                        } catch (Exception e) {
                            Log.d("Request Error", e.toString());
                        }
                        return result;
                    }

                    @Override
                    protected void onPostExecute(final ArrayList<Code> result) {
                        loadKindSecondDialog(result);
                    }
                }.execute();

            }catch (Exception e){
                Log.d("request error", e.toString());
            }
        } else {
            Toast.makeText(context, "상위 구분을 먼저 선택해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    private void loadKindSecondDialog(final ArrayList<Code> list){
        CodeAdapter adapter = new CodeAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        btnKind2.setText(list.get(position).getKind());
                        KIND_DETAIL_CODE = list.get(position).getKind_num();
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setMargin(150, 160, 150, 160)
                .setExpanded(false)
                .create();
        dialog.show();

    }

    private void loadSexDialog(){
        final ArrayList<String> list = new ArrayList<String>();
        list.add("남(수컷)");
        list.add("여(암컷)");

        BaseAdapter adapter = new BaseAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        tvSex.setText(list.get(position));
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setMargin(150, 160, 150, 160)
                .setExpanded(false)
                .create();
        dialog.show();
    }

    private void loadAgeDialog(){
        final ArrayList<String> list = new ArrayList<String>();
        list.add("아기");
        list.add("보통");
        list.add("늙음");
        list.add("1~3");
        list.add("4~6");
        list.add("7~10");
        list.add("11~14");
        list.add("15~20");
        list.add("20이상");

        BaseAdapter adapter = new BaseAdapter(this, list);

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(adapter)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        tvAge.setText(list.get(position));
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.CENTER)
                .setMargin(170, 160, 170, 160)
                .setExpanded(false)
                .create();
        dialog.show();
    }

    private void loadCanlenderDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LossRegistActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String strMonth = "";
        String strDay = "";

        monthOfYear++;
        strMonth = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;
        strDay = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;

        String date = "" + year + strMonth + strDay;

        tvDate.setText(year + "-" + strMonth + "-" + strDay);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();

                bitmap = resizeBitmapImageFn(bitmap, 1024);

                ivImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap resizeBitmapImageFn(Bitmap bmpSource, int maxResolution){
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(bmpSource, newWidth, newHeight, true);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(SysUtill.strToDouble("35.13774116370102"), SysUtill.strToDouble("129.10133904673413")))
                .zoom(17)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        pointLocation = latLng;
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);

        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);

        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (list != null) {
                if (list.size() == 0) {
                    tvPlace.setText("해당되는 주소 정보는 없습니다");
                } else {
                    tvPlace.setText(list.get(0).getAddressLine(0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
