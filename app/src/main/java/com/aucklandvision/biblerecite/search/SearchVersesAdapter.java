package com.aucklandvision.biblerecite.search;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

public class SearchVersesAdapter extends BaseAdapter {
    private ArrayList<Verses> items = new ArrayList<>();
    private MainActivity mainActivity;
    private int searchLanguage;
    private SearchVersesView searchVersesView;

    public SearchVersesAdapter(MainActivity mainActivity, int searchLanguage) {
        this.mainActivity = mainActivity;
        this.searchLanguage = searchLanguage;
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
        searchVersesView = null;
        if (convertView == null) {
            searchVersesView = new SearchVersesView(mainActivity, searchLanguage);
        } else {
            searchVersesView = (SearchVersesView) convertView;
        }
        searchVersesView.setVerse(items.get(position));

        ViewSearchHolder viewSearchHolder = new ViewSearchHolder();
        viewSearchHolder.searchVerse = items.get(position);
        searchVersesView.setTag(viewSearchHolder);

        return searchVersesView;
    }

    public void setItems(ArrayList<Verses> items) {
        this.items = items;
    }

    public void addItems(ArrayList<Verses> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
    }
}
