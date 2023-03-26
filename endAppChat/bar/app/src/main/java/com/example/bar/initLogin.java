package com.example.bar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.TUIThemeManager;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;

public class initLogin extends AppCompatActivity {

    public static final int SDKAPPIDS = 1400800264;
    private static  String USERID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_login);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button loginbutton = findViewById(R.id.btn_logins);
        EditText username = findViewById(R.id.et_usernames);
        Context context = initLogin.this;
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String USERID = username.getText().toString().trim();
                String USERSIGS = GenerateTestUserSig.genTestUserSig(USERID);
                TUILogin.login(context, SDKAPPIDS, USERID, USERSIGS, new TUICallback() {

                    @Override
                    public void onError(final int code, final String desc) {
                    }

                    @Override
                    public void onSuccess() {
                    }
                });

                TUIThemeManager.getInstance().changeLanguage(context,"zh");
                Intent intent = new Intent(initLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}