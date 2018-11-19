package com.aucklandvision.biblerecite.search;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.main.MainPresenter;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.SearchPagingPrecess;
import com.aucklandvision.biblerecite.utils.ThemeColor;

import java.util.ArrayList;

public class SearchDialog {
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private SearchPresenter searchPresenter;
    private MainPresenter mainPresenter;
    private UserInfo userInfo;

    private Dialog dialog;
    private boolean isSubmitedInSearch;
    private int searchLanguage;
    private int searchSection;
    private String searchQuery;

    private TextView text_search_message;
    private SearchView searchView;
    private ListView search_list;

    private SearchVersesAdapter searchVersesAdapter;

    //paging
    private int countNumOfMoreClicked;
    private ArrayList<Object> searchItemList;
    private SearchPagingPrecess searchPagingPrecess;
    private View footer;


    public SearchDialog(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
        searchPresenter = new SearchPresenter();
        mainPresenter = mainFragment.getMainPresenter();
    }

    public void showSearchDialog() {
        userInfo = mainFragment.getUserInfo();
        dialog = new Dialog(mainActivity);
        isSubmitedInSearch = false;
        int width = (int) (mainActivity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (mainActivity.getResources().getDisplayMetrics().heightPixels * 0.9);
        //v1.0.1
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //v1.0.1
        dialog.setContentView(R.layout.search_main);
        dialog.getWindow().setLayout(width, height);

        //title
        TextView text_search_title = (TextView) dialog.findViewById(R.id.text_search_title);
        text_search_title.setBackgroundColor(ThemeColor.ARGB);
        text_search_title.setText(" Search");

        //put a message in the text_search_message
        text_search_message = (TextView) dialog.findViewById(R.id.text_search_message);
        if (userInfo.getLanguage() == 0) text_search_message.setText("검색어를 입력하고 찾기버튼을 누르세요.");
        else text_search_message.setText("Put a word you want to search \nand click the search button");

        //setting spinner(language, section)
        Spinner spinner_language = (Spinner) dialog.findViewById(R.id.spinner_language);
        SpinnerAdapter spinnerAdapter1 = ArrayAdapter.createFromResource(mainActivity, R.array.language, android.R.layout.simple_spinner_dropdown_item);
        spinner_language.setAdapter(spinnerAdapter1);
        spinner_language.setSelection(userInfo.getLanguage());
        Spinner spinner_section = (Spinner) dialog.findViewById(R.id.spinner_section);
        SpinnerAdapter spinnerAdapter2 = ArrayAdapter.createFromResource(mainActivity, R.array.section, android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(spinnerAdapter2);

        //spinner event
        spinner_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchLanguage = position;
                showSearchListView();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchSection = position;
                showSearchListView();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //search declare
        searchView = (SearchView) dialog.findViewById(R.id.search_content);
        //search event
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isSubmitedInSearch = true;
                showSearchListView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //cancel button
        ImageView image_search_xbutton = dialog.findViewById(R.id.image_search_xbutton);
        image_search_xbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    public void showSearchListView() {
        searchQuery = String.valueOf(searchView.getQuery()).trim();
        if (!searchQuery.equals("")) {
            //initiate click number
            countNumOfMoreClicked = 0;

            //when searching, condition
            searchItemList = new ArrayList<>();
            searchItemList.add(searchQuery);
            searchItemList.add(searchLanguage);
            searchItemList.add(searchSection);

            //get a total searching number for paging
            int numOfTotalRow = searchPresenter.getNumTotalRowInSearch(searchItemList);
            //setting for init paging
            searchPagingPrecess = initSearchPaging(numOfTotalRow);

            //setting listview into an adapter
            search_list = (ListView) dialog.findViewById(R.id.search_list);
            searchVersesAdapter = new SearchVersesAdapter(mainActivity, searchLanguage);
            ArrayList<Verses> listSearchVerses = searchPresenter.getListSearchVerses(searchItemList, searchPagingPrecess);
            searchVersesAdapter.setItems(listSearchVerses);
            search_list.setAdapter(searchVersesAdapter);

            //when changing a condition, initiate a footer
            if (search_list.getFooterViewsCount() != 0)
                search_list.removeFooterView(footer);

            //process by result of searching
            if (numOfTotalRow == 0) {
                text_search_message.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                search_list.getLayoutParams().height = 0;
                if (searchLanguage == 0)
                    text_search_message.setText("검색 결과가 없습니다. \n검색어를 바꾼 후 다시 시도해 주십시오.");
                else
                    text_search_message.setText("No results found. \nAdjust your search and try again.");
            } else {
                //setting a footer of listview
                if (countNumOfMoreClicked < searchPagingPrecess.getTotalNumClicked()) {
                    footer = dialog.getLayoutInflater().inflate(R.layout.search_addfootview, null, false);
                    TextView text_more = footer.findViewById(R.id.text_more);
                    search_list.addFooterView(footer);
                    text_more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //add listview
                            if (countNumOfMoreClicked == 0)
                                searchPagingPrecess.changeNumberToShowAtOnce();
                            searchPagingPrecess.renewNumberOfOffset(countNumOfMoreClicked);
                            ArrayList<Verses> listSearchVerses = searchPresenter.getListSearchVerses(searchItemList, searchPagingPrecess);
                            searchVersesAdapter.addItems(listSearchVerses);
                            searchVersesAdapter.notifyDataSetChanged();

                            //remove footer when All clicked
                            if (++countNumOfMoreClicked == searchPagingPrecess.getTotalNumClicked()) {
                                search_list.removeFooterView(footer);
                            }
                        }
                    });
                }

                //setting listveiw layout
                text_search_message.getLayoutParams().height = 0;
                search_list.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                //when click a item of listview, event prcessing
                search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ViewSearchHolder viewSearchHolder = (ViewSearchHolder) view.getTag();
                        mainFragment.setVerse(mainPresenter.onSearchVerseItemClick(viewSearchHolder.searchVerse, searchLanguage));
                        userInfo = mainPresenter.getUserInfo();
                        dialog.cancel();
                        mainFragment.showAllItem();
                    }
                });
            }
        } else {
            //when opened search, message
            if (searchLanguage == 0) text_search_message.setText("검색어를 입력하고 찾기버튼을 누르세요.");
            else text_search_message.setText("Put a word you want to search \nand click the search button");
        }
    }

    public SearchPagingPrecess initSearchPaging(int numOfTotalRow) {
        searchPagingPrecess = new SearchPagingPrecess();
        searchPagingPrecess.initSearchPaging(numOfTotalRow, 0);
        return searchPagingPrecess;
    }
}
