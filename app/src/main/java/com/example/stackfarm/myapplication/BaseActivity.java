package com.example.stackfarm.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.stackfarm.myapplication.collector.ActivityCollector;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);


    }
    public boolean equals(Object obj) {
        if(this.getClass()==obj.getClass())return true;
        else return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.removeActivity(this);
    }
}
