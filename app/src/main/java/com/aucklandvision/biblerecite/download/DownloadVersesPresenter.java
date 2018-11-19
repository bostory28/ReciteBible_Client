package com.aucklandvision.biblerecite.download;

import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

import vo.UpdateVO;
import vo.VersesVO;

public class DownloadVersesPresenter {
    private DownloadVersesRepository downloadVersesRepository;
    public DownloadVersesPresenter() {
        downloadVersesRepository = new DownloadVersesRepository();
    }

    public int getCountVerses() {
        int countAllVerses = downloadVersesRepository.getCountVerses();
        return countAllVerses;
    }

    public String getUpdateDate() {
        String lastDate = downloadVersesRepository.getUpdateDate();
        return lastDate;
    }

    public void addVerses(ArrayList<VersesVO> listAllVerses) {
        for (int i = 0; i < listAllVerses.size(); i++) {
            Verses verses = new Verses();
            verses.set_id_server(listAllVerses.get(i).getVerses_sq());
            verses.setVerse_title_kr(listAllVerses.get(i).getVerse_title_kr());
            verses.setVerse_title_en(listAllVerses.get(i).getVerse_title_en());
            verses.setVerse_kr(listAllVerses.get(i).getVerse_kr());
            verses.setVerse_en(listAllVerses.get(i).getVerse_en());
            verses.setVerse_section(listAllVerses.get(i).getVerse_section());
            verses.setYr(listAllVerses.get(i).getYr());
            verses.setMon(listAllVerses.get(i).getMon());
            verses.setVersion(listAllVerses.get(i).getVersion());
            verses.setFavorite(0);
            verses.setCheck_remember_kr(0);
            verses.setCheck_remember_en(0);
            verses.setQuiz_level(0);
            downloadVersesRepository.insertAllVerses(verses);
        }
    }

    public void updateUserInfoInInit(int year, UpdateVO updateVO) {
        downloadVersesRepository.updateUserInfoInInit(year, updateVO);
    }

    /* UpdateVO information
     * new -> update_type : 1
     * update -> update_type : 2
     * delete -> update_type : 3
     */
    public void processVersesUpdated(ArrayList<UpdateVO> listUpdate) {
        for (int i = 0; i < listUpdate.size(); i++) {

            switch (listUpdate.get(i).getUpdate_type()) {
                case 1:
                    downloadVersesRepository.insertVerseInUpdate(listUpdate.get(i));
                    break;
                case 2:
                    String strEn = listUpdate.get(i).getVerse_en();
                    int isExist = strEn.indexOf("\'");
                    if (isExist > 0) {
                        strEn = strEn.replace("'", "''");
                        listUpdate.get(i).setVerse_en(strEn);
                    }
                    downloadVersesRepository.updateVerseInUpdate(listUpdate.get(i));
                    break;
                case 3:
                    downloadVersesRepository.deleteVerseInUpdate(listUpdate.get(i));
                    break;
            }
        }
    }

    public void updateUserUpdateDate(ArrayList<UpdateVO> listUpdate, Verses verses) {
        int count = 0;
        for (int i = 0; i < listUpdate.size(); i++) {
            switch (listUpdate.get(i).getUpdate_type()) {
                case 1:
                    if (listUpdate.get(i).getYr() == verses.getYr() && listUpdate.get(i).getMon() < verses.getMon()) {
                        ++count;
                    } else if (listUpdate.get(i).getYr() == verses.getYr()
                            && listUpdate.get(i).getMon() == verses.getMon()
                            && listUpdate.get(i).getVerse_title_en().compareTo(verses.getVerse_title_en()) < 0) {
                        ++count;
                    } else if (listUpdate.get(i).getYr() < verses.getYr()) {
                        ++count;
                    }
                    break;
                case 3:
                    if (listUpdate.get(i).getYr() == verses.getYr()  && listUpdate.get(i).getMon() < verses.getMon()) {
                        --count;
                    } else if (listUpdate.get(i).getYr() == verses.getYr()
                            && listUpdate.get(i).getMon() == verses.getMon()
                            && listUpdate.get(i).getVerse_title_en().compareTo(verses.getVerse_title_en()) <= 0) {
                        --count;
                    } else if (listUpdate.get(i).getYr() < verses.getYr()) {
                        --count;
                    }
                    break;
            }
        }
        downloadVersesRepository.updateUserUpdateDate(listUpdate.get(listUpdate.size() - 1).getLastdate(), count);
    }
}
