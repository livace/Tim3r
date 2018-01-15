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
    private String imageUrl;
    private Long id = null;

    public Event(EventType type,
                 Long timeBegin,
                 Long timeEnd,
                 String title,
                 String description,
                 String imageUrl,
                 City city) {
        this.type = type;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.day = Days.getDay(timeBegin);
        this.title = title;
        this.description = description;
//        this.image = image;
        this.imageUrl = imageUrl;
        this.city = city;
    }




    public EventType getType() {
        return type;
    }

    public Long getTimeBegin() {
        return timeBegin;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public Day getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getImageUrl() {return imageUrl;}

    public City getCity() {
        return city;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int result = timeBegin.hashCode();
        result = result * 100007 + timeEnd.hashCode();
        return result;
    }
}
