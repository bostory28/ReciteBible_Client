package com.aucklandvision.biblerecite.information;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aucklandvision.biblerecite.MainActivity;
import com.aucklandvision.biblerecite.R;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.utils.DownloadAndInstallApkFile;
import com.aucklandvision.biblerecite.version.VersionInfo;

public class InformationView {
    private MainActivity mainActivity;
    private MainFragment mainFragment;
    private String urlToDownloadApk;

    public InformationView(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
        mainActivity = (MainActivity) mainFragment.getActivity();
    }

    public void showInfoDialog() {
        UserInfo userInfo = mainFragment.getUserInfo();
        boolean isNewVersion = mainFragment.getIsNewVersion();
        urlToDownloadApk = mainFragment.getUrlToDownloadApk();

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_info);

        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View versionInfo = inflater.inflate(R.layout.info_dialog, null, false);
        TextView text_app_title = (TextView) versionInfo.findViewById(R.id.text_app_title);
        TextView text_app_version = (TextView) versionInfo.findViewById(R.id.text_app_version);
        TextView text_explain_version = (TextView) versionInfo.findViewById(R.id.text_explain_version);
        Button btn_update = (Button) versionInfo.findViewById(R.id.btn_update);
        if (isNewVersion == true) {
            btn_update.setEnabled(true);
            btn_update.setVisibility(View.VISIBLE);
        } else {
            btn_update.setEnabled(false);
            btn_update.setVisibility(View.INVISIBLE);
        }
        if (userInfo.getLanguage() == 0) {
            builder.setTitle("정보");
            text_app_title.setText("오클랜드비전교회암송");
            text_app_version.setText("버전 " + VersionInfo.version);
            text_explain_version.setText(VersionInfo.explain_kr);
            btn_update.setText("업데이트");
            builder.setPositiveButton("확인", null);
        } else {
            builder.setTitle("Information");
            text_app_title.setText("AVBC Reciting");
            text_app_version.setText("Version " + VersionInfo.version);
            text_explain_version.setText(VersionInfo.explain_en);
            btn_update.setText("Update");
            builder.setPositiveButton("OK", null);
        }
        final AlertDialog dialog = builder.create();
        dialog.setView(versionInfo);
        dialog.show();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new DownloadAndInstallApkFile(mainActivity).execute(urlToDownloadApk);
            }
        });
    }
}
