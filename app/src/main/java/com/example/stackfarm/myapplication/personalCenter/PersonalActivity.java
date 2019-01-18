package com.example.stackfarm.myapplication.personalCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.stackfarm.myapplication.BaseActivity;
import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.collector.ActivityCollector;
import com.example.stackfarm.myapplication.guiding.GuideActivity;
import com.example.stackfarm.myapplication.home.HomeActivity;

public class PersonalActivity extends BaseActivity {
    private TextView nickTag;
    private Button ord;
    private Button his;
    private Button fav;
    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1=new Intent(PersonalActivity.this, HomeActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.navigation_personal:
                    return true;
                case R.id.navigation_shops:
                    Intent intent3=new Intent(PersonalActivity.this, GuideActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        int num=0;
        nickTag =findViewById(R.id.nickname_tag);
        ord=findViewById(R.id.order);
        his=findViewById(R.id.his);
        fav=findViewById(R.id.fav);

        nickTag.setText("昵称："+"\n"+"\n"+"个性签名：");
        ord.setText(num+"\n"+"订单");
        his.setText(num+"\n"+"历史");
        fav.setText(num+"\n"+"收藏");


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_personal);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityCollector.removeActivity(this);
    }
}
