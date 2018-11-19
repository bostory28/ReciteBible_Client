package com.aucklandvision.biblerecite.font;

import java.util.ArrayList;

public class FontPresenter {
    private FontRepository fontRepository;

    public FontPresenter() {
        fontRepository = new FontRepository();
    }

    public ArrayList getFontInfo() {
        ArrayList<Object> fontList = fontRepository.getFontInfo();
        return fontList;
    }

    public void updateFontSize(int fontSize) {
        fontRepository.updateFontSize(fontSize);
    }

    public void updateFontType(String fontType) {
        fontRepository.updateFontType(fontType);
    }
}
