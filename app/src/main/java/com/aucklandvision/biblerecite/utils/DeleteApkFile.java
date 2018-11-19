package com.aucklandvision.biblerecite.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.aucklandvision.biblerecite.MainActivity;

import java.io.File;

public class DeleteApkFile {
    private MainActivity mainActivity;

    public DeleteApkFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void deleteApkFiles() {
        String root = Environment.getExternalStorageDirectory().toString() + "/" +DirectoryOfApk.directory;
        SharedPreferences preferences = mainActivity.getSharedPreferences("apk", Activity.MODE_PRIVATE);
        String apkName = preferences.getString("apkName", "");
        if (!apkName.equals("")) {
            String pathApk = root + apkName;
            File apkFile = new File(pathApk);
            apkFile.delete();
            mainActivity.getSharedPreferences("apk", Context.MODE_PRIVATE).edit().clear().commit();
        }
    }
}
