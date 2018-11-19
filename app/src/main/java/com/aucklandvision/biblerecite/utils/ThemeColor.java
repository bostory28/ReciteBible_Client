package com.aucklandvision.biblerecite.utils;

import android.content.Context;

import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.theme.Theme;

import java.util.ArrayList;

public class ThemeColor {
    private Context context;
    private String[] colorNames = {"Gray", "Dark Gray", "Green", "Red", "Pink", "Purple", "Deep Purple", "Indigo", "Blue", "Teal", "Orange", "Brown"};
    private int[] argb_contents = {0xffBDBDBD, 0xff626365, 0xff59A982, 0xffE66D75, 0xffF4BABD, 0xff9F7EB3, 0xff4F2085, 0xff3A5A9A, 0xff8AB1D2, 0xff019788, 0xffEFB66B, 0xff785648};
    private int[] argb_systems = {0xff757575, 0xff626365, 0xff366B4D, 0xffD04C4D, 0xffDB7475, 0xff704988, 0xff310D5A, 0xff27437E, 0xff809FB4, 0xff057F74, 0xffEFB66B, 0xff37160B};

    public static int ARGB;
    public static String STRARGB;

    public ThemeColor(Context context) {
        this.context = context;
    }

    public ArrayList<Theme> getThemeList() {
        ArrayList<Theme> listTheme = new ArrayList<>();
        for (int i = 0; i < colorNames.length; i++) {
            Theme theme = new Theme();
            theme.setColorName(colorNames[i]);
            theme.setArgb_content(argb_contents[i]);
            theme.setArgb_system(argb_systems[i]);
            listTheme.add(theme);
        }
        return listTheme;
    }

    public void applyTheme(String themeName) {
        if (themeName.equals(colorNames[0])) {
            context.setTheme(R.style.AppTheme_Gray);
        } else if (themeName.equals(colorNames[1])) {
            context.setTheme(R.style.AppTheme_DarkGray);
        } else if (themeName.equals(colorNames[2])) {
            context.setTheme(R.style.AppTheme_Green);
        } else if (themeName.equals(colorNames[3])) {
            context.setTheme(R.style.AppTheme_Red);
        } else if (themeName.equals(colorNames[4])) {
            context.setTheme(R.style.AppTheme_Pink);
        } else if (themeName.equals(colorNames[5])) {
            context.setTheme(R.style.AppTheme_Purple);
        } else if (themeName.equals(colorNames[6])) {
            context.setTheme(R.style.AppTheme_DeepPurple);
        } else if (themeName.equals(colorNames[7])) {
            context.setTheme(R.style.AppTheme_Indigo);
        } else if (themeName.equals(colorNames[8])) {
            context.setTheme(R.style.AppTheme_Blue);
        } else if (themeName.equals(colorNames[9])) {
            context.setTheme(R.style.AppTheme_Teal);
        } else if (themeName.equals(colorNames[10])) {
            context.setTheme(R.style.AppTheme_Orange);
        } else if (themeName.equals(colorNames[11])) {
            context.setTheme(R.style.AppTheme_Brown);
        }
    }
}
