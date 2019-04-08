package com.example.stackfarm.myapplication.community;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.CommunityAdapter;
import com.example.stackfarm.myapplication.bean.CommunityBean;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private int FragmentPage;

    CommunityAdapter adapter;
    List<CommunityBean> list=new ArrayList<>();
    RecyclerView recyclerView;

    public  static CommunityFragment newInstance(int iFragmentPage){
        CommunityFragment myFragment = new CommunityFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(FragmentPage,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        CommunityBean one=new CommunityBean(R.mipmap.a_m,"小花花","4/6 2:34 2019","金陵科技学院","穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷");
        CommunityBean two=new CommunityBean(R.mipmap.a_m,"小草草","4/3 21:34 2019","金陵科技学院","穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷");
        CommunityBean three=new CommunityBean(R.mipmap.a_m,"李鹏飞","4/3 1:21 2019","金陵科技学院","穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷穷");
        list.add(one);
        list.add(two);
        list.add(three);

        recyclerView=view.findViewById(R.id.community_view);

        adapter=new CommunityAdapter(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
