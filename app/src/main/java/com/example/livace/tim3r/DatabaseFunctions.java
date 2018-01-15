package com.example.livace.tim3r;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;

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
        values.put("city", eventToSave.getCity().slug);
        values.put("type", eventToSave.getType().id);

        db.insert("eventsDatabase",null, values);
    }

    static HashSet<Event> findInDb(int Date){
        SQLiteDatabase db = eventsDb.getReadableDatabase();

        String sortOrder = "timeBegin ASC";
        long dateThis = Date * 24 * 60 * 60 * 1000;
        long dateNext = (Date + 1)* 24 * 60 * 60 * 1000;
        String selection = "timeBegin BETWEEN ? and ?";

        Cursor c = db.query("eventsDatabase", null, selection, new String[] {String.valueOf(dateThis), String.valueOf(dateNext)}, null, null, sortOrder);

        HashSet <Event> events = new HashSet<Event>();
        Event event;

        c.moveToFirst();
        do{
            int type = c.getInt(7);
            Long timeBegin = c.getLong(2);
            Long timeEnd =c.getLong(3);
            String title = c.getString(1);
            String description = c.getString(4);
            String city = c.getString(6);
            String imageUrl = c.getString(5);

            event = new Event(EventTypes.getEventTypeById(type), timeBegin, timeEnd, title,description, imageUrl, Cities.getCityBySlug(city));
            events.add(event);
        }while (c.moveToNext());

        return events;
    }

    static void removeFromDb(){

    }
}
