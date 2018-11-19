package com.aucklandvision.biblerecite.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;
import java.util.Random;

public class SetVerseByLevel {
    private SpannableStringBuilder ssbAll;      //all verses level adjusted
    private SpannableStringBuilder ssb;         //a verse level adjusted
    private String verse;                       //receive verse from different language
    private int level;                          //quiz level
    private int language;                       //language english or korean

    private String[] arrVerses;                 //to change verses into each verse
    private String[] arrVerse;                  //to change a verse into each word

    private ArrayList<Integer> randomArray;     //save random number in an Arraylist
    private Random random;                      //random object
    private int tempRandom;                     //random number
    private boolean isExist;                    //to avoid the same random number

    private int countNum;                       //counting number of text in Verses

    public SpannableStringBuilder setVerseByLevel(Verses verses, UserInfo userInfo) {
        if (verses.get_id() != 0) {
            verse = "";
            level = verses.getQuiz_level();
            language = userInfo.getLanguage();
            if (language == 0)
                verse = verses.getVerse_kr();
            else
                verse = verses.getVerse_en();
            arrVerses = verse.split("\n");
            arrVerse = null;
            ssb = null;
            ssbAll = new SpannableStringBuilder();
            randomArray = null;
            tempRandom = 0;
            countNum = 0;
            isExist = false;

            if (level == 0 && language == 0) {
                ssbAll.append(verses.getVerse_kr());
                return ssbAll;
            } else if (level == 0 && language == 1) {
                ssbAll.append(verses.getVerse_en());
                return ssbAll;
            }

            level++;
            for (int k = 0; k < arrVerses.length; k++) {
                ssb = new SpannableStringBuilder(arrVerses[k]);
                arrVerse = arrVerses[k].split(" ");

                switch (level) {
                    case 4:
                        countNum = arrVerse[0].length() + 1;
                        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#" + ThemeColor.STRARGB.substring(4))),
                                countNum, arrVerses[k].length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        countNum = 0;
                        break;
                    default:
                        randomArray = new ArrayList<>();
                        random = new Random();

                        for (int i = 0; i < arrVerse.length - arrVerse.length / level; i++) {
                            isExist = false;
                            tempRandom = random.nextInt((arrVerse.length - 1) - 1 + 1) + 1;

                            for (int j = 0; j < randomArray.size(); j++) {
                                if (randomArray.get(j) == tempRandom) {
                                    isExist = true;
                                    break;
                                }
                            }
                            if (isExist == false) {
                                randomArray.add(tempRandom);
                            } else {
                                --i;
                            }
                        }

                        for (int j = 0; j < randomArray.size(); j++) {
                            for (int i = 0; i < randomArray.get(j); i++) {
                                countNum += arrVerse[i].length() + 1;
                            }
                            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#" + ThemeColor.STRARGB.substring(4))),
                                    countNum, countNum + arrVerse[randomArray.get(j)].length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            countNum = 0;
                        }
                }
                if (k != 0) ssbAll.append("\n");
                ssbAll.append(ssb);
            }
        } else {
            ssbAll = new SpannableStringBuilder();
            ssbAll.clear();
        }
        return ssbAll;
    }

    public SpannableStringBuilder showVerseInstantly(Verses verses, UserInfo userInfo) {
        language = userInfo.getLanguage();
        ssbAll = new SpannableStringBuilder();

        if (language == 0) {
            ssbAll.append(verses.getVerse_kr());
        } else if (language == 1) {
            ssbAll.append(verses.getVerse_en());
        }
        return ssbAll;
    }
}
