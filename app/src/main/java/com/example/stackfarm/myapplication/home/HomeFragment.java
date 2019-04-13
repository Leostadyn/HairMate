package com.example.stackfarm.myapplication.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.BarberAdapter;
import com.example.stackfarm.myapplication.adapter.BigImageAdapter;
import com.example.stackfarm.myapplication.adapter.TubatuAdapter;
import com.example.stackfarm.myapplication.bean.BarberBean;
import com.example.stackfarm.myapplication.bean.BigImageBean;
import com.example.stackfarm.myapplication.design.HairDesignActivity;
import com.example.stackfarm.myapplication.faceshape.FaceActivity;
import com.example.stackfarm.myapplication.guiding.GuideActivity;
import com.example.stackfarm.myapplication.trail.TrailHairstyle;
import com.example.stackfarm.myapplication.utils.BottomSelectorPopDialog;
import com.example.stackfarm.myapplication.utils.ClipViewPager;
import com.example.stackfarm.myapplication.utils.ScalePageTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private ImageButton shops;
    private ImageButton shifaxing;
    private ImageButton faxingsheji;
    private ImageButton celianxing;

    private int FragmentPage;

    final static int CAMERA_RESULT_CODE=1;
    final static int CROP_RESULT_CODE=2;
    final static int ALBUM_RESULT_CODE=3;
    final static int REQUEST_PERMISSIONS=4;

    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;

    RecyclerView recyclerView;
    BigImageAdapter adapter;
    List<BigImageBean> list=new ArrayList<>();

    RecyclerView recyclerView2;
    BarberAdapter adapter2;
    List<BarberBean> list2=new ArrayList<>();

    private TextView cainixihuan;
    private TextView tuijianmendian;

    public  static HomeFragment newInstance(int iFragmentPage){
        HomeFragment myFragment = new HomeFragment();
        myFragment.FragmentPage = iFragmentPage;
        return  myFragment;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(FragmentPage,container,false);

        initBigImages(view);
        initBarbers(view);

        mViewPager = (ClipViewPager)view.findViewById(R.id.viewpager4);
        /**调节ViewPager的滑动速度**/
        mViewPager.setSpeedScroller(300);

        /**给ViewPager设置缩放动画，这里通过PageTransformer来实现**/
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        List<String> strList = Arrays.asList("one", "two", "three","four");

        /**
         * 需要将整个页面的事件分发给ViewPager，不然的话只有ViewPager中间的view能滑动，其他的都不能滑动，
         * 这是肯定的，因为ViewPager总体布局就是中间那一块大小，其他的子布局都跑到ViewPager外面来了
         */
        view.findViewById(R.id.page_container4).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mPagerAdapter = new TubatuAdapter(view.getContext(),strList);
        mViewPager.setAdapter(mPagerAdapter);
        initData();

        shops=(ImageButton) view.findViewById(R.id.shops);
        shops.setOnClickListener(this);

        shifaxing=(ImageButton)view.findViewById(R.id.shifaxing) ;
        shifaxing.setOnClickListener(this);

        faxingsheji=(ImageButton)view.findViewById(R.id.sheji);
        faxingsheji.setOnClickListener(this);

        celianxing=(ImageButton)view.findViewById(R.id.lianxing);
        celianxing.setOnClickListener(this);


//        设置字体
        setFonts(view);

        return view;
    }

    private void setFonts(View view){
        cainixihuan=(TextView)view.findViewById(R.id.cainixihuan);
        tuijianmendian=(TextView)view.findViewById(R.id.tuijianmendian);
        TextPaint tp = tuijianmendian.getPaint();
        tp.setFakeBoldText(true);
        tp=cainixihuan.getPaint();
        tp.setFakeBoldText(true);
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
                }else{
                    startActivity(mIntent);
                }
                break;
            case R.id.shifaxing:
                String[] list = {"拍照","相册"};
                popupSelectOperation(list);  //弹框显示选项
                break;

            case R.id.sheji:
                Intent intent=new Intent(getActivity(), HairDesignActivity.class);
                startActivity(intent);
                break;

            case R.id.lianxing:
                Intent intent1=new Intent(getActivity(), FaceActivity.class);
                startActivity(intent1);
                break;

            default:
                break;
        }
    }

    private void backgroundAlpha(float b) {
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = b;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    private void initBigImages(View view){
        BigImageBean one=new BigImageBean(R.mipmap.oneb);
        BigImageBean two=new BigImageBean(R.mipmap.twob);
        BigImageBean three=new BigImageBean(R.mipmap.threeb);

        list.add(one);
        list.add(two);
        list.add(three);
        recyclerView=view.findViewById(R.id.big_image_view);
        adapter=new BigImageAdapter(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    private void initBarbers(View view){
        BarberBean one=new BarberBean("Tony",R.mipmap.barber1,"38.0","18.0","58.0");
        BarberBean two=new BarberBean("首席设计师",R.mipmap.barber2,"38.0","18.0","58.0");

        list2.add(one);
        list2.add(two);

        recyclerView2=view.findViewById(R.id.barber_view);
        adapter2=new BarberAdapter(list2);
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(adapter2);
    }

    public BottomSelectorPopDialog mBottomSelectorPopDialog;
    private void popupSelectOperation(String[] list) {
        backgroundAlpha(0.8f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBottomSelectorPopDialog = new BottomSelectorPopDialog(getActivity(), list, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSelectorPopDialog.dismiss();
                    switch (v.getId()) {
                        case R.id.tv_action_one:
                            Log.d("photo","tv_action_one");
                            Intent cameraIntent=new Intent(getContext(),TrailHairstyle.class);
                            cameraIntent.putExtra("TRAIL","camera");
                            startActivity(cameraIntent);
                            break;
                        case R.id.tv_action_two:
                            Log.d("photo","tv_action_two");
                            Intent albumIntent = new Intent(getContext(), TrailHairstyle.class);
                            albumIntent.putExtra("TRAIL","album");
                            startActivity(albumIntent);


                            break;
                        case R.id.tv_cancel_show:
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        //显示窗口消失后让背景恢复
        mBottomSelectorPopDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        mBottomSelectorPopDialog.showAtLocation(getActivity().findViewById(R.id.container), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }
}

