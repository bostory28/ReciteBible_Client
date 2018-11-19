package com.aucklandvision.biblerecite.theme;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aucklandvision.biblerecite.MainActivity;

import java.util.ArrayList;

public class ThemeApdater extends BaseAdapter {
    private ThemeView themeView;
    private ArrayList<Theme> items = new ArrayList<>();
    private MainActivity mainActivity;

    public ThemeApdater(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
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
        themeView = null;
        if (convertView == null) {
            themeView = new ThemeView(mainActivity);
        } else {
            themeView = (ThemeView) convertView;
        }

        themeView.setView(items.get(position));
        return themeView;
    }

    public void setItems(ArrayList<Theme> items) {
        this.items = items;
    }
}
