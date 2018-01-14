package com.example.livace.tim3r;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by livace on 14.01.2018.
 */

public class Event {
    private EventType type;
    private Long timeBegin;
    private Long timeEnd;
    private Day day;
    private String title;
    private String description;
    private Bitmap image;
    private City city;

    public Event(EventType type, Long timeBegin, Long timeEnd, Day day, String title, String description, Bitmap image, City city) {
        this.type = type;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.day = Days.getDay(timeBegin);
        this.day = day;
        this.title = title;
        this.description = description;
        this.image = image;
        this.city = city;
    }
    @Override
    public int hashCode() {
        int result = begin.hashCode();
        result = result * 100007 + end.hashCode();
        return result;
    }
}
