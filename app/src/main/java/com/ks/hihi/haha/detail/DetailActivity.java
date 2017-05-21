package com.ks.hihi.haha.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ks.hihi.haha.R;
import com.ks.hihi.haha.items.BaseObj;
import com.ks.hihi.haha.main.GlobalApplication;
import com.ks.hihi.haha.utill.Config;
import com.ks.hihi.haha.utill.SysUtill;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by jo on 2017-05-06.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private Context context;
    private BaseObj item;
    private NetworkImageView nivImage;
    private ImageButton btnBack;

    private ImageLoader imageLoader = GlobalApplication.getGlobalApplicationContext().getImageLoader();

    private TextView tvInsertDate;
    private TextView tvUserName;
    private TextView tvTitle;
    private TextView tvSex;
    private TextView tvDate;
    private TextView tvColor;
    private TextView tvKind;
    private TextView tvFeature;
    private TextView tvProcess;
    private TextView tvPlace;


    private GoogleMap mMap;
    private UiSettings uiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        context = getApplicationContext();

        item = (BaseObj) getIntent().getSerializableExtra("ITEM");

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        nivImage = (NetworkImageView) findViewById(R.id.niv_image);
        nivImage.setDefaultImageResId(R.drawable.img_default);
        nivImage.setErrorImageResId(R.drawable.img_default);
        String imgUrl = Config.IMG_BASE_RUL + "/images/find/" + item.getImg_std();
        nivImage.setImageUrl(imgUrl, imageLoader);


        tvInsertDate = (TextView) findViewById(R.id.tv_insert_date);
        tvInsertDate.setText("작성일 : " + item.getInsert_date());

        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvUserName.setText("작성자 : " + item.getUser_name());

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(item.getTitle());

        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvSex.setText(item.getSex());

        tvDate = (TextView) findViewById(R.id.tv_date);
        tvDate.setText(item.getDate());

        tvColor = (TextView) findViewById(R.id.tv_color);
        tvColor.setText(item.getColor());

        tvKind = (TextView) findViewById(R.id.tv_kind);
        tvKind.setText(item.getKind() + " / " + item.getKind_detail());

        tvFeature = (TextView) findViewById(R.id.tv_feature);
        tvFeature.setText(item.getFeature());

        tvProcess = (TextView) findViewById(R.id.tv_process);
        tvProcess.setText(item.getProcess());

        tvPlace = (TextView) findViewById(R.id.tv_place);
        tvPlace.setText(item.getPlace() + " " + item.getPlace_detail());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnBack.getId()){
            this.finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.addMarker(new MarkerOptions().position(new LatLng(SysUtill.strToDouble(item.getLat()), SysUtill.strToDouble(item.getLng()))).title("Marker"));

        uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setScrollGesturesEnabled(false);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(SysUtill.strToDouble(item.getLat()), SysUtill.strToDouble(item.getLng())))
                .zoom(17)
                .bearing(0)
                .tilt(0)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
