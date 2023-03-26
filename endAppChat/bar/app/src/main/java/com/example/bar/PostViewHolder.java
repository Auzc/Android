package com.example.bar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bar.database.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private TextView titleTextView;
    private TextView contentTextView;
    private TextView authorTextView;




    public PostViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.postTitle);
        contentTextView = itemView.findViewById(R.id.postContent);
        authorTextView = itemView.findViewById(R.id.postAuthor);
    }

    public void bind(Post post) {
        titleTextView.setText(post.getTitle());
        contentTextView.setText(post.getContent());
        authorTextView.setText(post.getAuthor());
    }
}
