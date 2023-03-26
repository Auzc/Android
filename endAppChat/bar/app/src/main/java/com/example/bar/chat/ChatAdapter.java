package com.example.bar.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bar.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<Message> mMessages;

    public ChatAdapter(ArrayList<Message> messages) {
        mMessages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = mMessages.get(position);

        holder.mUserIdTextView.setText(message.getUser_id());
        holder.mMessageContentTextView.setText(message.getMessage_content());
        holder.mTimeTextView.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }
    public void addMessage(Message message) {
        mMessages.add(message);
        // 通知适配器数据已经更新，需要刷新视图
        notifyDataSetChanged();
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView mUserIdTextView;
        public TextView mMessageContentTextView;
        public TextView mTimeTextView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            mUserIdTextView = itemView.findViewById(R.id.nickname);
            mMessageContentTextView = itemView.findViewById(R.id.content);
            mTimeTextView = itemView.findViewById(R.id.time);
        }
    }
}
