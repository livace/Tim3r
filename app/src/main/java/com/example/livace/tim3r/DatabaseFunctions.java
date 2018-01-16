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


    DatabaseFunctions(Context context) {
        this.context = context;
    }

    static void saveToDb(Event eventToSave) {
        SQLiteDatabase db = eventsDb.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("title", eventToSave.getTitle());
        values.put("timeBegin", eventToSave.getTimeBegin());
        values.put("timeEnd", eventToSave.getTimeEnd());
        values.put("description", eventToSave.getDescription());
        values.put("image", "");
        values.put("city", eventToSave.getCity().slug);
        values.put("type", eventToSave.getType().id);

        db.insert("eventsDatabase", null, values);
        db.close();
    }

    static HashSet<Event> findInDb(long date) {
        SQLiteDatabase db = eventsDb.getReadableDatabase();

        String sortOrder = "timeBegin ASC";
        long dateThis = Utility.getTimeStampFromDate(date);
        long dateNext = Utility.getTimeStampFromDate(date + 1);
        String selection = "timeBegin BETWEEN ? and ?";

        Cursor c = db.query("eventsDatabase",
                null,
                selection,
                new String[]{String.valueOf(dateThis), String.valueOf(dateNext)},
                null,
                null,
                sortOrder);

        HashSet<Event> events = new HashSet<Event>();
        Event event;

        c.moveToFirst();
        do {
            int type = c.getInt(c.getColumnIndex("type"));
            Long timeBegin = c.getLong(c.getColumnIndex("timeBegin"));
            Long timeEnd = c.getLong(c.getColumnIndex("timeEnd"));
            String title = c.getString(c.getColumnIndex("title"));
            String description = c.getString(c.getColumnIndex("description"));
            String city = c.getString(c.getColumnIndex("city"));
            String imageUrl = c.getString(c.getColumnIndex("imageUrl"));
            Long id = c.getLong(c.getColumnIndex("_id"));

            event = new Event(EventTypes.getEventTypeById(type),
                    timeBegin,
                    timeEnd,
                    title,
                    description,
                    imageUrl,
                    Cities.getCityBySlug(city));

            event.setId(id);
            events.add(event);
        } while (c.moveToNext());

        db.close();
        return events;
    }

    static void removeFromDb(Event event) {
        Long id = event.getId();
        SQLiteDatabase db = eventsDb.getWritableDatabase();

        db.delete("eventsDatabase", "rowId = ?", new String[] {String.valueOf(id)});

        db.close();
    }
}
