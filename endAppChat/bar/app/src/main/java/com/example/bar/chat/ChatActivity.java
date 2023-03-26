package com.example.bar.chat;


import static com.example.bar.MainActivity.user_objectId;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bar.MainActivity;
import com.example.bar.R;
import com.example.bar.database.User;
import com.example.bar.user.LoginActivity;
import com.example.bar.user.RegisterActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ChatActivity extends AppCompatActivity {
    private EditText mMessageEditText;
    private Button mSendButton;
    private ImageButton btn_back;


    private ArrayList<Message> mMessages = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private String mUser_id;
    private String chatRoom_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        btn_back=findViewById(R.id.btn_back1);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 获取用户名
        mUser_id = user_objectId;
        ChatRoom chatRoom =new ChatRoom();

        chatRoom_id = getIntent().getStringExtra("chatRoom_id");



        // 初始化控件
        mMessageEditText = findViewById(R.id.edit_text);
        mSendButton = findViewById(R.id.send_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMessageEditText.getText()!=null){
                    Message anew= new Message();
                    anew.setChatroom_id(chatRoom_id);
                    anew.setTime(getTime());
                    anew.setUser_id(user_objectId);
                    anew.setMessage_content(mMessageEditText.getText().toString());
                    anew.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                mMessageEditText.setText("");
                                Toast.makeText(ChatActivity.this, anew.getChatroom_id()+"\n"+anew.getUser_id(), Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(ChatActivity.this, "发送异常", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else {

                }
            }
        });
        // 实例化RecyclerView
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 创建适配器并设置给RecyclerView
        mAdapter = new ChatAdapter(mMessages);
        mRecyclerView.setAdapter(mAdapter);


    }
    public String getTime(){
        SimpleDateFormat d_f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        d_f.setTimeZone(TimeZone.getTimeZone("GMT+08"));  //设置时区，+08是北京时间
        String date = d_f.format(new Date());

        System.out.println("获取的时间为："  +date);
        return date;

    }
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            BmobQuery<Message> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.findObjects(new FindListener<Message>() {
                @Override
                public void done(List<Message> object, BmobException e) {
                    if (e == null) {
                        int i;
                        for (i = 0; i < object.size(); i++) {
                            if(object.get(i).getChatroom_id().equals(chatRoom_id)){
                                mMessages.add(new Message(object.get(i).getUser_id(),object.get(i).getMessage_content(),object.get(i).getTime()));
                            }

                        }
                    } else {
                        Toast.makeText(ChatActivity.this,"网络异常\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            // 将新消息添加到列表中

            mRecyclerView.scrollToPosition(mMessages.size() - 1);
            // 重新启动定时任务，使其循环执行
            mHandler.postDelayed(this, 10000);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // 启动定时任务，每隔一定时间间隔添加一条新消息
        mHandler.postDelayed(mRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 停止定时任务，防止在后台无限制地添加新消息
        mHandler.removeCallbacks(mRunnable);
    }



}
