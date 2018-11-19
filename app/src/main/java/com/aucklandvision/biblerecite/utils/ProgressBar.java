package com.aucklandvision.biblerecite.utils;

import android.app.Dialog;
import android.view.Window;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;

public class ProgressBar {
    private Dialog dialog;
    private MainActivity mainActivity;

    public ProgressBar(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void showProgressbar() {
        dialog = new Dialog(mainActivity);
        int width = (int) (mainActivity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (mainActivity.getResources().getDisplayMetrics().heightPixels * 0.17);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.network_progressbar);
        dialog.getWindow().setLayout(width, height);
        dialog.setCancelable(false);
        dialog.show();
    }
    public void closeProgressbar() {
        dialog.cancel();
    }
}
