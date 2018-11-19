package com.aucklandvision.biblerecite.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import com.aucklandvision.biblerecite.MainActivity;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadAndInstallApkFile extends AsyncTask<String, String, String> {
    private MainActivity mainActivity;
    private ProgressBar progressBar;
    private String urlToDownloadApk;

    public DownloadAndInstallApkFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressBar(mainActivity);
        progressBar.showProgressbar();
    }

    @Override
    protected String doInBackground(String... urls) {
        int count;
        String root = Environment.getExternalStorageDirectory().toString() + "/" + DirectoryOfApk.directory;
        urlToDownloadApk = urls[0];
        try {
            URL url = new URL(urls[0]);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            InputStream inputStream = new BufferedInputStream(url.openStream(), 10240);
            String path = root+urls[0].substring(urls[0].indexOf("app")+3, urls[0].length());
            OutputStream outputStream = new FileOutputStream(path);
            byte[] data = new byte[1024];

            long total = 0;
            while ((count = inputStream.read(data)) != -1) {
                total += count;
                outputStream.write(data, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    protected void onPostExecute(String root) {
        super.onPostExecute(root);
        progressBar.closeProgressbar();

        try {
            String apkName = urlToDownloadApk.substring(urlToDownloadApk.indexOf("app") + 3, urlToDownloadApk.length());
            SharedPreferences preferences = mainActivity.getSharedPreferences("apk", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("apkName", apkName);
            editor.commit();
            String path = root + apkName;
            File apkFile = new File(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(mainActivity, mainActivity.getApplicationContext().getPackageName() + ".provider", apkFile);
                Intent packageinstaller = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                packageinstaller.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                packageinstaller.setDataAndType(apkUri, "application/vnd.android.package-archive");
                mainActivity.startActivity(packageinstaller);
            } else {
                Uri apkUri = Uri.fromFile(apkFile);
                Intent packageinstaller = new Intent(Intent.ACTION_VIEW);
                packageinstaller.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                packageinstaller.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                packageinstaller.setDataAndType(apkUri, "application/vnd.android.package-archive");
                mainActivity.startActivity(packageinstaller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
