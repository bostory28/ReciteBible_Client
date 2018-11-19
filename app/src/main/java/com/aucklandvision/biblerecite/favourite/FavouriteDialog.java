package com.aucklandvision.biblerecite.favourite;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.main.MainPresenter;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.ThemeColor;

import java.util.ArrayList;

public class FavouriteDialog {

    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private MainPresenter mainPresenter;
    private Dialog dialog;
    private UserInfo userInfo;

    private FavouriteAdapter favouriteApdater;

    public FavouriteDialog(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
    }

    public void showFavoriteDialog() {
        userInfo = mainFragment.getUserInfo();
        FavouritePresenter favouritePresenter = new FavouritePresenter();
        mainPresenter = mainFragment.getMainPresenter();
        dialog = new Dialog(mainActivity);
        int width = (int) (mainActivity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (mainActivity.getResources().getDisplayMetrics().heightPixels * 0.80);
        //v1.0.1
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //v1.0.1
        dialog.setContentView(R.layout.favorite_main);
        dialog.getWindow().setLayout(width, height);

        TextView text_favorite_title = dialog.findViewById(R.id.text_favorite_title);
        text_favorite_title.setBackgroundColor(ThemeColor.ARGB);
        text_favorite_title.setText(" Favourites");

        ListView favorite_listview = dialog.findViewById(R.id.favorite_listview);
        favouriteApdater = new FavouriteAdapter(mainActivity, userInfo);

        ArrayList<Verses> favoriteList = favouritePresenter.getFavoriteList();
        favouriteApdater.setItems(favoriteList);
        favorite_listview.setAdapter(favouriteApdater);

        favorite_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavouriteHolder favouriteHolder = (FavouriteHolder) view.getTag();
                mainFragment.setVerse(mainPresenter.onSearchVerseItemClick(favouriteHolder.verses, userInfo.getLanguage()));
                userInfo = mainPresenter.getUserInfo();
                dialog.cancel();
                mainFragment.showAllItem();
            }
        });
        ImageView image_favorite_xbutton = dialog.findViewById(R.id.image_favorite_xbutton);
        image_favorite_xbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
