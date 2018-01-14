package com.example.livace.tim3r;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CrazyDream on 14.01.2018.
 */
public class EventsDb extends SQLiteOpenHelper {

    public EventsDb(Context context) {
        super(context, "eventsDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase eventsDatabase) {
        eventsDatabase.execSQL("create table eventsDatabase ("
                + "title text,"
                + "timeBegin integer,"
                + "timeEnd integer,"
                + "discription text,"
                + "image blob,"
                + "city text,"
                + "type integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}