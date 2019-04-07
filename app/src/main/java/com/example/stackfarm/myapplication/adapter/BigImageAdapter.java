package com.example.stackfarm.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.bean.BigImageBean;

import java.util.List;

public class BigImageAdapter extends RecyclerView.Adapter<BigImageAdapter.ViewHolder>{
    private List<BigImageBean> list;

    public BigImageAdapter(List<BigImageBean> list) {
        this.list = list;
    }
    @NonNull
    @Override
    public BigImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_big_image,viewGroup,false);
        BigImageAdapter.ViewHolder viewHolder=new BigImageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BigImageBean bean=list.get(position);
        holder.imageView.setImageResource(bean.getImageID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.item_big_img);
        }
    }

}
