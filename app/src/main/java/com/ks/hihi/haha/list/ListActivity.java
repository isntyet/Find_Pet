/**
 * Copyright 2017 Harish Sridharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ks.hihi.haha.list;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.ks.hihi.haha.R;
import com.ks.hihi.haha.items.BaseObj;
import com.ks.hihi.haha.items.ItemCard;
import com.ks.hihi.haha.request.RequestList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static android.R.attr.type;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_TYPE = "type";
    private ShimmerRecyclerView shimmerRecycler;
    private CardAdapter mAdapter;
    private ImageButton btnBack;

    public int FLAG_LOSS_OR_FIND = 0;  //0 = loss, 1 = find
    public ArrayList<ItemCard> itemList = new ArrayList<ItemCard>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager layoutManager = null;

        setContentView(R.layout.activity_list);
        layoutManager = new LinearLayoutManager(this);

        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view);


        mAdapter = new CardAdapter();
        mAdapter.setType(type);

        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(mAdapter);
        shimmerRecycler.showShimmerAdapter();
        shimmerRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCards();
            }
        }, 2000);

    }

    private void loadCards() {

        new AsyncTask<Void, Void, List<BaseObj>>() {
            @Override
            protected List<BaseObj> doInBackground(Void... params) {
                List<BaseObj> result = null;
                try {
                    RequestList.selectList service = RequestList.retrofitHttp.create(RequestList.selectList.class);
                    Call<List<BaseObj>> call = null;

                    if(FLAG_LOSS_OR_FIND == 0){
                        call = service.createListTask("loss", 0);
                    } else {
                        call = service.createTask("find");
                    }

                    result = call.execute().body();
                } catch (Exception e) {
                    Log.d("Request Error", e.toString());
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<BaseObj> result) {
                for(int i=0; i<result.size(); i++){
                    ItemCard itemcard = new ItemCard(result.get(i).getTitle(), result.get(i).getPlace(), result.get(i).getFeature(), result.get(i).getImg_std(), result.get(i).get_id());
                    itemList.add(itemcard);
                }
                mAdapter.setCards(itemList);
                shimmerRecycler.hideShimmerAdapter();
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnBack.getId()){
            this.finish();
        }
    }
}
