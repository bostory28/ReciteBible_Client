package com.aucklandvision.biblerecite.schedule;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.UserInfo;

import java.util.ArrayList;

public class ScheduleYearAdapter extends BaseAdapter {
    private ArrayList<Integer> items = new ArrayList<>();
    private MainActivity mainActivity;
    private UserInfo userInfo;

    public ScheduleYearAdapter(MainActivity mainActivity, UserInfo userInfo) {
        this.mainActivity = mainActivity;
        this.userInfo = userInfo;
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
        ScheduleYearView scheduleYearView = null;
        if (convertView == null) {
            scheduleYearView = new ScheduleYearView(mainActivity, userInfo);
        } else {
            scheduleYearView = (ScheduleYearView) convertView;
        }
        scheduleYearView.setYear(items.get(position));

        ViewYearHolder viewHolder = new ViewYearHolder();
        viewHolder.text_schedule_year = scheduleYearView.findViewById(R.id.text_schedule_year);
        viewHolder.text_arrow = scheduleYearView.findViewById(R.id.text_arrow);
        scheduleYearView.setTag(viewHolder);

        return scheduleYearView;
    }

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }
}
