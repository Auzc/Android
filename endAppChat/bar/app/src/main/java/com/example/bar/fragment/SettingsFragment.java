package com.example.bar.fragment;

import static com.example.bar.MainActivity.user;
import static com.example.bar.MainActivity.user_objectId;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bar.R;
import com.example.bar.database.User;
import com.example.bar.user.SettingsActivity;
import com.example.bar.user.LoginActivity;
import com.example.bar.user.EditMessageActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class SettingsFragment extends Fragment {

    private ImageView userProfileImage;
    private Button button_user_nickname;
    private TextView hello;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        userProfileImage = view.findViewById(R.id.user_profile_image);
        button_user_nickname = view.findViewById(R.id.button_user_nickname);
        hello =  view.findViewById(R.id.hello);
        TextView user_id=view.findViewById(R.id.user_id);
        if(user_objectId!=null){
            button_user_nickname.setText(user.getUser_name());
            user_id.setText(user_objectId);
            hello.setText("👋你好！"+user.getName());
        }




        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cr7);
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f, bitmap.getWidth() / 2f, paint);
        userProfileImage.setImageBitmap(circleBitmap);


        button_user_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_objectId==null){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        Button button_message_edit = view.findViewById(R.id.button_message_edit);
        button_message_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EditMessageActivity.class);
                startActivity(intent);
            }
        });
        ImageView action_settings = view.findViewById(R.id.action_settings);
        action_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
