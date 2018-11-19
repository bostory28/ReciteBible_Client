package com.aucklandvision.biblerecite.schedule;

import android.app.Dialog;
import android.content.DialogInterface;
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

public class ScheduleDialog {
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private MainPresenter mainPresenter;
    private UserInfo userInfo;
    private Dialog dialog;
    private boolean isCloseForcedInSchedule = true;

    private ListView listView_month;
    private ListView listView_year;
    private ScheduleMonthAdapter scheduleMonthAdapter;
    private ScheduleYearAdapter scheduleYearAdapter;

    public static View prevViewSearch;                       //store an early view of schedule

    public ScheduleDialog(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
    }

    public void showScheduleDialog() {
        mainPresenter = mainFragment.getMainPresenter();
        userInfo = mainFragment.getUserInfo();

        dialog = new Dialog(mainActivity);
        //isCloseForcedInSchedule = mainFragment.getIsCloseForcedInSchedule();
        int width = (int) (mainActivity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (mainActivity.getResources().getDisplayMetrics().heightPixels * 0.80);
        //v1.0.1
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //v1.0.1
        dialog.setContentView(R.layout.schedule_main);
        dialog.getWindow().setLayout(width, height);

        //cancel button
        ImageView image_schedule_xbutton = dialog.findViewById(R.id.image_schedule_xbutton);
        image_schedule_xbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (isCloseForcedInSchedule == true) {
                    userInfo = mainPresenter.getUserInfo();
                    mainPresenter.getCurrPageInfo(userInfo.getCurYr(), userInfo.getCurPage());
                    mainFragment.setVerse(mainPresenter.getCurrVerse());
                    mainFragment.showRememberCheckbox();
                }
                prevViewSearch = null;
            }
        });

        //schedule title
        TextView text_schedule_title = (TextView) dialog.findViewById(R.id.text_schedule_title);
        text_schedule_title.setBackgroundColor(ThemeColor.ARGB);
        text_schedule_title.setText(" Schedule");

        listView_month = (ListView) dialog.findViewById(R.id.listView_month);
        scheduleMonthAdapter = new ScheduleMonthAdapter(mainActivity, userInfo, mainPresenter);
        listView_year = (ListView) dialog.findViewById(R.id.listView_year);
        scheduleYearAdapter = new ScheduleYearAdapter(mainActivity, userInfo);

        ArrayList<Integer> listYear = mainPresenter.getAllYear();
        scheduleYearAdapter.setItems(listYear);
        listView_year.setAdapter(scheduleYearAdapter);
        listView_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prevViewSearch != null) {
                    ViewYearHolder ealryViewHolder = (ViewYearHolder) prevViewSearch.getTag();
                    ealryViewHolder.text_arrow.setTextColor(0xffffffff);
                }
                prevViewSearch = view;

                ViewYearHolder viewHolder = (ViewYearHolder) view.getTag();
                viewHolder.text_arrow.setTextColor(ThemeColor.ARGB);

                int year = (Integer) scheduleYearAdapter.getItem(position);
                mainPresenter.getAllVersesOfCurrentYear(year);
                showScheduleMonth();
            }
        });
        showScheduleMonth();
        dialog.show();
    }

    public void showScheduleMonth() {
        ArrayList<Verses> listVerse = mainPresenter.getListVerses();
        mainFragment.setListVerse(listVerse);
        scheduleMonthAdapter.setItems(listVerse);
        listView_month.setAdapter(scheduleMonthAdapter);
        listView_month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainFragment.setVerse(mainPresenter.onScheduleMonthItemClick(view, position));
                userInfo = mainPresenter.getUserInfo();
                isCloseForcedInSchedule = false;
                dialog.cancel();
                mainFragment.showAllItem();
            }
        });
    }
}
