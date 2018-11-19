package com.aucklandvision.biblerecite.search;

import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.SearchPagingPrecess;

import java.util.ArrayList;

public class SearchPresenter {
    private SearchRepository searchRepository;

    public SearchPresenter() {
        searchRepository = new SearchRepository();
    }

    public int getNumTotalRowInSearch(ArrayList<Object> searchItemList) {
        int numOfTotalRow = searchRepository.getNumTotalRowInSearch(searchItemList);
        return numOfTotalRow;
    }

    public ArrayList<Verses> getListSearchVerses(ArrayList<Object> searchItemList, SearchPagingPrecess searchPagingPrecess) {
        ArrayList<Verses> listSearchVerses = searchRepository.getListSearchVerses(searchItemList, searchPagingPrecess);
        return listSearchVerses;
    }
}
