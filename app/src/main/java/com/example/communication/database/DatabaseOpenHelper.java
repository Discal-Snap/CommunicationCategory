package com.example.communication.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    public static final String DB_NAME="Communication";
    public static final int DB_VERSION=1;

    public DatabaseOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
}
