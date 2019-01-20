package com.example.stackfarm.myapplication.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    private int FragmentPage;

    public  static HomeFragment newInstance(int iFragmentPage){
        HomeFragment myFragment = new HomeFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(FragmentPage,container,false);
        return view;
    }
}
