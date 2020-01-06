package com.example.communication.database_client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.communication.model.Words;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    public SQLiteOpenHelper openHelper;
    public SQLiteDatabase database;
    public static DatabaseAccess instance;

    public DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance= new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.database=openHelper.getWritableDatabase();
    }

    public void close(){
        if (database != null){
            this.database.close();
        }
    }

    public List<Words> getAllWord(){
        Words words;
        List<Words> wordsList=new ArrayList<>();
        open();
        Cursor cursor=database.rawQuery("select * from EngMyn",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words=new Words(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            wordsList.add(words);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return wordsList;
    }
    
}
