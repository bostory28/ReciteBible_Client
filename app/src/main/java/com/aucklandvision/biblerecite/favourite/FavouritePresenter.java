package com.aucklandvision.biblerecite.favourite;

import com.aucklandvision.biblerecite.main.Verses;

import java.util.ArrayList;

public class FavouritePresenter {
    private FavouriteRepository favouriteRepository;

    public FavouritePresenter() {
        favouriteRepository = new FavouriteRepository();
    }

    public ArrayList<Verses> getFavoriteList() {
        ArrayList<Verses> favoriteList = favouriteRepository.getFavoriteList();
        return favoriteList;
    }
}
