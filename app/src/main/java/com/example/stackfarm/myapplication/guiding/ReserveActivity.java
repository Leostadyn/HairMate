package com.example.stackfarm.myapplication.guiding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stackfarm.myapplication.R;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView head;
    TextView name;
    Button reserve;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve);
        initView();
        Intent intent=getIntent();
        judge(intent.getStringExtra("name"));
        reserve.setOnClickListener(this);
    }
    void initView(){
        head=(ImageView)findViewById(R.id.head);
        name=(TextView)findViewById(R.id.name);
        reserve=(Button)findViewById(R.id.reserve);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reserve:
                Toast.makeText(this,"预约成功，请耐心等候:)",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void judge(String s){
        switch (s){
            case "1":
                loadOne();
                break;
            case "2":
                loadTwo();
                break;
            default:
                break;
        }
    }

    void loadOne(){
        head.setImageResource(R.mipmap.barber1);
        name.setText("Tony老师");
    }

    void loadTwo(){
        head.setImageResource(R.mipmap.barber2);
        name.setText("首席发型设计师");
    }
}
