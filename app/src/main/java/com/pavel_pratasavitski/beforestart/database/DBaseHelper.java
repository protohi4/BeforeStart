package com.pavel_pratasavitski.beforestart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pavel_pratasavitski.beforestart.database.DBase.PhotoTable;

/**
 * Created by Pavel_Pratasavitski on 8/23/2017.
 */

public class DBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "PhotoBase.db";

    public DBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PhotoTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                PhotoTable.Cols.ID + ", " +
                PhotoTable.Cols.TITLE + ", " +
                PhotoTable.Cols.DESCRIPTION + ", " +
                PhotoTable.Cols.LATITUDE + ", " +
                PhotoTable.Cols.LONGITUDE + ", " +
                PhotoTable.Cols.URL + ")"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
