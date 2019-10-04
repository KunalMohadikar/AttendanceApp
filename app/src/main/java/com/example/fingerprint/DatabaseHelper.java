package com.example.fingerprint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Attendance.db";
    public static final String CLASSES_TABLE = "classname_table";
    public static final String CLASSES_TABLE_COLUMN1 = "CLASSNAME";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+CLASSES_TABLE+ " (CLASSNAME TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLASSES_TABLE);
        int i;
        for(i=0;i<FirstActivity.ClassNames.size();i++)
        {
            db.execSQL("DROP TABLE IF EXISTS " + FirstActivity.ClassNames.get(i));
        }
        FirstActivity.ClassNames.clear();
        onCreate(db);
    }

    public boolean insertData(String Class_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSES_TABLE_COLUMN1,Class_name);
        long result =db.insert(CLASSES_TABLE,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+CLASSES_TABLE,null);
        return res;
    }

    public void create_table(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table "+table+ " (ROLL_NO INTEGER PRIMARY KEY,NAME TEXT,ATTENDANCE INTEGER)");
    }

    public boolean Insert_data_in_class(String Class_name,String ROLL_NO,String Name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ROLL_NO",ROLL_NO);
        contentValues.put("NAME",Name);
        contentValues.put("ATTENDANCE","0");
        long result =db.insert(Class_name,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getClassData(String class_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+class_name,null);
        return res;
    }



}
