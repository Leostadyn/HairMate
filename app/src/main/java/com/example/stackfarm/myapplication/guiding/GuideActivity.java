package com.example.stackfarm.myapplication.guiding;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.TubatuAdapter;
import com.example.stackfarm.myapplication.home.HomeFragment;
import com.example.stackfarm.myapplication.home.ScalePageTransformer;
import com.example.stackfarm.myapplication.personalCenter.PersonalFragment;
import com.example.stackfarm.myapplication.utils.ClipViewPager;
import com.yinglan.scrolllayout.ScrollLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private PersonalFragment personalFragment;
    private HomeFragment homeFragment;

    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;
    public LocationClient mLocationClient;
    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1700L);
            getWindow().setEnterTransition(fade);
        }

        setContentView(R.layout.guiding);

        mapView=(MapView)findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        List<String >permissionList=new ArrayList<>();

        if(ContextCompat.checkSelfPermission(GuideActivity.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(GuideActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(GuideActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[]permissions=permissionList.toArray(new String [permissionList.size()]);
            ActivityCompat.requestPermissions(GuideActivity.this,permissions,1);
        }else{
            requestLocation();
        }

        mViewPager = (ClipViewPager)findViewById(R.id.viewpager1);
        /**调节ViewPager的滑动速度**/
        mViewPager.setSpeedScroller(300);

        /**给ViewPager设置缩放动画，这里通过PageTransformer来实现**/
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        List<String> strList = Arrays.asList("one", "two", "three", "four");

        /**
         * 需要将整个页面的事件分发给ViewPager，不然的话只有ViewPager中间的view能滑动，其他的都不能滑动，
         * 这是肯定的，因为ViewPager总体布局就是中间那一块大小，其他的子布局都跑到ViewPager外面来了
         */
        findViewById(R.id.page_container1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mPagerAdapter = new TubatuAdapter(this,strList);
        mViewPager.setAdapter(mPagerAdapter);
        initData();

        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_shops);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        //bottomNavigationView Item 选择监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                hideAllFragment(transaction);
                Intent intent=new Intent();

                switch (item.getItemId()){

                    case R.id.navigation_personal:
                        intent.putExtra("data_return","个人中心");
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                    case R.id.navigation_home:
                        intent.putExtra("data_return","首页");
                        setResult(RESULT_OK,intent);
                        finish();
                        break;
                    case R.id.navigation_shops:
                        break;
                }
                transaction.commit();

                return false;
            }
        });

        final ScrollLayout mScrollLayout=(ScrollLayout)findViewById(R.id.scroll_down_layout);

        ScrollLayout.OnScrollChangedListener mOnScrollChangedListener=new ScrollLayout.OnScrollChangedListener() {
            @Override
            public void onScrollProgressChanged(float currentProgress) {
                if (currentProgress >= 0) {
                    float precent = 280 * currentProgress;
                    if (precent > 280) {
                        precent = 280;
                    } else if (precent < 0) {
                        precent = 0;
                    }
                    mScrollLayout.getBackground().setAlpha(255 - (int) precent);
                }
            }

            @Override
            public void onScrollFinished(ScrollLayout.Status currentStatus) {

            }

            @Override
            public void onChildScroll(int top) {

            }
        };
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.getBackground().setAlpha(0);
        mScrollLayout.setExitOffset(280);

    }

    private void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.one);
        list.add(R.mipmap.two);
        list.add(R.mipmap.three);
        /**这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常；**/
        mViewPager.setOffscreenPageLimit(list.size());
        mPagerAdapter.addAll(list);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                requestLocation();
                }else{
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update=MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update=MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData=locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    public void hideAllFragment(FragmentTransaction transaction){
        if(personalFragment!=null){
            transaction.hide(personalFragment);
        }
        if(homeFragment!=null){
            transaction.hide(homeFragment);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);

    }

}
