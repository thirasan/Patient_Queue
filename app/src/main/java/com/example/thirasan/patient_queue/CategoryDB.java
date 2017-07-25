package com.example.thirasan.patient_queue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thirasan on 7/25/2017 AD.
 */

public class CategoryDB extends SQLiteOpenHelper{
    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public CategoryDB(Context context) {
        super(context, "category.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FRIEND_TABLE = "CREATE TABLE category ( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sort TEXT)";

        db.execSQL(CREATE_FRIEND_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS category";

        db.execSQL(DROP_FRIEND_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }

    public List<String> getCategoryList() {
        List<String> category = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                ("category", null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {
            category.add(cursor.getString(1) + "");

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return category;
    }

    public void addCategory(String category) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Friend.Column.ID, friend.getId());
        values.put("sort", category);

        sqLiteDatabase.insert("category", null, values);

        sqLiteDatabase.close();
    }

    public void deleteCategory(String id) {

        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("sort","null");

        sqLiteDatabase.update("category",values, "id = "+ id, null);

//        sqLiteDatabase.delete("category", "id = " + id, null);

        sqLiteDatabase.close();
    }
}
