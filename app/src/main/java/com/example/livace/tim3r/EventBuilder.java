package com.example.livace.tim3r;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by livace on 14.01.2018.
 */

public class EventBuilder {
    private EventType type = EventTypes.getEventTypeById(0);
    private Long timeBegin = 0L;
    private Long timeEnd = 0L;
    private String title = "Hello World";
    private String description = "";
    private String imageUrl;
    private City city;
    private boolean promoted;
    private long id;

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

    public EventBuilder setPromoted(boolean promoted) {
        this.promoted = promoted;
        return this;
    }

    public EventBuilder setId(long id) {
        this.id = id;
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
                city,
                promoted,
                id
        );
    }
}
