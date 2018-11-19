package com.aucklandvision.biblerecite.main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;
import java.util.ArrayList;

public class MainRepository implements MainContract.Repository{
    private SQLiteDatabase sqLiteDatabase;

    public MainRepository() {
        sqLiteDatabase = OpenSqliteDatabase.getSqLiteDatabase();
    }

    @Override
    public ArrayList<Integer> getUserCurrInfo() {
        ArrayList<Integer> list = new ArrayList<>();
        if (sqLiteDatabase != null) {
            String sql = "SELECT CURRPAGE, CURRYR, LANGUAGES FROM USER";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            list.add(cursor.getInt(0));
            list.add(cursor.getInt(1));
            list.add(cursor.getInt(2));
        }
        return list;
    }

    public ArrayList<Integer> getCountNumVersesOfAllTense(int curYr) {
        ArrayList listYr = new ArrayList();
        if (sqLiteDatabase != null) {
            String sql = "SELECT " +
                    "(SELECT count(*) FROM VERSES WHERE YR < " + curYr + ") prevyr, " +
                    "(SELECT count(*) FROM VERSES WHERE YR = " + curYr + ") curryr, " +
                    "(SELECT count(*) FROM VERSES WHERE YR > " + curYr + ") nextyr " +
                    "FROM VERSES GROUP BY curryr";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            if (cursor.getCount() != 0) {
                cursor.moveToNext();
                listYr.add(cursor.getInt(0));
                listYr.add(cursor.getInt(1));
                listYr.add(cursor.getInt(2));
            }
        }
        return listYr;
    }

    @Override
    public ArrayList<Verses> getAllVersesOfCurrentYear(int curYr) {
        ArrayList<Verses> list = new ArrayList<Verses>();
        int mon = 0;
        int count = 0;
        if (sqLiteDatabase != null) {
            String sql = "SELECT _ID, " +
                    "_ID_SERVER, " +
                    "VERSE_TITLE_KR, " +
                    "VERSE_TITLE_EN, " +
                    "VERSE_KR, " +
                    "VERSE_EN, " +
                    "VERSION, " +
                    "MON, " +
                    "YR, " +
                    "FAVORITE, " +
                    "QUIZ_LEVEL," +
                    "CHECK_REMEMBER_KR, " +
                    "CHECK_REMEMBER_EN, " +
                    "VERSE_SECTION " +
                    "FROM VERSES " +
                    "WHERE YR = " + curYr +
                    " ORDER BY YR DESC, " +
                    "MON, " +
                    "VERSE_TITLE_EN ";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            for (int i = 0; i< cursor.getCount(); i++) {
                Verses verses = new Verses();
                cursor.moveToNext();
                verses.set_id(cursor.getInt(0));
                verses.set_id_server(cursor.getInt(1));
                verses.setVerse_title_kr(cursor.getString(2));
                verses.setVerse_title_en(cursor.getString(3));
                verses.setVerse_kr(cursor.getString(4));
                verses.setVerse_en(cursor.getString(5));
                verses.setVersion(cursor.getInt(6));
                verses.setMon(cursor.getInt(7));
                verses.setYr(cursor.getInt(8));
                verses.setFavorite(cursor.getInt(9));
                verses.setQuiz_level(cursor.getInt(10));
                verses.setCheck_remember_kr(cursor.getInt(11));
                verses.setCheck_remember_en(cursor.getInt(12));
                verses.setVerse_section(cursor.getInt(13));
                if (i == 0 || mon == cursor.getInt(7)) {
                    verses.setCountnum(++count);
                } else {
                    count = 0;
                    verses.setCountnum(++count);
                }
                mon = cursor.getInt(7);
                list.add(verses);
            }
        }
        return list;
    }

    @Override
    public void updateRememberCheckbox(int _id, int isChecked, int language) {
        if (sqLiteDatabase != null) {
            String columName = (language == 0 ? "CHECK_REMEMBER_KR": "CHECK_REMEMBER_EN");
            String sql = "UPDATE VERSES SET " + columName + " = " + isChecked +
                    " WHERE _ID = " + _id ;
            sqLiteDatabase.execSQL(sql);
        } else {
            Log.d("updateStateCheckbox", "VERSES StateCheckbox NOT UPDATED");
        }
    }

    @Override
    public void updateQuizLevel(int _id, int level) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE VERSES SET QUIZ_LEVEL = " + level +
                    " WHERE _ID = " + _id ;
            sqLiteDatabase.execSQL(sql);
        } else {
            Log.d("updateQuizLevel", "VERSES Quizlevel NOT UPDATED");
        }
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE USER SET CURRPAGE = ?, CURRYR = ?, LANGUAGES = ?" ;
            Object[] params = {userInfo.getCurPage(), userInfo.getCurYr(), userInfo.getLanguage()};
            sqLiteDatabase.execSQL(sql, params);
        } else {
            Log.d("updateUserInfo", "USER TABLE NOT UPDATED");
        }
    }

    @Override
    public ArrayList<Integer> getAllYear() {
        ArrayList<Integer> listYear = new ArrayList<>();
        if (sqLiteDatabase != null) {
            String sql = "SELECT YR FROM VERSES GROUP BY YR ORDER BY YR";
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                listYear.add(cursor.getInt(0));
            }
            Log.d("getAllYear", "GOT ALL YEAR");
        }
        return listYear;
    }

    @Override
    public int getPageNumSelectedInSchedule(int year) {
        int pageNum = 0;
        if (sqLiteDatabase != null) {
            String sql = "SELECT COUNT(*) FROM VERSES " +
                         "WHERE YR < " + year;
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            pageNum = cursor.getInt(0);
        }
        return pageNum;
    }

    @Override
    public int getPageNumSelectedInSearch(Verses searchVerses) {
        int pageNum = 0;
        if (sqLiteDatabase != null) {
            String sql = "SELECT COUNT(*) FROM VERSES " +
                         "WHERE YR < " + searchVerses.getYr();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            cursor.moveToNext();
            pageNum = cursor.getInt(0);

            sql = "SELECT _ID " +
                   "FROM VERSES " +
                   "WHERE YR = " + searchVerses.getYr() +
                   " ORDER BY MON, VERSE_TITLE_EN";
            cursor = sqLiteDatabase.rawQuery(sql, null);
            for (int i = 0; i< cursor.getCount(); i++) {
                cursor.moveToNext();
                if (searchVerses.get_id() == cursor.getInt(0)){
                    pageNum += i + 1;
                    break;
                }
            }
        }
        return pageNum;
    }

    public void updateFavoriteCheckbox(int _id, int isChecked) {
        if (sqLiteDatabase != null) {
            String sql = "UPDATE VERSES SET FAVORITE = " + isChecked +
                    " WHERE _ID = " + _id ;
            sqLiteDatabase.execSQL(sql);
        }
    }
}
