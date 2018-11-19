package com.aucklandvision.biblerecite.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

public class CreateAndAlterTable extends SQLiteOpenHelper {
    public CreateAndAlterTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableName = "VERSES";
        String sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);

        tableName = "USER";
        sql = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(sql);

        tableName = "VERSES";
        sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_ID_SERVER INTEGER, " +
                "VERSE_TITLE_KR TEXT, " +
                "VERSE_TITLE_EN TEXT, " +
                "VERSE_SECTION INTEGER, " +
                "VERSE_KR TEXT, " +
                "VERSE_EN TEXT, " +
                "VERSION INTEGER, " +
                "MON INTEGER, " +
                "YR INTEGER," +
                "FAVORITE INTEGER," +
                "QUIZ_LEVEL INTEGER," +
                "CHECK_REMEMBER_KR INTEGER," +
                "CHECK_REMEMBER_EN INTEGER)";
        db.execSQL(sql);
        Log.d("createAllTable", "VERSES TABLE CREATED");

        tableName = "USER";
        sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                "(_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "LANGUAGES integer, " +
                "CURRPAGE integer," +
                "CURRYR integer," +
                "FONTSIZE integer," +
                "FONTTYPE TEXT," +
                "THEMENAME TEXT," +
                "ARGB TEXT," +
                "_LASTDATE_SERVER TEXT)";
        db.execSQL(sql);
        Log.d("createAllTable", "USER TABLE CREATED");

        sql = "INSERT INTO USER(" +
                "LANGUAGES, " +
                "CURRPAGE, " +
                "CURRYR, " +
                "FONTSIZE, " +
                "FONTTYPE, " +
                "THEMENAME, " +
                "ARGB, " +
                "_LASTDATE_SERVER) VALUES("+
                "1, 0, 0, 25, 'Normal', 'Indigo', '0xFF3A5A9A', '20180619 12:12:12')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > 1) {

        }
    }
}
