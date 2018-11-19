package com.aucklandvision.biblerecite.search;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.CalendarEnglishVer;

public class SearchVersesView extends LinearLayout {
    private int searchLanguage;
    private TextView text_verse_title;
    private TextView text_verse_month_en;
    private TextView text_verse_month_kr;
    private TextView text_verse_content;
    private TextView text_verse_year;
    public SearchVersesView(Context context, int searchLanguage) {
        super(context);
        this.searchLanguage = searchLanguage;
        init(context);
    }

    public SearchVersesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_verse, this, true);

        text_verse_title = (TextView) findViewById(R.id.text_verse_title);
        text_verse_month_en = (TextView) findViewById(R.id.text_verse_month_en);
        text_verse_month_kr = (TextView) findViewById(R.id.text_verse_month_kr);
        text_verse_content = (TextView) findViewById(R.id.text_verse_content);
        text_verse_year = (TextView) findViewById(R.id.text_verse_year);
    }

    public void setVerse(Verses verses) {
        if (searchLanguage == 0) {
            text_verse_title.setText(verses.getVerse_title_kr());
            text_verse_month_en.setText("");
            text_verse_year.setText(String.valueOf(verses.getYr()+"/"));
            text_verse_month_kr.setText(String.valueOf(verses.getMon()));
            text_verse_content.setText(verses.getVerse_kr());
        } else {
            text_verse_title.setText(verses.getVerse_title_en());
            text_verse_month_en.setText(CalendarEnglishVer.calMonth(verses.getMon()));
            text_verse_year.setText("/"+String.valueOf(verses.getYr()));
            text_verse_month_kr.setText("");
            text_verse_content.setText(verses.getVerse_en());
        }
    }
}
