package com.example.stackfarm.myapplication.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.stackfarm.myapplication.MainActivity;
import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.TubatuAdapter;
import com.example.stackfarm.myapplication.guiding.GuideActivity;
import com.example.stackfarm.myapplication.utils.ClipViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private ImageButton shops;

    private int FragmentPage;

    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;

    private TextView cainixihuan;
    private TextView tuijianmendian;
    private TextView mendiandingwei;
    private TextView celianxing;
    private TextView shifaxin;
    private TextView faxingsheji;

    public  static HomeFragment newInstance(int iFragmentPage){
        HomeFragment myFragment = new HomeFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(FragmentPage,container,false);

        mViewPager = (ClipViewPager) view.findViewById(R.id.viewpager);
        /**调节ViewPager的滑动速度**/
        mViewPager.setSpeedScroller(300);

        /**给ViewPager设置缩放动画，这里通过PageTransformer来实现**/
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        List<String> strList = Arrays.asList("one", "two", "three", "four");

        /**
         * 需要将整个页面的事件分发给ViewPager，不然的话只有ViewPager中间的view能滑动，其他的都不能滑动，
         * 这是肯定的，因为ViewPager总体布局就是中间那一块大小，其他的子布局都跑到ViewPager外面来了
         */
        view.findViewById(R.id.page_container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mPagerAdapter = new TubatuAdapter(getActivity(),strList);
        mViewPager.setAdapter(mPagerAdapter);
        initData();

        shops=(ImageButton) view.findViewById(R.id.shops);
        shops.setOnClickListener(this);

        Typeface mTypeface=Typeface.createFromAsset(view.getContext().getAssets(),"fonts/wryh.ttf");
        cainixihuan=(TextView)view.findViewById(R.id.cainixihuan);
        tuijianmendian=(TextView)view.findViewById(R.id.tuijianmendian);
        mendiandingwei=(TextView)view.findViewById(R.id.mendiandingwei);
        celianxing=(TextView)view.findViewById(R.id.celianxing);
        faxingsheji=(TextView)view.findViewById(R.id.faxingsheji);
        shifaxin=(TextView)view.findViewById(R.id.shifaxin);
        celianxing.setTypeface(mTypeface);
        cainixihuan.setTypeface(mTypeface);
        tuijianmendian.setTypeface(mTypeface);
        mendiandingwei.setTypeface(mTypeface);
        shifaxin.setTypeface(mTypeface);
        faxingsheji.setTypeface(mTypeface);
        TextPaint tp = tuijianmendian.getPaint();
        tp.setFakeBoldText(true);
        tp=cainixihuan.getPaint();
        tp.setFakeBoldText(true);

        return view;
    }
    private void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.one);
        list.add(R.mipmap.two);
        list.add(R.mipmap.three);
        list.add(R.mipmap.four);
        /**这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常；**/
        mViewPager.setOffscreenPageLimit(list.size());
        mPagerAdapter.addAll(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shops:
                Intent mIntent=new Intent(getActivity(), GuideActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivityForResult(mIntent, 1, ActivityOptions.makeSceneTransitionAnimation(getActivity() ).toBundle());
                }
                break;

            default:
                break;
        }
    }
}

