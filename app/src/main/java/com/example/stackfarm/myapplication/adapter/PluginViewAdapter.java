package com.example.stackfarm.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.bean.PluginBean;

import java.util.List;

public class PluginViewAdapter extends RecyclerView.Adapter<PluginViewAdapter.ViewHolder> {
    private List<PluginBean> list;

    public PluginViewAdapter(List<PluginBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PluginViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_plugin,viewGroup,false);
        PluginViewAdapter.ViewHolder viewHolder=new PluginViewAdapter.ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PluginBean bean=list.get(position);
        holder.imageView.setImageResource(bean.getImageId());
        holder.textView.setText(bean.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.item_plug_img);
            textView=itemView.findViewById(R.id.item_plug_txt);
        }
    }

    public interface OnitemClickListener{
        void onItemClick(int position);
    }
}
