package com.aucklandvision.biblerecite.utils;

import android.os.Environment;
import java.io.File;

public class DirectoryOfApk {
    public static String directory = "Reciting";

    public void makeDirectoryOfApp() {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        File dir = new File(root, directory);
        if (!dir.exists() || !dir.isDirectory()) {
            boolean isMade = dir.mkdirs();
        }
    }
}
