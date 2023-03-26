package com.example.bar;

import static com.example.bar.MainActivity.user_objectId;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bar.chat.ChatActivity;
import com.example.bar.database.Post;
import com.example.bar.database.User;
import com.example.bar.user.EditMessageActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PostActivity extends AppCompatActivity {

    private ImageButton btn_back,ok;
    private EditText titleEditText,contentEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        btn_back=findViewById(R.id.btn_back);
        titleEditText=findViewById(R.id.titleEditText);
        contentEditText=findViewById(R.id.contentEditText);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleEditText.getText()!=null&&contentEditText.getText()!=null){
                    Post anew=new Post();
                    anew.setTitle(titleEditText.getText().toString());
                    anew.setTime(getTime());
                    anew.setAuthor("admin");
                    anew.setContent(contentEditText.getText().toString());
                    anew.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(PostActivity.this,"发送成功" , Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(PostActivity.this, "发送异常", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }





            }
        });
    }
    public String getTime(){
        SimpleDateFormat d_f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        d_f.setTimeZone(TimeZone.getTimeZone("GMT+08"));  //设置时区，+08是北京时间
        String date = d_f.format(new Date());

        System.out.println("获取的时间为："  +date);
        return date;

    }

}
