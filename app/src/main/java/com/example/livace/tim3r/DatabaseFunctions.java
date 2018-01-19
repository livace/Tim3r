package com.example.livace.tim3r;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by CrazyDream on 14.01.2018.
 */

public class DatabaseFunctions {
    static Context context;
    static EventsDb eventsDb;

    public static void setContext(Context ctx) {
        if (context != ctx) {
            context = ctx;
            eventsDb = new EventsDb(context);
        }
    }

    static void saveToDb(Event eventToSave) {
        SQLiteDatabase db = eventsDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("title", eventToSave.getTitle());
        values.put("timeBegin", eventToSave.getTimeBegin());
        values.put("timeEnd", eventToSave.getTimeEnd());
        values.put("description", eventToSave.getDescription());
        values.put("image", "");
        if (eventToSave.getCity() != null) {
            values.put("city", eventToSave.getCity().slug);
        } else {
            values.put("city", "no");
        }
        values.put("type", eventToSave.getType().id);

//        Log.e("DB", "SaveToDb");

//        TODO: Почему-то сохраняет дважды, починить

        Long id = db.insert("eventsDatabase", null, values);
        db.close();
    }

    static ArrayList<Event> findInDb(long date) {
        SQLiteDatabase db = eventsDb.getReadableDatabase();

        String sortOrder = "timeBegin ASC";
        long dateThis = Utility.getTimeStampFromDate(date);
        long dateNext = Utility.getTimeStampFromDate(date + 1);
        String selection = "timeBegin BETWEEN ? and ?";

        Cursor cursor = db.query("eventsDatabase",
                null,
                selection,
                new String[]{String.valueOf(dateThis), String.valueOf(dateNext)},
                null,
                null,
                sortOrder);

        ArrayList<Event> events = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                Event event = generateEvent(cursor);

                events.add(event);
            }
        } finally {
            cursor.close();
        }

        db.close();

        return events;
    }

    private static Event generateEvent(Cursor cursor) {
        int type = cursor.getInt(cursor.getColumnIndex("type"));

        Long timeBegin = cursor.getLong(cursor.getColumnIndex("timeBegin"));
        Long timeEnd = cursor.getLong(cursor.getColumnIndex("timeEnd"));
        String title = cursor.getString(cursor.getColumnIndex("title"));

        String description = cursor.getString(cursor.getColumnIndex("description"));
        String city = cursor.getString(cursor.getColumnIndex("city"));
        String imageUrl = cursor.getString(cursor.getColumnIndex("image"));
        Long id = cursor.getLong(0);
        City foundCity = Cities.getCityBySlug(city);
        Event event = new Event(EventTypes.getEventTypeById(type),
                timeBegin,
                timeEnd,
                title,
                description,
                imageUrl,
                foundCity);

        event.setId(id);
        return event;
    }

    public static Event FindEventById(Long id) {
        SQLiteDatabase db = eventsDb.getReadableDatabase();

        String sortOrder = "timeBegin ASC";
        String selection = "_id = ?";

        Cursor cursor = db.query("eventsDatabase",
                null,
                selection,
                new String[]{String.valueOf(id)},
                null,
                null,
                sortOrder);

        Event event = null;
        try {
            cursor.moveToFirst();
            event = generateEvent(cursor);
        } finally {
            cursor.close();
        }

        db.close();
        return event;
    }


    static void removeFromDb(Event event) {
        Long id = event.getId();
        SQLiteDatabase db = eventsDb.getWritableDatabase();

        db.delete("eventsDatabase", "_id = ?", new String[]{String.valueOf(id)});

        db.close();
    }
}
