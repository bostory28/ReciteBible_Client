package com.aucklandvision.biblerecite.download;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;
import com.aucklandvision.biblerecite.main.Verses;

import vo.UpdateVO;

public class DownloadVersesRepository {
    private SQLiteDatabase sqLiteDatabase;

    public DownloadVersesRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    public int getCountVerses() {
        int countAllVerses = 0;
        if (sqLiteDatabase != null) {
            String sql = "SELECT COUNT(*) FROM VERSES";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            countAllVerses = cursor.getInt(0);
        }
        return countAllVerses;
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

    public void insertAllVerses(Verses verses) {
        if (sqLiteDatabase != null) {
            String sql = "INSERT INTO VERSES(" +
                    "_ID_SERVER, " +
                    "VERSE_TITLE_KR, " +
                    "VERSE_TITLE_EN, " +
                    "VERSE_SECTION, " +
                    "VERSE_KR, " +
                    "VERSE_EN, " +
                    "VERSION, " +
                    "MON, " +
                    "YR," +
                    "QUIZ_LEVEL," +
                    "FAVORITE," +
                    "CHECK_REMEMBER_KR," +
                    "CHECK_REMEMBER_EN) VALUES("+
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {verses.get_id_server(),
                    verses.getVerse_title_kr(),
                    verses.getVerse_title_en(),
                    verses.getVerse_section(),
                    verses.getVerse_kr(),
                    verses.getVerse_en(),
                    verses.getVersion(),
                    verses.getMon(),
                    verses.getYr(),
                    verses.getQuiz_level(),
                    verses.getFavorite(),
                    verses.getCheck_remember_kr(),
                    verses.getCheck_remember_en()};
            sqLiteDatabase.execSQL(sql, params);
        }
    }

    public void updateUserInfoInInit(int year, UpdateVO updateVO) {
        if (sqLiteDatabase != null) {
            if (sqLiteDatabase != null) {
                String sql = "UPDATE USER " +
                             "SET CURRPAGE = 1, " +
                                 "CURRYR = " + year + ", " +
                                 "_LASTDATE_SERVER = '" + updateVO.getLastdate() + "'";
                sqLiteDatabase.execSQL(sql);
            }
        }
    }

    public void insertVerseInUpdate(UpdateVO updateVO) {
        if (sqLiteDatabase != null) {
            String sql = "INSERT INTO VERSES(" +
                    "_ID_SERVER, " +
                    "VERSE_TITLE_KR, " +
                    "VERSE_TITLE_EN, " +
                    "VERSE_SECTION, " +
                    "VERSE_KR, " +
                    "VERSE_EN, " +
                    "VERSION, " +
                    "MON, " +
                    "YR, " +
                    "QUIZ_LEVEL," +
                    "FAVORITE," +
                    "CHECK_REMEMBER_KR," +
                    "CHECK_REMEMBER_EN) VALUES("+
                    "?, ?, ?, ?, ?, ?, 0, ?, ?, 0, 0, 0, 0)";
            Object[] params = {updateVO.getVerses_sq(),
                    updateVO.getVerse_title_kr(),
                    updateVO.getVerse_title_en(),
                    updateVO.getVerse_section(),
                    updateVO.getVerse_kr(),
                    updateVO.getVerse_en(),
                    updateVO.getMon(),
                    updateVO.getYr()};
            sqLiteDatabase.execSQL(sql, params);
        }
    }

    public void updateUserUpdateDate(String lastDate, int count) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE USER " +
                    "SET CURRPAGE = CASE " +
                                   "WHEN ( " + count + " + (SELECT CURRPAGE FROM USER)) <= 0 THEN 1 " +
                                   "ELSE ( " + count + " + (SELECT CURRPAGE FROM USER)) END, " +
                    " _LASTDATE_SERVER = '" + lastDate + "'";
            sqLiteDatabase.execSQL(sql);
        }
    }

    public void updateVerseInUpdate(UpdateVO updateVO) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE VERSES " +
                         "SET VERSE_TITLE_KR = '" + updateVO.getVerse_title_kr() + "', " +
                             "VERSE_TITLE_EN = '" + updateVO.getVerse_title_en() + "', " +
                             "VERSE_SECTION = " + updateVO.getVerse_section() + ", " +
                             "VERSE_KR = '" + updateVO.getVerse_kr() + "', " +
                             "VERSE_EN = '" + updateVO.getVerse_en() + "', " +
                             "MON = " + updateVO.getMon() + ", " +
                             "YR = " + updateVO.getYr() +
                        " WHERE _ID_SERVER = " + updateVO.getVerses_sq();
            sqLiteDatabase.execSQL(sql);
        }
    }

    public void deleteVerseInUpdate(UpdateVO updateVO) {
        if (sqLiteDatabase != null) {
            String sql = "DELETE FROM VERSES " +
                        " WHERE _ID_SERVER = " + updateVO.getVerses_sq();
            sqLiteDatabase.execSQL(sql);
        }
    }
}
