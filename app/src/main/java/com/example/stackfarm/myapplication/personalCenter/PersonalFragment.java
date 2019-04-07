package com.example.stackfarm.myapplication.personalCenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stackfarm.myapplication.R;

public class PersonalFragment extends Fragment {
    private int FragmentPage;

    public  static  PersonalFragment newInstance(int iFragmentPage){
        PersonalFragment myFragment = new PersonalFragment();
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
