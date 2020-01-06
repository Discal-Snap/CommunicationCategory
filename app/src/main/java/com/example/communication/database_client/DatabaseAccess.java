package com.example.communication.database_client;

import android.content.ContentValues;
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

    public List<Words> getAllWord(String string){

        Words words=null;
        List<Words> wordsList=new ArrayList<>();
        open();
        Cursor cursor=database.rawQuery("select * from EngMyn where category like "+"'%"+string+"%'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            words=new Words(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            wordsList.add(words);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return wordsList;
    }

    public int update(int id,String file_location){
        open();
        database.beginTransaction();
        ContentValues contentValues=new ContentValues();
        contentValues.put("file_location",file_location);
        int result=database.update("EngMyn",contentValues,"id="+id,null);
        database.setTransactionSuccessful();
        database.endTransaction();
        close();
        return result;
    }

    public String getFileLocation(String text){
        String result="not found";
        open();
        Cursor cursor = database.rawQuery("select * from EngMyn where Eng = "+"'"+text.trim()+"'",null);
        if (cursor.moveToFirst()){
            result=cursor.getString(5);
        }
        cursor.close();
        close();

        return result;
    }

}
