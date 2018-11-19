package com.aucklandvision.biblerecite.favourite;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

public class FavouriteAdapter extends BaseAdapter {
    private FavouriteView favouriteView;
    private ArrayList<Verses> items = new ArrayList<>();
    private MainActivity mainActivity;
    private UserInfo userInfo;

    public FavouriteAdapter(MainActivity mainActivity, UserInfo userInfo) {
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
        favouriteView = null;
        if (convertView == null) {
            favouriteView = new FavouriteView(mainActivity, userInfo);
        } else {
            favouriteView = (FavouriteView) convertView;
        }

        favouriteView.setView(items.get(position));

        FavouriteHolder favouriteHolder = new FavouriteHolder();
        favouriteHolder.verses = items.get(position);
        favouriteView.setTag(favouriteHolder);

        return favouriteView;
    }

    public void setItems(ArrayList<Verses> items) {
        this.items = items;
    }
}
