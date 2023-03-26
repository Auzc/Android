package com.example.bar.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PostSyncHelper {

    private static final String TAG = "PostSyncHelper";

    public interface OnPostSyncListener {
        void onSyncSuccess();
        void onSyncFailed(String error);
    }

    public static void syncPosts(final Context context, final OnPostSyncListener listener) {
        BmobQuery<Post> query = new BmobQuery<>();
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "syncPosts: success");
                    // 将数据保存到本地SQLite数据库中
                    DatabaseHelper dbHelper = new DatabaseHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    try {
                        db.beginTransaction();
                        for (Post post : list) {
                            ContentValues values = new ContentValues();
                            values.put("objectId", post.getObjectId());
                            values.put("title", post.getTitle());
                            values.put("content", post.getContent());
                            values.put("createdAt", post.getCreatedAt());
                            values.put("updatedAt", post.getUpdatedAt());
                            db.insertWithOnConflict("posts", null, values, SQLiteDatabase.CONFLICT_REPLACE);
                        }
                        db.setTransactionSuccessful();
                        listener.onSyncSuccess();
                    } catch (SQLException ex) {
                        Log.e(TAG, "syncPosts: failed to save data to local database", ex);
                        listener.onSyncFailed(ex.getMessage());
                    } finally {
                        db.endTransaction();
                        db.close();
                    }
                } else {
                    Log.e(TAG, "syncPosts: failed to get data from Bmob cloud", e);
                    listener.onSyncFailed(e.getMessage());
                }
            }
        });
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, "myapp.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE posts (objectId TEXT PRIMARY KEY, title TEXT, content TEXT, createdAt TEXT, updatedAt TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS posts");
            onCreate(db);
        }
    }

}

