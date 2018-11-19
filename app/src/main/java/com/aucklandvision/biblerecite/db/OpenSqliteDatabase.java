package com.aucklandvision.biblerecite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OpenSqliteDatabase {

    private String databaseName = "biblerecite.db";
    private static SQLiteDatabase sqLiteDatabase;
    private CreateAndAlterTable createAndAlterTable;
    private Context context;
    private int DATABASE_VERSION = 1;

    public OpenSqliteDatabase(Context context) {
        this.context = context;
    }
    public static SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public void openDatabase() {
        //context.deleteDatabase(databaseName);
        createAndAlterTable = new CreateAndAlterTable(context, databaseName, null, DATABASE_VERSION);
        sqLiteDatabase = createAndAlterTable.getWritableDatabase();
    }
}
