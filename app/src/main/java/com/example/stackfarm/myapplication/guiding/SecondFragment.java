package com.example.stackfarm.myapplication.guiding;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.TubatuAdapter;
import com.example.stackfarm.myapplication.utils.ClipViewPager;
import com.example.stackfarm.myapplication.utils.ScalePageTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondFragment extends Fragment implements View.OnClickListener {

    private int FragmentPage;

    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;

    private Button reserve2;

    public  static SecondFragment newInstance(int iFragmentPage){
        SecondFragment myFragment = new SecondFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(FragmentPage,container,false);
        mViewPager = (ClipViewPager)view.findViewById(R.id.viewpager2);
        /**调节ViewPager的滑动速度**/
        mViewPager.setSpeedScroller(300);

        /**给ViewPager设置缩放动画，这里通过PageTransformer来实现**/
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        List<String> strList = Arrays.asList("one", "two", "three", "four");

        /**
         * 需要将整个页面的事件分发给ViewPager，不然的话只有ViewPager中间的view能滑动，其他的都不能滑动，
         * 这是肯定的，因为ViewPager总体布局就是中间那一块大小，其他的子布局都跑到ViewPager外面来了
         */
        view.findViewById(R.id.page_container2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mPagerAdapter = new TubatuAdapter(view.getContext(),strList);
        mViewPager.setAdapter(mPagerAdapter);
        initData();

        reserve2=(Button)view.findViewById(R.id.reserve2);
        reserve2.setOnClickListener(this);
        return view;
    }

    private void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.barber2);
        /**这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常；**/
        mViewPager.setOffscreenPageLimit(list.size());
        mPagerAdapter.addAll(list);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reserve2:
                Intent intent =new Intent(getContext(),ReserveActivity.class);
                intent.putExtra("name","2");
                startActivity(intent);
            default:
                break;
        }
    }
}
