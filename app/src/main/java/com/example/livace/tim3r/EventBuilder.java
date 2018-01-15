package com.example.livace.tim3r;

import android.graphics.Bitmap;

/**
 * Created by livace on 14.01.2018.
 */

public class EventBuilder {
    private EventType type;
    private Long timeBegin;
    private Long timeEnd;
    private String title;
    private String description = "Без описания";
    private String imageUrl;
    private City city;
    
    public EventBuilder() {
        
    }
    
    public EventBuilder setType(EventType type) {
        this.type = type;
        return this;
    }

    public EventBuilder setTimeBegin(Long timeBegin) {
        this.timeBegin = timeBegin;
        return this;
    }

    public EventBuilder setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
        return this;
    }

    public EventBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public EventBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EventBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public EventBuilder setCity(City city) {
        this.city = city;
        return this;
    }

    public Event build() {
        return new Event(
                type,
                timeBegin,
                timeEnd,
                title,
                description,
                imageUrl,
                city
        );
    }
}
