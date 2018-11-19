package com.aucklandvision.biblerecite.init;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;
import com.aucklandvision.biblerecite.main.UserInfo;

public class InitRepository {
    private SQLiteDatabase sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();

    public UserInfo getInit() {
        UserInfo userInfo = new UserInfo();
        if (sqLiteDatabase != null) {
            String sql = "SELECT THEMENAME, ARGB, FONTSIZE, FONTTYPE FROM USER";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            userInfo.setThemeName(cursor.getString(0));
            userInfo.setArgb(cursor.getString(1));
            userInfo.setFontsize(cursor.getInt(2));
            userInfo.setFonttype(cursor.getString(3));
        }
        return userInfo;
    }
}
