package com.example.stackfarm.myapplication.guiding;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.TubatuAdapter;
import com.example.stackfarm.myapplication.utils.ClipViewPager;
import com.example.stackfarm.myapplication.utils.ScalePageTransformer;
import com.yinglan.scrolllayout.ScrollLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private MapView mapView=null;
    private ScrollLayout mScrollLayout;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private boolean isFirstLocate=true;
    private TubatuAdapter mPagerAdapter;
    private ClipViewPager mViewPager;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1700L);
            getWindow().setEnterTransition(fade);
        }

        setContentView(R.layout.guiding);


        mapView=(MapView)findViewById(R.id.aMapView);
        mapView.onCreate(savedInstanceState);

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
            String[] permissions=permissionList.toArray(new String [permissionList.size()]);
            ActivityCompat.requestPermissions(GuideActivity.this,permissions,1);
        }else{
           requestLocation();
        }

        mScrollLayout=(ScrollLayout)findViewById(R.id.scroll_down_layout);

        ScrollLayout.OnScrollChangedListener mOnScrollChangedListener=new ScrollLayout.OnScrollChangedListener() {
            @Override
            public void onScrollProgressChanged(float currentProgress) {
                if (currentProgress >= 0) {
                    float percent = 280 * currentProgress;
                    if (percent > 280) {
                        percent = 280;
                    } else if (percent < 0) {
                        percent = 0;
                    }
                    mScrollLayout.getBackground().setAlpha(255 - (int) percent);
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
        mScrollLayout.setVisibility(View.INVISIBLE);


        mViewPager = (ClipViewPager)findViewById(R.id.viewpager3);
        /**调节ViewPager的滑动速度**/
        mViewPager.setSpeedScroller(300);

        /**给ViewPager设置缩放动画，这里通过PageTransformer来实现**/
        mViewPager.setPageTransformer(true, new ScalePageTransformer());
        List<String> strList = Arrays.asList("one", "two", "three");

        /**
         * 需要将整个页面的事件分发给ViewPager，不然的话只有ViewPager中间的view能滑动，其他的都不能滑动，
         * 这是肯定的，因为ViewPager总体布局就是中间那一块大小，其他的子布局都跑到ViewPager外面来了
         */
        findViewById(R.id.page_container3).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mPagerAdapter = new TubatuAdapter(this,strList);
        mViewPager.setAdapter(mPagerAdapter);
        initData();


    }

    private void initData() {
        List<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.one);
//        list.add(R.mipmap.two);
//        list.add(R.mipmap.three);
        /**这里需要将setOffscreenPageLimit的值设置成数据源的总个数，如果不加这句话，会导致左右切换异常；**/
        mViewPager.setOffscreenPageLimit(list.size());
        mPagerAdapter.addAll(list);
    }

    private void requestLocation(){
        if(aMap==null) {
            aMap = mapView.getMap();
            setMapCustomStyleFile(this);
        }
        initLocation();
        makeLoc();
    }

    private void showShops(){
        mScrollLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        //initMap(outState);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private  void makeLoc(){
        LatLng latLng=new LatLng(31.9052300000,118.8998700000);
        LatLng latLng1=new LatLng(31.9152000000,118.8998600000);
        final Marker marker1= aMap.addMarker(new MarkerOptions().position(latLng));
        final Marker marker2=aMap.addMarker(new MarkerOptions().position(latLng1));
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener=new AMap.OnMarkerClickListener() {

            // marker 对象被点击时回调的接口
            // 返回 true 则表示移到地图中心，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                final String st1=marker1.getId();
                final String st2=marker2.getId();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if(marker.getId().equals(st1)) {
                    showShops();
                    hideAllFragment(transaction);
                    if (firstFragment == null) {
                        Log.e("TAG", "222222222");
                        firstFragment = FirstFragment.newInstance(R.layout.latlng);
                        transaction.add(R.id.FrameWindow, firstFragment);
                    } else {
                        transaction.show(firstFragment);
                    }
                }else if(marker.getId().equals(st2)){
                    showShops();
                    hideAllFragment(transaction);
                    if(secondFragment==null){
                        Log.e("TAG","33333333333");
                        secondFragment=SecondFragment.newInstance(R.layout.latlng1);
                        transaction.add(R.id.FrameWindow,secondFragment);
                    }else{
                        transaction.show(secondFragment);
                    }
                }
                transaction.commit();
                Toast.makeText(GuideActivity.this,marker.getId(),Toast.LENGTH_LONG).show();
                return false;
            }
        };

// 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
    }




    public void hideAllFragment(FragmentTransaction transaction){
        if(firstFragment!=null){
            Log.e("TAG","h111");
            transaction.hide(firstFragment);
        }
        if(secondFragment!=null){
            Log.e("TAG","h2222");
            transaction.hide(secondFragment);
        }
    }
    private void initLocation(){
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);//设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }

    /**
     * 自定义地图样式，需要下载离线地图style文件
     * @param context
     */
    private void setMapCustomStyleFile(Context context) {
        String styleName = "style3.data";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        aMap.setMapCustomEnable(true);
        //设定离线样式文件
        aMap.setCustomMapStylePath(filePath + "/" + styleName);
        //设定是否显示标注
        aMap.showMapText(true);
    }

}
