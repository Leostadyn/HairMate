package com.example.stackfarm.myapplication.community;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunityFragment extends Fragment {

    private int FragmentPage;

    public  static CommunityFragment newInstance(int iFragmentPage){
        CommunityFragment myFragment = new CommunityFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(FragmentPage,container,false);
        return view;
    }
}
