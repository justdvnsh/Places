package com.example.places.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class placesDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "places.db";
    public static final int DATABASE_VERSION = 4;

    public placesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_PLACES_TABLE =
                "CREATE TABLE " + placesContract.placesEntry.TABLE_NAME + " (" +
                        placesContract.placesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        placesContract.placesEntry.COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        placesContract.placesEntry.COLUMN_FEATURE + " TEXT," +
                        placesContract.placesEntry.COLUMN_ADMIN + " TEXT," +
                        placesContract.placesEntry.COLUMN_SUB_ADMIN + " TEXT," +
                        placesContract.placesEntry.COLUMN_LOCALITY + " TEXT," +
                        placesContract.placesEntry.COLUMN_THOROUGHFARE + " TEXT," +
                        placesContract.placesEntry.COLUMN_COUNTRY_NAME + " TEXT," +
                        placesContract.placesEntry.COLUMN_POSTAL_CODE + " TEXT NOT NULL," +
                        placesContract.placesEntry.COLUMN_LATITUDE + " TEXT NOT NULL," +
                        placesContract.placesEntry.COLUMN_LONGITUDE + " TEXT NOT NULL," +
                        " UNIQUE ("+ placesContract.placesEntry.COLUMN_LATITUDE + ", " + placesContract.placesEntry.COLUMN_LONGITUDE  + ") ON CONFLICT REPLACE );";

        sqLiteDatabase.execSQL(SQL_CREATE_PLACES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + placesContract.placesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
