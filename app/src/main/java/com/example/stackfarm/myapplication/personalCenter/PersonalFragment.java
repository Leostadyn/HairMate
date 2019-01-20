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

    private TextView nickTag;
    private Button ord;
    private Button his;
    private Button fav;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(FragmentPage,container,false);
        int num=0;
        nickTag =view.findViewById(R.id.nickname_tag);
        ord=view.findViewById(R.id.order);
        his=view.findViewById(R.id.his);
        fav=view.findViewById(R.id.fav);

        nickTag.setText("昵称："+"\n"+"\n"+"个性签名：");
        ord.setText(num+"\n"+"订单");
        his.setText(num+"\n"+"历史");
        fav.setText(num+"\n"+"收藏");

        return view;
    }
}
