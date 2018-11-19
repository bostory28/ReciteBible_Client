package com.aucklandvision.biblerecite.download;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.main.MainPresenter;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.main.Verses;
import com.aucklandvision.biblerecite.utils.ProgressBar;

import java.util.ArrayList;

import vo.UpdateVO;

public class DownloadDialog {
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private MainPresenter mainPresenter;
    private UserInfo userInfo;
    private ArrayList<UpdateVO> listUpdate;
    private Verses verses;

    public DownloadDialog(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
    }

    public void showDownloadDialog() {
        userInfo = mainFragment.getUserInfo();
        mainPresenter = mainFragment.getMainPresenter();
        listUpdate = mainFragment.getDownloadList();
        verses = mainFragment.getVerses();
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ProgressBar progressBar = new ProgressBar(mainActivity);
                        progressBar.showProgressbar();

                        //setting userinfo
                        mainPresenter.updateUserInfo(userInfo);
                        DownloadVersesThread downloadVersesThread = new DownloadVersesThread(mainFragment, verses, progressBar);
                        downloadVersesThread.start();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder updateBuilder = new AlertDialog.Builder(mainActivity);
        updateBuilder.setCancelable(false);
        updateBuilder.setIcon(R.drawable.ic_title_update);

        //ver1.1.1
        if (listUpdate != null) {
            LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View updateDetails = inflater.inflate(R.layout.scroll_update_dialog, null, false);
            ScrollView sv = (ScrollView) updateDetails.findViewById(R.id.scrollView_update);
            if (listUpdate.size() <= 5) sv.getLayoutParams().height = (int) (17 * (listUpdate.size()+1) * Resources.getSystem().getDisplayMetrics().density);
            else sv.getLayoutParams().height = (int) (17 * 5 * Resources.getSystem().getDisplayMetrics().density);
            TextView tv = (TextView) updateDetails.findViewById(R.id.textViewWithScroll);
            if (userInfo.getLanguage() == 0) tv.setText("상세내용\n");
            else tv.setText("Details\n");

            String str_Update_type = null;
            for (int i = 0; i < listUpdate.size(); i++) {
                switch (listUpdate.get(i).getUpdate_type()) {
                    case 1:
                        if (userInfo.getLanguage() == 0) str_Update_type = "추가";
                        else str_Update_type = "ADD";
                        break;
                    case 2:
                        if (userInfo.getLanguage() == 0) str_Update_type = "변경";
                        else str_Update_type = "ALTER";
                        break;
                    case 3:
                        if (userInfo.getLanguage() == 0) str_Update_type = "삭제";
                        else str_Update_type = "DELETE";
                        break;
                }
                if (userInfo.getLanguage() == 0) tv.append(i + 1 + ". " + str_Update_type + " " + listUpdate.get(i).getYr() + "/" + listUpdate.get(i).getMon() + " " + listUpdate.get(i).getVerse_title_kr() + "\n");
                else tv.append(i + 1 + ". " + str_Update_type + " " + listUpdate.get(i).getYr() + "/" + listUpdate.get(i).getMon() + " " + listUpdate.get(i).getVerse_title_en() + "\n");
            }
            updateBuilder.setView(updateDetails);
        }
        if (userInfo.getLanguage() == 0) {
            updateBuilder.setTitle("다운로드");
            if (listUpdate != null) updateBuilder.setMessage(listUpdate.size() + "개의 성경말씀을 다운로드하시겠습니까?").setPositiveButton("네", dialogClickListener).setNegativeButton("아니오", dialogClickListener);
            else updateBuilder.setMessage("다운로드 할 성경말씀이 없습니다.\n성경말씀을 다운로드하시겠습니까?").setPositiveButton("네", dialogClickListener).setNegativeButton("아니오", dialogClickListener);
        } else {
            updateBuilder.setTitle("Download");
            if (listUpdate != null) updateBuilder.setMessage("Do you want to download " + listUpdate.size() + " bible verses?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
            else updateBuilder.setMessage("There is no new bible verse to download. Do you want to download bible verses?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
        }
        updateBuilder.show();
    }
}
