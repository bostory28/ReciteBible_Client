package com.aucklandvision.biblerecite.utils;

import android.content.Intent;

import com.aucklandvision.biblerecite.MainActivity;

public class BadgeUtil {
    public static int badgeCount = 0;
    public static String lastDateClient;

    private MainActivity mainActivity;
    public BadgeUtil(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setInitBadgeNumber() {
        BadgeUtil.badgeCount = 0;
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", BadgeUtil.badgeCount);
        intent.putExtra("badge_count_package_name","com.aucklandvision.biblerecite");
        intent.putExtra("badge_count_class_name", "com.aucklandvision.biblerecite.MainActivity");
        mainActivity.sendBroadcast(intent);
    }
}
