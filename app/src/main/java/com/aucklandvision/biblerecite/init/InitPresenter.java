package com.aucklandvision.biblerecite.init;

import com.aucklandvision.biblerecite.main.UserInfo;

public class InitPresenter {
    private InitRepository initRepository;

    public InitPresenter() {
        initRepository = new InitRepository();
    }

    public UserInfo getInit() {
        UserInfo userInfo = initRepository.getInit();
        return userInfo;
    }
}
