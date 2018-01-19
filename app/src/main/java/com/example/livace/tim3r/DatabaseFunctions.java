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

    static void saveToDb(Event event) {
        SQLiteDatabase db = eventsDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("title", event.getTitle());
        values.put("timeBegin", event.getTimeBegin());
        values.put("timeEnd", event.getTimeEnd());
        values.put("description", event.getDescription());
        values.put("image", "");
        if (event.getCity() != null) {
            values.put("city", event.getCity().slug);
        } else {
            values.put("city", "no");
        }
        values.put("type", event.getType().id);

        Long id = db.insert("eventsDatabase", null, values);

        Log.e("Db", "Saved, id = " + String.valueOf(id));

        event.setId(id);

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

        Log.e("Db", "Found " + String.valueOf(events.size()) + "Events");

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
        Long id = cursor.getLong(cursor.getColumnIndex("id"));
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
        String selection = "id = ?";

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

        db.delete("eventsDatabase", "id = ?", new String[]{String.valueOf(id)});

        db.close();
    }
}
