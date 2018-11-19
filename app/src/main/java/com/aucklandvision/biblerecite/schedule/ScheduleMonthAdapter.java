package com.aucklandvision.biblerecite.schedule;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aucklandvision.biblerecite.main.MainPresenter;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

public class ScheduleMonthAdapter extends BaseAdapter {
    private ArrayList<Verses> items = new ArrayList<>();
    private Context context;
    private UserInfo userInfo;
    private MainPresenter mainPresenter;

    public ScheduleMonthAdapter(Context context, UserInfo userInfo, MainPresenter mainPresenter) {
        this.context = context;
        this.userInfo = userInfo;
        this.mainPresenter = mainPresenter;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScheduleMonthView scheduleMonthView = null;
        if (convertView == null) {
            scheduleMonthView = new ScheduleMonthView(context, userInfo, mainPresenter, this);
        } else {
            scheduleMonthView = (ScheduleMonthView) convertView;
        }

        scheduleMonthView.setVerses(items.get(position), items, position);

        ViewMonthHolder viewMonthHolder = new ViewMonthHolder();
        viewMonthHolder.year = items.get(position).getYr();
        scheduleMonthView.setTag(viewMonthHolder);

        return scheduleMonthView;
    }

    public void setItems(ArrayList<Verses> items) {
        this.items = items;
    }
}
