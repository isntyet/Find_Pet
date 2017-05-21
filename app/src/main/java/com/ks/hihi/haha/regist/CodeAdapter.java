package com.ks.hihi.haha.regist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ks.hihi.haha.R;
import com.ks.hihi.haha.items.Code;

import java.util.ArrayList;

/**
 * Created by jo on 2017-05-19.
 */

public class CodeAdapter extends android.widget.BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Code> list;


    public CodeAdapter(Context context, ArrayList<Code> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseAdapter.ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_base_list, parent, false);

            viewHolder = new BaseAdapter.ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
//            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (BaseAdapter.ViewHolder) view.getTag();
        }

        Context context = parent.getContext();

        viewHolder.textView.setText(list.get(position).getKind());

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
