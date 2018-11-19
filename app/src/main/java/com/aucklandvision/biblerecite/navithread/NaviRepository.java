package com.aucklandvision.biblerecite.navithread;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;

//ver1.1.1
public class NaviRepository {
    private SQLiteDatabase sqLiteDatabase;

    public NaviRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    public String getUpdateDate() {
        String lastDate = null;
        if (sqLiteDatabase != null) {
            String sql = "SELECT MAX(_LASTDATE_SERVER) FROM USER";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            lastDate = cursor.getString(0);
        }
        return lastDate;
    }
}
