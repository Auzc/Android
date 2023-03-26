package com.example.bar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bar.PostActivity;
import com.example.bar.PostAdapter;
import com.example.bar.R;
import com.example.bar.chat.ChatActivity;
import com.example.bar.chat.Message;
import com.example.bar.database.Post;
import com.example.bar.database.PostDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CommunityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> myPostList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PostDatabaseHelper dbHelper;
    private Button add_new;
    public CommunityFragment() {
        // Required empty public constructor
    }
    public CommunityFragment(PostDatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community, container, false);
        add_new= rootView.findViewById(R.id.add_new);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PostActivity.class);
                startActivity(intent);

            }
        });

        // 初始化 RecyclerView
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        mDbHelper = new PostDatabaseHelper(this);
//        List<Post> postList = mDbHelper.getAllPosts();
//
//        if (myPostList != null) {
//            mAdapter = new PostAdapter(postList);
//            mRecyclerView.setAdapter(mAdapter);
//        }

// 设置RecyclerView的适配器


        // 初始化 Adapter
        //
        //getPostsFromServer();




        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        return rootView;
    }

    private void getPostsFromServer(){
        BmobQuery<Post> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> object, BmobException e) {
                if (e == null) {
                    int i;
                    for (i = 0; i < object.size(); i++) {
                        myPostList.add(new Post(object.get(i).getTitle(),object.get(i).getContent(),object.get(i).getAuthor(),object.get(i).getTime()));
                    }
                } else {
                    Toast.makeText(getActivity(),"网络异常\n"+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        if(mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
        if (myPostList != null) {
            PostAdapter adapter = new PostAdapter(myPostList);

// 设置RecyclerView的适配器
            mRecyclerView.setAdapter(adapter);
        }
        Log.println(Log.VERBOSE, "lxy", "获取成功");

    }

    public void onResume(){
        super.onResume();
        //getPostsFromServer();
    }
    @Override
    public void onRefresh() {
        // 向服务器发送请求，获取最新的帖子内容
        //getPostsFromServer();
        mSwipeRefreshLayout.setRefreshing(false);


    }



}