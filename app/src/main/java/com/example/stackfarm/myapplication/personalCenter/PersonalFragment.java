package com.example.stackfarm.myapplication.personalCenter;

import android.content.Intent;
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
import com.example.stackfarm.myapplication.logining.LoginActivity;

public class PersonalFragment extends Fragment implements View.OnClickListener {
    private int FragmentPage;
    private TextView comment;
    private TextView settings;
    private TextView login;

    public  static  PersonalFragment newInstance(int iFragmentPage){
        PersonalFragment myFragment = new PersonalFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(FragmentPage,container,false);
        comment=(TextView)view.findViewById(R.id.commentTxt);
        comment.setOnClickListener(this);
        settings=(TextView)view.findViewById(R.id.settingsTxt);
        settings.setOnClickListener(this);
        login=(TextView)view.findViewById(R.id.loginTxt);
        login.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commentTxt:
                Intent intent=new Intent(getActivity(),CommentActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsTxt:
                Intent intent1=new Intent(getActivity(),SettingsActivity.class);
                startActivity(intent1);
                break;
            case R.id.loginTxt:
                Intent intent2=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
