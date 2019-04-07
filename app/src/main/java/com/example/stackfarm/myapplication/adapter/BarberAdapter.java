package com.example.stackfarm.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.bean.BarberBean;

import java.util.List;

public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.ViewHolder>{
    private List<BarberBean> list;

    public BarberAdapter(List<BarberBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_barber,viewGroup,false);
        BarberAdapter.ViewHolder viewHolder=new BarberAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BarberBean bean=list.get(position);
        holder.imageView.setImageResource(bean.getImageID());
        holder.name.setText(bean.getName());
        holder.tag.setText(bean.getTag());
        holder.exp.setText("接单： "+bean.getOrderNums()+" 评论"+bean.getCommentNums());
        holder.money1.setText("￥"+bean.getMoney1());
        holder.money2.setText("￥"+bean.getMoney2());
        holder.money3.setText("￥"+bean.getMoney3());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tag;
        TextView exp;
        TextView name;
        TextView money1;
        TextView money2;
        TextView money3;
        ViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.item_barber_img);
            name=itemView.findViewById(R.id.item_barber_name);
            tag=itemView.findViewById(R.id.item_barber_tag);
            exp=itemView.findViewById(R.id.item_barber_exp);
            money1=itemView.findViewById(R.id.item_barber_money1);
            money2=itemView.findViewById(R.id.item_barber_money2);
            money3=itemView.findViewById(R.id.item_barber_money3);
        }
    }
}
