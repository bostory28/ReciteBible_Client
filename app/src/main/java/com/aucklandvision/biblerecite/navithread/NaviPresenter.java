package com.aucklandvision.biblerecite.navithread;
//ver1.1.1
public class NaviPresenter {
    private NaviRepository naviRepository;

    public NaviPresenter() {
        naviRepository = new NaviRepository();
    }
    public String getUpdateDate() {
        String lastDate = naviRepository.getUpdateDate();
        return lastDate;
    }
}
