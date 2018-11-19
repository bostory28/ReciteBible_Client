package com.aucklandvision.biblerecite.main;

import android.view.MotionEvent;
import android.view.View;
import com.aucklandvision.biblerecite.schedule.ViewMonthHolder;
import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter {
    private MainRepository mainRepository;

    //Model
    private ArrayList<Verses> listVerses;
    private Verses verses;
    private UserInfo userInfo;

    //page information
    private int countTotalPage = 0;         //count page totally
    private int indexCurrPage = 0;          //page number of arraylist
    private int countPrevYear = 0;          //count page of previous year
    private int countCurrYear = 0;          //count page of current year
    private int countNextYear = 0;          //count page of future year

    //touch event
    private float x1;
    private float x2;
    private float y1;
    private float y2;
    private final int DISTANCE_CONVERT_VERSE = 150;
    private final int DISTANCE_SHOW_QIUZDIALOG = 30;

    //schedule
    private ViewMonthHolder viewMonthHolder;
    private int pageNumSelected;

    public MainPresenter() {
        setMainRepository();
    }

    @Override
    public void setMainRepository() {
        mainRepository = new MainRepository();
    }

    @Override
    public UserInfo getCurrUserInfo() {
        ArrayList<Integer> listPage = null;
        userInfo = new UserInfo();
        listPage = mainRepository.getUserCurrInfo();
        userInfo.setCurPage(listPage.get(0));
        userInfo.setCurYr(listPage.get(1));
        userInfo.setLanguage(listPage.get(2));

        getCurrPageInfo(userInfo.getCurYr(), userInfo.getCurPage());
        return userInfo;
    }

    public void getCurrPageInfo(int curYear, int curPage) {
        //all verse list of current year
        getAllVersesOfCurrentYear(curYear);
        //get count of prev year, current year, next year
        getCountNumVersesOfAllTense(curYear);
        //total index of current year
        getIndexOfCurrentYear(curPage);
        //total page
        getCountNumOfTotalPage();
    }

    public void getAllVersesOfCurrentYear(int year) {
        listVerses = mainRepository.getAllVersesOfCurrentYear(year);
    }
    public void getCountNumVersesOfAllTense(int year) {
        ArrayList<Integer> listYr = mainRepository.getCountNumVersesOfAllTense(year);
        if (listYr.size() != 0) {
            countPrevYear = listYr.get(0);
            countCurrYear = listYr.get(1);
            countNextYear = listYr.get(2);
        }
    }

    public int getIndexForDelete() {
        ArrayList<Integer> listYr = mainRepository.getCountNumVersesOfAllTense(userInfo.getCurYr());
        int prevYear = 0;
        int index = 0;
        if (listYr.size() != 0) {
            prevYear = listYr.get(0);
        }
        index = userInfo.getCurPage() - prevYear - 1;
        return index;
    }
    public void getIndexOfCurrentYear(int page) {
        indexCurrPage = page - countPrevYear - 1;
    }
    public void getCountNumOfTotalPage() {
        countTotalPage = countPrevYear + countCurrYear + countNextYear;
    }

    @Override
    public Verses getCurrVerse() {
        verses = new Verses();
        if (indexCurrPage >= 0 && indexCurrPage < listVerses.size()) {
            verses = setVerseIntoModel();
        } else {
            if (indexCurrPage < 0) {
                userInfo.setCurYr(userInfo.getCurYr() - 1);

                getCurrPageInfo(userInfo.getCurYr(), userInfo.getCurPage());
                verses = setVerseIntoModel();
            } else if (indexCurrPage >=  listVerses.size()) {
                userInfo.setCurYr(userInfo.getCurYr() + 1);

                getCurrPageInfo(userInfo.getCurYr(), userInfo.getCurPage());
                verses = setVerseIntoModel();
            }
        }
        return verses;
    }

    public Verses setVerseIntoModel() {
        if (listVerses.size() != 0) {
            verses = new Verses();
            verses.set_id(listVerses.get(indexCurrPage).get_id());
            verses.set_id_server(listVerses.get(indexCurrPage).get_id_server());
            verses.setVerse_title_kr(listVerses.get(indexCurrPage).getVerse_title_kr());
            verses.setVerse_title_en(listVerses.get(indexCurrPage).getVerse_title_en());
            verses.setVerse_kr(listVerses.get(indexCurrPage).getVerse_kr());
            verses.setVerse_en(listVerses.get(indexCurrPage).getVerse_en());
            verses.setMon(listVerses.get(indexCurrPage).getMon());
            verses.setYr(listVerses.get(indexCurrPage).getYr());
            verses.setCheck_remember_kr(listVerses.get(indexCurrPage).getCheck_remember_kr());
            verses.setCheck_remember_en(listVerses.get(indexCurrPage).getCheck_remember_en());
            verses.setQuiz_level(listVerses.get(indexCurrPage).getQuiz_level());
            verses.setFavorite(listVerses.get(indexCurrPage).getFavorite());
        }
        return verses;
    }

    @Override
    public void updateRememberCheckbox(int _id, int isChecked, int language) {
        mainRepository.updateRememberCheckbox(_id, isChecked, language);
        if (language == 0) listVerses.get(indexCurrPage).setCheck_remember_kr(isChecked);
        else listVerses.get(indexCurrPage).setCheck_remember_en(isChecked);
    }

    @Override
    public int decideTouchEventSelected(MotionEvent event) {
        int whichTouch = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                x2 = event.getX();
                y2 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(x1 - x2) - Math.abs(y1 - y2) > DISTANCE_CONVERT_VERSE) {
                    //convert verse
                    whichTouch = 1;
                } else if(Math.abs(x1 - x2) <= DISTANCE_SHOW_QIUZDIALOG && Math.abs(y1 - y2) <= DISTANCE_SHOW_QIUZDIALOG) {
                    //show quiz dialog
                    whichTouch = 2;
                } else {
                    whichTouch = 3;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                y2 = event.getY();
                break;
        }
        return whichTouch;
    }

    public ArrayList<Object> getModelsMovedIntoPage() {
        ArrayList<Object> listModel = new ArrayList<>();
        if (x1 - DISTANCE_CONVERT_VERSE > x2) {
            if (userInfo.getCurPage() < countTotalPage) {
                indexCurrPage += 1;
                userInfo.setCurPage(userInfo.getCurPage() + 1);
                verses = getCurrVerse();

                listModel.add(0);
                listModel.add(userInfo);
                listModel.add(verses);
            } else {
                //end of page
                listModel.add(2);
            }
        } else if (x1 < x2 - DISTANCE_CONVERT_VERSE) {
            if (userInfo.getCurPage() > 1) {
                indexCurrPage -= 1;
                userInfo.setCurPage(userInfo.getCurPage() - 1);
                verses = getCurrVerse();

                listModel.add(0);
                listModel.add(userInfo);
                listModel.add(verses);
            } else {
                //beginning of page
                listModel.add(1);
            }
        }
        return listModel;
    }

    @Override
    public void updateQuizLevel(int _id, int level) {
        mainRepository.updateQuizLevel(_id, level);
        listVerses.get(indexCurrPage).setQuiz_level(level);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        mainRepository.updateUserInfo(userInfo);
    }

    @Override
    public ArrayList<Integer> getAllYear() {
        ArrayList<Integer> listYear = mainRepository.getAllYear();
        return listYear;
    }

    @Override
    public Verses onScheduleMonthItemClick(View view, int position) {
        viewMonthHolder = (ViewMonthHolder) view.getTag();
        pageNumSelected = mainRepository.getPageNumSelectedInSchedule(viewMonthHolder.year);
        pageNumSelected += position + 1;
        getCurrPageInfo(viewMonthHolder.year, pageNumSelected);

        verses = setVerseIntoModel();
        userInfo.setCurYr(viewMonthHolder.year);
        userInfo.setCurPage(pageNumSelected);
        return verses;
    }

    @Override
    public void updateRememberCheckboxInSchedule(int _id, int isChecked, int language) {
        mainRepository.updateRememberCheckbox(_id, isChecked, language);
    }

    @Override
    public Verses onSearchVerseItemClick(Verses searchVerses, int searchLanguage) {
        pageNumSelected = mainRepository.getPageNumSelectedInSearch(searchVerses);
        getCurrPageInfo(searchVerses.getYr(), pageNumSelected);

        verses = setVerseIntoModel();
        userInfo.setCurYr(searchVerses.getYr());
        userInfo.setCurPage(pageNumSelected);
        userInfo.setLanguage(searchLanguage);
        return verses;
    }

    public void updateFavoriteCheckbox(int _id, int isChecked) {
        mainRepository.updateFavoriteCheckbox(_id, isChecked);
        listVerses.get(indexCurrPage).setFavorite(isChecked);
    }

    public ArrayList<Verses> getListVerses() {
        return listVerses;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
