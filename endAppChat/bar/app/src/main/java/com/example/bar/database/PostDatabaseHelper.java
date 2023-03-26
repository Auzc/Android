package com.example.bar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PostDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "post_database";

    private static final String TABLE_POSTS = "posts";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TIME = "time";

    public PostDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_CONTENT + " TEXT,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_TIME + " INTEGER"
                + ")";
        db.execSQL(CREATE_POSTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        onCreate(db);
    }

    public void addPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, post.getTitle());
        values.put(KEY_CONTENT, post.getContent());
        values.put(KEY_AUTHOR, post.getAuthor());
        values.put(KEY_TIME, post.getTime());

        db.insert(TABLE_POSTS, null, values);
        db.close();
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query("posts", null, null, null, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                Post post = new Post();
                int index = cursor.getColumnIndex("id");
                if (index >= 0) {
                    post.setId(cursor.getString(index));
                }
                index = cursor.getColumnIndex("title");
                if (index >= 0) {
                    post.setTitle(cursor.getString(index));
                }
                index = cursor.getColumnIndex("content");
                if (index >= 0) {
                    post.setContent(cursor.getString(index));
                }
                index = cursor.getColumnIndex("author");
                if (index >= 0) {
                    post.setAuthor(cursor.getString(index));
                }
                index = cursor.getColumnIndex("time");
                if (index >= 0) {
                    post.setTime(cursor.getString(index));
                }
                posts.add(post);
            }
        } catch (Exception e) {
            //Log.e(TAG, "getAllPosts: failed to get all posts", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return posts;
    }




    public void updatePost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, post.getTitle());
        values.put(KEY_CONTENT, post.getContent());
        values.put(KEY_AUTHOR, post.getAuthor());
        values.put(KEY_TIME, post.getTime());

        db.update(TABLE_POSTS, values, KEY_ID + " = ?", new String[]{String.valueOf(post.getId())});

        db.close();
    }

    public void deletePost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POSTS, KEY_ID + " = ?", new String[]{String.valueOf(post.getId())});
        db.close();
    }

    public List<Post> getPostsByTime(long startTime, long endTime) {
        List<Post> postList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_POSTS + " WHERE " + KEY_TIME + " BETWEEN ? AND ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(startTime), String.valueOf(endTime)});

        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.setId(cursor.getString(0));
                post.setTitle(cursor.getString(1));
                post.setContent(cursor.getString(2));
                post.setAuthor(cursor.getString(3));
                post.setTime(cursor.getString(4));

                postList.add(post);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return postList;
    }
}