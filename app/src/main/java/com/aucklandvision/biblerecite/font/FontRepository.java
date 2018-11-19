package com.aucklandvision.biblerecite.font;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;

import java.util.ArrayList;

public class FontRepository {
    private SQLiteDatabase sqLiteDatabase;

    public FontRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    public ArrayList<Object> getFontInfo() {
        ArrayList<Object> fontList = new ArrayList<>();
        if (sqLiteDatabase != null) {
            String sql = "SELECT FONTSIZE, FONTTYPE FROM USER";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            fontList.add(cursor.getInt(0));
            fontList.add(cursor.getString(1));
        }
        return fontList;
    }

    public void updateFontSize(int fontSize) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE USER SET FONTSIZE = "  + fontSize;
            sqLiteDatabase.execSQL(sql);
        }
    }

    public void updateFontType(String fontType) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE USER SET FONTTYPE = '"  + fontType + "'";
            sqLiteDatabase.execSQL(sql);
        }
    }
}
