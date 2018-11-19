package com.aucklandvision.biblerecite.theme;

import com.aucklandvision.biblerecite.main.MainRepository;

public class ThemePresenter {
    private ThemeRepository themeRepository;

    public ThemePresenter() {
        themeRepository = new ThemeRepository();
    }

    public void updateTheme(String colorName, String argb_content) {
        themeRepository.updateTheme(colorName, argb_content);
    }
}
