package com.example.stackfarm.myapplication.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.BarberAdapter;
import com.example.stackfarm.myapplication.bean.BarberBean;

import java.util.ArrayList;
import java.util.List;

public class HairDesignActivity extends AppCompatActivity {

    List<BarberBean> list2=new ArrayList<>();
    RecyclerView recyclerView2;
    BarberAdapter adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hair_design);
        initBarbers();
    }

    private void initBarbers(){
        BarberBean one=new BarberBean("Tony", R.mipmap.barber1,"38.0","18.0","58.0");
        BarberBean two=new BarberBean("首席设计师",R.mipmap.barber2,"38.0","18.0","58.0");
        BarberBean three=new BarberBean("Tony", R.mipmap.barber1,"38.0","18.0","58.0");
        BarberBean four=new BarberBean("首席设计师",R.mipmap.barber2,"38.0","18.0","58.0");


        list2.add(one);
        list2.add(two);
        list2.add(three);
        list2.add(four);

        recyclerView2= findViewById(R.id.design_hair_view);
        adapter2=new BarberAdapter(list2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(adapter2);
    }
}
