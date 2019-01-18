package com.example.stackfarm.myapplication.collector;

import com.example.stackfarm.myapplication.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<BaseActivity>activities=new ArrayList<>();
    public static void addActivity(BaseActivity activity){
            activities.add(activity);
    }
    public static void removeActivity(BaseActivity activity){
        activities.remove(activity);
        activity.finish();
        if(activities.size()==0)activities.clear();
    }

}
