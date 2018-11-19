package com.aucklandvision.biblerecite.search;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.SearchPagingPrecess;

import java.util.ArrayList;

public class SearchRepository {
    private SQLiteDatabase sqLiteDatabase;

    public SearchRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    public int getNumTotalRowInSearch(ArrayList<Object> searchItemList) {
        int countTotalRow = 0;
        String columnName = "";
        String sqlSection = "";
        String searchQuery = (String) searchItemList.get(0);
        int language = (Integer) searchItemList.get(1);
        int section = (Integer) searchItemList.get(2);

        if (language == 0) columnName = "VERSE_KR";
        else columnName = "VERSE_EN";
        if (section != 0) sqlSection = "AND VERSE_SECTION = " + section ;

        if (sqLiteDatabase != null) {
            String sql = "SELECT COUNT(*) " +
                    "FROM VERSES " +
                    "WHERE "+ columnName + " LIKE '%" + searchQuery + "%' " +
                    sqlSection +
                    "";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            countTotalRow = cursor.getInt(0);
        }
        return countTotalRow;
    }

    public ArrayList<Verses> getListSearchVerses(ArrayList<Object> searchItemList, SearchPagingPrecess searchPagingPrecess) {
        ArrayList<Verses> list = new ArrayList<Verses>();
        String columnName = "";
        String sqlSection = "";
        String searchQuery = (String) searchItemList.get(0);
        int language = (Integer) searchItemList.get(1);
        int section = (Integer) searchItemList.get(2);
        if (language == 0) columnName = "VERSE_KR";
        else columnName = "VERSE_EN";
        if (section != 0) sqlSection = "AND VERSE_SECTION = " + section ;

        if (sqLiteDatabase != null) {
            String sql = "SELECT _ID, " +
                    "VERSE_TITLE_KR, " +
                    "VERSE_TITLE_EN, " +
                    "VERSE_KR, " +
                    "VERSE_EN, " +
                    "MON, " +
                    "YR, " +
                    "VERSE_SECTION " +
                    "FROM VERSES " +
                    "WHERE "+ columnName + " LIKE '%" + searchQuery + "%' " +
                    sqlSection +
                    " ORDER BY YR DESC, " +
                    "MON DESC, " +
                    "VERSE_TITLE_EN " +
                    "LIMIT "+ searchPagingPrecess.getNumberToShowAtOnce() + " OFFSET " + searchPagingPrecess.getNumberOfOffset();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            for (int i = 0; i< cursor.getCount(); i++) {
                Verses verses = new Verses();
                cursor.moveToNext();
                verses.set_id(cursor.getInt(0));
                verses.setVerse_title_kr(cursor.getString(1));
                verses.setVerse_title_en(cursor.getString(2));
                verses.setVerse_kr(cursor.getString(3));
                verses.setVerse_en(cursor.getString(4));
                verses.setMon(cursor.getInt(5));
                verses.setYr(cursor.getInt(6));
                verses.setVerse_section(cursor.getInt(7));
                list.add(verses);
            }
        }
        return list;
    }
}
