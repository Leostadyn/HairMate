package com.example.stackfarm.myapplication.logining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stackfarm.myapplication.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username_edit;
    private EditText password_edit;
    private Button login_btn;
    private Button forget_btn;
    private Button register_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logining);

        username_edit=(EditText) findViewById(R.id.username_edit);
        password_edit=(EditText) findViewById(R.id.password_edit);
        login_btn=(Button) findViewById(R.id.login_btn);
        forget_btn=(Button) findViewById(R.id.forget_btn);
        register_btn=(Button) findViewById(R.id.register_btn);

        login_btn.setOnClickListener(this);
        forget_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                String username=username_edit.getText().toString();
                String password=password_edit.getText().toString();
                if(username.equals("admin")&&password.equals("123456")){

                }else {
                    Toast.makeText(LoginActivity.this,"username or password is invalid",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.forget_btn:


                break;

            case R.id.register_btn:
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }
}
