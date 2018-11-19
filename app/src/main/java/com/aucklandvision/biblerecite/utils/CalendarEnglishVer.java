package com.aucklandvision.biblerecite.utils;

public class CalendarEnglishVer {
    public static String calMonth(int mon) {
        String monstr = "";
        switch (mon) {
            case 1: monstr = "Jan"; break;
            case 2: monstr = "Feb"; break;
            case 3: monstr = "Mar"; break;
            case 4: monstr = "Apr"; break;
            case 5: monstr = "May"; break;
            case 6: monstr = "Jun"; break;
            case 7: monstr = "Jul"; break;
            case 8: monstr = "Aug"; break;
            case 9: monstr = "Sep"; break;
            case 10: monstr = "Oct"; break;
            case 11: monstr = "Nov"; break;
            case 12: monstr = "Dec"; break;
        }
        return monstr;
    }
}
