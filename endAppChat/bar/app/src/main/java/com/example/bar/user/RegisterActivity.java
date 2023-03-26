package com.example.bar.user;

import static com.example.bar.MainActivity.user;
import static com.example.bar.MainActivity.user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mRegisterButton;
    private TextView mLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        Bmob.initialize(this, "b0d100ab01a474d071fdfa28178407e1");
        mUsernameEditText = findViewById(R.id.et_username);
        mPasswordEditText = findViewById(R.id.et_password);
        mRegisterButton = findViewById(R.id.btn_register);
        mLoginTextView = findViewById(R.id.tv_login);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mUsernameEditText.getTextSize()>=1){
                    user.setUser_name(mUsernameEditText.getText().toString());
                    user.setPassword(mPasswordEditText.getText().toString());
                    user.setLocation("左边锋");
                    user.setName("");
                    user.setGender("男");
                    user.setAge(20);
                    user.setHeight(180);
                    user.setWeight(70);
                    user.setSignature("");
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){

                                Intent intent =new Intent();
                                intent.putExtra("user_objectId", objectId);
                                intent.setClass(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{


                            }
                        }
                    });

                }else{

                }
            }
        });

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                mLoginTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
