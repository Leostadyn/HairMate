package com.example.stackfarm.myapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.stackfarm.myapplication.BaseActivity;
import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.guiding.GuideActivity;
import com.example.stackfarm.myapplication.personalCenter.PersonalActivity;

public class HomeActivity extends BaseActivity {
    protected BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_personal:
                    Intent intent2=new Intent(HomeActivity.this, PersonalActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_shops:
                    Intent intent3=new Intent(HomeActivity.this, GuideActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
