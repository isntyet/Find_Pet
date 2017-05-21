package com.ks.hihi.haha.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ks.hihi.haha.R;
import com.ks.hihi.haha.list.ListActivity;
import com.ks.hihi.haha.map.MapActivity;
import com.ks.hihi.haha.regist.FindRegistActivity;
import com.ks.hihi.haha.regist.LossRegistActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout llFindInMap = null;
    private LinearLayout llFindInList = null;
    private LinearLayout llLossRegist = null;
    private LinearLayout llFindRegist = null;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        llFindInMap = (LinearLayout) findViewById(R.id.ll_find_map);
        llFindInMap.setOnClickListener(this);

        llFindInList = (LinearLayout) findViewById(R.id.ll_find_list);
        llFindInList.setOnClickListener(this);

        llLossRegist = (LinearLayout) findViewById(R.id.ll_loss_regist);
        llLossRegist.setOnClickListener(this);

        llFindRegist = (LinearLayout) findViewById(R.id.ll_find_regist);
        llFindRegist.setOnClickListener(this);




        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == llFindInMap.getId()){
            startActivity(new Intent(this, MapActivity.class));
        } else if(v.getId() == llFindInList.getId()){
            Intent intent = new Intent(this, ListActivity.class);
            intent.putExtra(ListActivity.EXTRA_TYPE, 0);
            startActivity(intent);
        } else if(v.getId() == llLossRegist.getId()){
            //찾기 등록
            startActivity(new Intent(this, LossRegistActivity.class));
        } else if(v.getId() == llFindRegist.getId()){
            //찾아주기 등록
            startActivity(new Intent(this, FindRegistActivity.class));
        }
    }

    //폰트 먹일때
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
//    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
