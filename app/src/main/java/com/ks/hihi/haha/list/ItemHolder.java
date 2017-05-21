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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ks.hihi.haha.R;
import com.ks.hihi.haha.items.ItemCard;
import com.ks.hihi.haha.main.GlobalApplication;
import com.ks.hihi.haha.utill.Config;


/**
 * Created by sharish on 27/12/16.
 */

public class ItemHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView;
    private TextView mDescView;
    private NetworkImageView mThumbnailView;
    private TextView mSummaryView;
    private String key;

    public LinearLayout layoutItem;

    private ImageLoader imageLoader = GlobalApplication.getGlobalApplicationContext().getImageLoader();

    public static ItemHolder newInstance(ViewGroup container, int type) {

        View root = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_news_card, container, false);
        return new ItemHolder(root);
    }

    private ItemHolder(View itemView) {
        super(itemView);
        layoutItem = (LinearLayout) itemView.findViewById(R.id.card_item);;
        mTitleView = (TextView) itemView.findViewById(R.id.card_title);
        mDescView = (TextView) itemView.findViewById(R.id.card_subtitle);
        mSummaryView = (TextView) itemView.findViewById(R.id.card_summary);
        //mThumbnailView = (ImageView) itemView.findViewById(R.id.card_image);
        mThumbnailView = (NetworkImageView) itemView.findViewById(R.id.card_image);
    }

    public void bind(ItemCard card) {

        key = card.getKey();
        mTitleView.setText(card.getTitle());
        mDescView.setText(card.getPlace());
        mSummaryView.setText(card.getFeature());

        mThumbnailView.setDefaultImageResId(R.drawable.img_default);
        mThumbnailView.setErrorImageResId(R.drawable.img_default);
        String imgUrl = Config.IMG_BASE_RUL + "/images/find/" + card.getImg_std();
        mThumbnailView.setImageUrl(imgUrl, imageLoader);
        //Glide.with(itemView.getContext()).load(card.getImg_std()).into(mThumbnailView);
    }

    public String getKey(){
        return key;
    }
}
