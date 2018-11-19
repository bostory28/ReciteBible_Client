package com.aucklandvision.biblerecite.schedule;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.utils.ThemeColor;

public class ScheduleYearView extends LinearLayout {
    private UserInfo userInfo;
    private TextView text_schedule_year;
    private TextView text_arrow;
    public ScheduleYearView(Context context, UserInfo userInfo) {
        super(context);
        this.userInfo = userInfo;
        init(context);
    }

    public ScheduleYearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_year, this, true);

        text_schedule_year = (TextView) findViewById(R.id.text_schedule_year);
        text_arrow = (TextView) findViewById(R.id.text_arrow);
    }

    public void setYear(int year) {
        text_schedule_year.setText(String.valueOf(year));
        if (text_schedule_year.getText().equals(String.valueOf(userInfo.getCurYr()))) {
            text_arrow.setTextColor(ThemeColor.ARGB);
            ScheduleDialog.prevViewSearch = this;
        }
    }
}
