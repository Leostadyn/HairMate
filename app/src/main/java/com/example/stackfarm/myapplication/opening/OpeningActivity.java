package com.example.stackfarm.myapplication.opening;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.stackfarm.myapplication.MainActivity;
import com.example.stackfarm.myapplication.R;

public class OpeningActivity extends AppCompatActivity {
    private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening);
        InactivityTimer inactivityTimer=new InactivityTimer(this);
        icon=(ImageView) findViewById(R.id.icon);
        final TranslateAnimation bIcon = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -10f);
        bIcon.setDuration(3000);
        icon.startAnimation(bIcon);
        bIcon.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub


            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                // TODO Auto-generated method stub
                icon.layout(0,2000,0,2000);
                icon.clearAnimation();
                Intent intent=new Intent(OpeningActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}
