package com.example.stackfarm.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TubatuAdapter extends RecyclingPagerAdapter {

    private List<Integer> mList;
    private Context mContext;
    private List<String> strList;

    public TubatuAdapter(Context context,List<String> strList) {
        mList = new ArrayList<>();
        mContext = context;
        this.strList = strList;

    }

    public void addAll(List<Integer> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        ImageView imageView = null;
        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setTag(position);
        imageView.setImageResource(mList.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG",""+strList.get(position));
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
