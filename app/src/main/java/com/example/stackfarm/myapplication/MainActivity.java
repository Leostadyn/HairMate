package com.example.stackfarm.myapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;

import com.example.stackfarm.myapplication.community.CommunityFragment;
import com.example.stackfarm.myapplication.home.HomeFragment;
import com.example.stackfarm.myapplication.personalCenter.PersonalFragment;

public class MainActivity extends AppCompatActivity {

     private HomeFragment homeFragment;
     private PersonalFragment personalFragment;
     private CommunityFragment communityFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();


            switch (item.getItemId()){
                case R.id.navigation_home:
                    hideAllFragment(transaction);
                    if(homeFragment==null){
                        homeFragment =HomeFragment.newInstance(R.layout.home);//"第二个Fragment"
                        transaction.add(R.id.FramePage,homeFragment);
                    }else{
                        transaction.show(homeFragment);
                    }
                    break;
                case R.id.navigation_personal:
                    hideAllFragment(transaction);
                    if(personalFragment==null){
                        personalFragment = PersonalFragment.newInstance(R.layout.personal);
                        transaction.add(R.id.FramePage,personalFragment);
                    }else{
                        transaction.show(personalFragment);
                    }
                    break;
                case R.id.navigation_community:
                    hideAllFragment(transaction);
                    if(communityFragment==null){
                        communityFragment=CommunityFragment.newInstance(R.layout.communities);
                        transaction.add(R.id.FramePage,communityFragment);
                    }else{
                        transaction.show(communityFragment);
                    }
                    break;
            }
            transaction.commit();

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(500L);
            getWindow().setEnterTransition(fade);
        }
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(homeFragment==null){
            homeFragment =HomeFragment.newInstance(R.layout.home);//"第二个Fragment"
            transaction.add(R.id.FramePage,homeFragment);
        }
        transaction.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setItemTextColor(null);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        //bottomNavigationView Item 选择监听
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }



    public void hideAllFragment(FragmentTransaction transaction){
        if(personalFragment!=null){
            transaction.hide(personalFragment);
        }
        if(homeFragment!=null){
            transaction.hide(homeFragment);
        }
        if(communityFragment!=null){
            transaction.hide(communityFragment);
        }

    }


}
