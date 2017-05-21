package com.ks.hihi.haha.items;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.ks.hihi.haha.utill.SysUtill;

/**
 * Created by jo on 2017-04-22.
 */

public class Item implements ClusterItem {
    private String key;
    private LatLng position;

    public Item(String key, String lat, String lng) {
        this.key = key;
        position = new LatLng(SysUtill.strToDouble(lat), SysUtill.strToDouble(lng));
//        mParkerOption = new MarkerOptions();
//        mParkerOption.position(mPosition).title(name);
    }

    public String getKey(){
        return key;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }
}