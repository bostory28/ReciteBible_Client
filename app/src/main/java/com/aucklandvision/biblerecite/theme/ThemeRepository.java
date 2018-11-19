package com.aucklandvision.biblerecite.theme;

import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;

public class ThemeRepository {

    private SQLiteDatabase sqLiteDatabase;

    public ThemeRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    public void updateTheme(String colorName, String argb_content) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE USER SET THEMENAME = '"  + colorName  + "', ARGB = '" + argb_content + "'";
            sqLiteDatabase.execSQL(sql);
        }
    }
}
