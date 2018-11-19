package com.aucklandvision.biblerecite.main;

import android.view.MotionEvent;
import java.util.ArrayList;

public interface MainContract {
    interface View {
        //void openDatabase();
        void setMainPresenter();

        //model
        void getUserinfo();
        void getVerse();

        //show
        void showToolbarTitle();
        void showRememberCheckbox();
        void showVerse();

        //event
        void updateRememberCheckbox();
        void touchEventOfMainView();

        //quiz
        void showQuizLevelDialog();
    }
    interface Presenter {
        //DB
        void setMainRepository();

        //model
        UserInfo getCurrUserInfo();
        void updateUserInfo(UserInfo userInfo);
        Verses getCurrVerse();

        //event
        void updateRememberCheckbox(int _id, int isChecked, int language);
        int decideTouchEventSelected(MotionEvent event);

        //quiz
        void updateQuizLevel(int _id, int level);

        //schedule
        ArrayList<Integer> getAllYear();
        Verses onScheduleMonthItemClick(android.view.View view, int position);
        void updateRememberCheckboxInSchedule(int _id, int isChecked, int language);

        //search
        Verses onSearchVerseItemClick(Verses searchVerses, int searchLanguage);
    }
    interface Repository {
        //userinfo
        ArrayList<Integer> getUserCurrInfo();
        void updateUserInfo(UserInfo userInfo);

        //verse
        ArrayList<Verses> getAllVersesOfCurrentYear(int curYr);

        //event
        void updateRememberCheckbox(int _id, int isChecked, int language);

        //quiz
        void updateQuizLevel(int _id, int level);

        //schedule
        ArrayList<Integer> getAllYear();
        int getPageNumSelectedInSchedule(int year);

        //search
        int getPageNumSelectedInSearch(Verses searchVerses);
    }
}
