package com.example.stackfarm.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.bean.CommunityBean;

import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder>{

    private List<CommunityBean> list;

    public CommunityAdapter(List<CommunityBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_community,viewGroup,false);
        CommunityAdapter.ViewHolder viewHolder=new CommunityAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CommunityBean bean=list.get(i);
        viewHolder.imageView.setImageResource(bean.getImageID());
        viewHolder.name.setText(bean.getName());
        viewHolder.time.setText(bean.getTime());
        viewHolder.loc.setText(bean.getLoc());
        viewHolder.content.setText(bean.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView time;
        TextView loc;
        TextView content;
        ViewHolder (View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.item_community_img);
            name=itemView.findViewById(R.id.item_community_name);
            time=itemView.findViewById(R.id.item_community_time);
            loc=itemView.findViewById(R.id.item_community_loc);
            content=itemView.findViewById(R.id.item_community_content);
        }
    }
}
