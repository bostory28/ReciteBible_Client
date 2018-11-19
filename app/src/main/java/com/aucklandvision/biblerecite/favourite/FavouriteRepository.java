package com.aucklandvision.biblerecite.favourite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;
import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

public class FavouriteRepository {

    private SQLiteDatabase sqLiteDatabase;

    public FavouriteRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    public ArrayList<Verses> getFavoriteList() {
        ArrayList<Verses> favoriteList = new ArrayList<>();
        if (sqLiteDatabase != null) {
            String sql = "SELECT _ID, " +
                    "VERSE_TITLE_KR, " +
                    "VERSE_TITLE_EN, " +
                    "MON, " +
                    "YR, " +
                    "FAVORITE " +
                    "FROM VERSES " +
                    "WHERE FAVORITE = 1" +
                    " ORDER BY YR, " +
                    "MON, " +
                    "VERSE_TITLE_EN ";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            for (int i = 0; i< cursor.getCount(); i++) {
                Verses verses = new Verses();
                cursor.moveToNext();
                verses.set_id(cursor.getInt(0));
                verses.setVerse_title_kr(cursor.getString(1));
                verses.setVerse_title_en(cursor.getString(2));
                verses.setMon(cursor.getInt(3));
                verses.setYr(cursor.getInt(4));
                verses.setFavorite(cursor.getInt(5));
                favoriteList.add(verses);
            }
        }
        return favoriteList;
    }
}
