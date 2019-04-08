package com.example.stackfarm.myapplication.personalCenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.stackfarm.myapplication.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    Button fin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        fin=findViewById(R.id.finish1);
        fin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finish1:
                finish();
                break;
        }
    }

}
