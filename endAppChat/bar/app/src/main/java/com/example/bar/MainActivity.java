package com.example.bar;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bar.database.PostSyncHelper;
import com.example.bar.database.User;
import com.example.bar.fragment.CommunityFragment;
import com.example.bar.fragment.HomeFragment;
import com.example.bar.fragment.MessagesFragment;
import com.example.bar.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment;
    private MessagesFragment messagesFragment;
    private CommunityFragment communityFragment;
    private SettingsFragment settingsFragment;
    public  static User user=new User();
    public  static String user_objectId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "b0d100ab01a474d071fdfa28178407e1");

        // 初始化PostSyncHelper对象
        PostSyncHelper.syncPosts(this, new PostSyncHelper.OnPostSyncListener() {
            @Override
            public void onSyncSuccess() {
                Log.d(TAG, "onSyncSuccess: data synced successfully");
                Toast.makeText(MainActivity.this, "Data synced successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSyncFailed(String error) {
                Log.e(TAG, "onSyncFailed: failed to sync data", new Exception(error));
                Toast.makeText(MainActivity.this, "Failed to sync data: " + error, Toast.LENGTH_LONG).show();
            }
        });





        user_objectId=getIntent().getStringExtra("user_objectId");
        if(user_objectId!=null){


                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject(user_objectId, new QueryListener<User>() {
                    @Override
                    public void done(User object, BmobException e) {
                        if(e==null){
                            user.setUser_name(object.getUser_name());
                            user.setWeight(object.getWeight());
                            user.setHeight(object.getHeight());
                            user.setGender(object.getGender());
                            user.setPhone(object.getPhone());
                            user.setLocation(object.getLocation());
                            user.setName(object.getName());
                            user.setAge(object.getAge());
                            user.setSignature(object.getSignature());
                        }else{

                        }
                    }
                });
        }



        // 初始化底部导航栏和Fragment
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        homeFragment = new HomeFragment();
        messagesFragment = new MessagesFragment();
        settingsFragment = new SettingsFragment();
        communityFragment = new CommunityFragment();
        // 默认显示HomeFragment
        setFragment(homeFragment);

        // 底部导航栏选中项变化的监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.action_messages:
                        setFragment(messagesFragment);
                        return true;
                    case R.id.action_community:
                        setFragment(communityFragment);
                        return true;
                    case R.id.action_settings:
                        setFragment(settingsFragment);
                        return true;
                }
                return false;
            }
        });
    }



    @Override
    protected void onResume() {

        super.onResume();
    }

    // 切换Fragment
    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
