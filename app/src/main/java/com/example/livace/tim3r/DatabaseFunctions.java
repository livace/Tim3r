package com.example.livace.tim3r;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by CrazyDream on 14.01.2018.
 */

public class DatabaseFunctions {
    static Context context;
    static EventsDb eventsDb = new EventsDb(context);


    DatabaseFunctions(Context context){
        this.context = context;
    }

    static void saveToDb(Event eventToSave){
        SQLiteDatabase db = eventsDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("title", eventToSave.getTitle());
        values.put("timeBegin", eventToSave.getTimeBegin());
        values.put("timeEnd", eventToSave.getTimeEnd());
        values.put("description", eventToSave.getDescription());
        values.put("image", "");
        values.put("city", eventToSave.getCity().name);
        values.put("type", eventToSave.getType().id);

        db.insert("eventsDatabase",null, values);
    }

    static void findInDb(){
        SQLiteDatabase db = eventsDb.getReadableDatabase();


    }

    static void removeFromDb(){

    }
}
