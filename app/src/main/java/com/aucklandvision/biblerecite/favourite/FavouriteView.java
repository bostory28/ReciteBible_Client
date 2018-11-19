package com.aucklandvision.biblerecite.favourite;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.CalendarEnglishVer;

public class FavouriteView extends LinearLayout {
    private UserInfo userInfo;
    private TextView text_favorite_title;
    private TextView text_favorite_yr;
    private TextView text_favorite_mon_en;
    private TextView text_favorite_mon_kr;
    private ImageView image_favorite_selected;
    public FavouriteView(Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;
        init(context);
    }

    public FavouriteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.favorite_content, this, true);

        text_favorite_title = (TextView) findViewById(R.id.text_favorite_title);
        text_favorite_yr = (TextView) findViewById(R.id.text_favorite_yr);
        text_favorite_mon_en = (TextView) findViewById(R.id.text_favorite_mon_en);
        text_favorite_mon_kr = (TextView) findViewById(R.id.text_favorite_mon_kr);
        image_favorite_selected = (ImageView) findViewById(R.id.image_favorite_selected);
    }

    public void setView(Verses view) {
        if (userInfo.getLanguage() == 0) {
            text_favorite_title.setText(view.getVerse_title_kr());
            text_favorite_yr.setText(view.getYr()+" / ");
            text_favorite_mon_kr.setText(String.valueOf(view.getMon()));
            text_favorite_mon_en.setText("");
        } else {
            text_favorite_title.setText(view.getVerse_title_en());
            text_favorite_yr.setText(" / " + view.getYr());
            text_favorite_mon_en.setText(CalendarEnglishVer.calMonth(view.getMon()));
            text_favorite_mon_kr.setText("");
        }

    }
}
