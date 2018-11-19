package com.aucklandvision.biblerecite.schedule;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainPresenter;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.CalendarEnglishVer;
import com.aucklandvision.biblerecite.utils.ThemeColor;

import java.util.ArrayList;

public class ScheduleMonthView extends LinearLayout {
    private UserInfo userInfo;
    private MainPresenter mainPresenter;
    private TextView text_versemonth;
    private TextView text_versenum;
    private TextView text_versetitle;
    private CheckBox checkBox_kr;
    private CheckBox checkBox_en;
    private Verses verses;
    private ArrayList<Verses> listVerses;
    private int position;
    private ScheduleMonthAdapter scheduleMonthAdapter;

    public ScheduleMonthView(Context context, UserInfo userInfo, MainPresenter mainPresenter, ScheduleMonthAdapter scheduleMonthAdapter) {
        super(context);
        this.userInfo = userInfo;
        this.mainPresenter = mainPresenter;
        this.scheduleMonthAdapter = scheduleMonthAdapter;
        init(context);
    }

    public ScheduleMonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_verse, this, true);

        text_versemonth = (TextView) findViewById(R.id.text_versemonth);
        text_versenum = (TextView) findViewById(R.id.text_versenum);
        text_versetitle = (TextView) findViewById(R.id.text_versetitle);

        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {ThemeColor.ARGB, ThemeColor.ARGB};
        checkBox_kr = (CheckBox) findViewById(R.id.check_remember_kr);
        checkBox_en = (CheckBox) findViewById(R.id.check_remember_en);
        CompoundButtonCompat.setButtonTintList(checkBox_kr, new ColorStateList(states, colors));
        CompoundButtonCompat.setButtonTintList(checkBox_en, new ColorStateList(states, colors));

        checkBox_kr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainPresenter.updateRememberCheckboxInSchedule(verses.get_id(), isChecked == true ? 1 : 0, 0);
                listVerses.get(position).setCheck_remember_kr(isChecked == true ? 1 : 0);
                scheduleMonthAdapter.setItems(listVerses);
                scheduleMonthAdapter.notifyDataSetChanged();
            }
        });
        checkBox_en.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainPresenter.updateRememberCheckboxInSchedule(verses.get_id(), isChecked == true ? 1 : 0, 1);
                listVerses.get(position).setCheck_remember_en(isChecked == true ? 1 : 0);
                scheduleMonthAdapter.setItems(listVerses);
                scheduleMonthAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setVerses(Verses verses, ArrayList<Verses> listVerses, int position) {
        this.verses = verses;
        this.listVerses = listVerses;
        this.position = position;

        if (userInfo.getLanguage() == 0) {
            text_versemonth.setText(verses.getMon() + "월");
            text_versetitle.setText(verses.getVerse_title_kr());
        } else {
            text_versemonth.setText(CalendarEnglishVer.calMonth(verses.getMon()));
            text_versetitle.setText(verses.getVerse_title_en());
        }

        if (verses.getCountnum() != 1) {
            text_versemonth.setTextColor(Color.WHITE);
        } else {
            text_versemonth.setTextColor(0xff666666);
        }
        text_versenum.setText(verses.getCountnum()+". ");
        checkBox_kr.setChecked(verses.getCheck_remember_kr() == 0 ? false : true);
        checkBox_en.setChecked(verses.getCheck_remember_en() == 0 ? false : true);
    }
}
