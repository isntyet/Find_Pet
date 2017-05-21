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

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ks.hihi.haha.detail.DetailActivity;
import com.ks.hihi.haha.items.BaseObj;
import com.ks.hihi.haha.items.ItemCard;
import com.ks.hihi.haha.request.RequestList;
import com.ks.hihi.haha.request.RequestOne;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


/**
 * Created by sharish on 27/12/16.
 */

public class CardAdapter extends RecyclerView.Adapter<ItemHolder> {
    private ArrayList<ItemCard> mCards = new ArrayList<ItemCard>();
    private int mType = 0;
    private Context context;
    public ArrayList<ItemCard> itemList = new ArrayList<ItemCard>();

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return ItemHolder.newInstance(parent, mType);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {

        holder.bind(mCards.get(position));

        ((ItemHolder) holder).layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, BaseObj>() {
                    @Override
                    protected BaseObj doInBackground(Void... params) {
                        RequestOne.selectOne service = RequestOne.retrofitHttp.create(RequestOne.selectOne.class);
                        Call<BaseObj> call = null;

                        BaseObj result = null;

                        try {
                            call = service.createTask("loss", mCards.get(position).getKey());
                            /*if(FLAG_LOSS_OR_FIND == 0){
                                call = service.createTask("loss", mCards[position].getKey());
                            } else {
                                call = service.createTask("find", item.getKey());
                            }*/
                            result = call.execute().body();
                            //Toast.makeText(context, result.getLoss_title(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.d("Request Error", e.toString());
                        }
                        return result;
                    }

                    @Override
                    protected void onPostExecute(BaseObj result) {
                        Intent it = new Intent(context, DetailActivity.class);
                        it.putExtra("ITEM", result);
                        context.startActivity(it);
                    }
                }.execute();

            }
        });

        if(position == getItemCount() - 1) {

            new AsyncTask<Void, Void, List<BaseObj>>() {
                @Override
                protected List<BaseObj> doInBackground(Void... params) {
                    List<BaseObj> result = null;
                    try {
                        RequestList.selectList service = RequestList.retrofitHttp.create(RequestList.selectList.class);
                        Call<List<BaseObj>> call = null;
                        call = service.createListTask("loss",mCards.size() -1);
                        /*if(FLAG_LOSS_OR_FIND == 0){
                            call = service.createTask("loss");
                        } else {
                            call = service.createTask("find");
                        }*/

                        result = call.execute().body();
                    } catch (Exception e) {
                        Log.d("Request Error", e.toString());
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(List<BaseObj> result) {
                    //itemList = new ItemCard[result.size()];

                    for(int i=0; i<result.size(); i++){
                        ItemCard itemcard = new ItemCard(result.get(i).getTitle(), result.get(i).getPlace(), result.get(i).getFeature(), result.get(i).getImg_std(), result.get(i).get_id());
                        mCards.add(itemcard);
                    }
                }
            }.execute();
        }

    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public void setCards(ArrayList<ItemCard> cards) {
        if (cards == null) {

        }
        mCards = cards;
    }


    public void setType(int type) {
        this.mType = type;
    }



}
