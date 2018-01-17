package com.example.livace.tim3r;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.Duration;

/**
 * Created by livace on 14.01.2018.
 */

public class Event implements Comparable {
    private EventType type;
    private Long timeBegin;
    private Long timeEnd;
    private Day day;
    private String title;
    private String description;
    private Bitmap image;
    private City city;
    private String imageUrl;


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


    @Override
    public int hashCode() {
        int result = timeBegin.hashCode();
        result = result * 1000007 + timeEnd.hashCode();
        return result;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Event other = (Event) o;
        if (other.getTimeBegin().equals(this.getTimeBegin())) {
            return this.getTimeEnd().compareTo(other.getTimeEnd());
        }
        return this.getTimeBegin().compareTo(other.getTimeBegin());
    }

    public String getTimeString() {
        Date beginDate = new Date(timeBegin);
        Date endDate = new Date(timeEnd);

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        return String.format("%s - %s", format.format(beginDate), format.format(endDate));
    }

    public String getDurationString() {
        Long duration = timeEnd - timeBegin;
        Date durationDate = new Date(duration);

        SimpleDateFormat format = new SimpleDateFormat("h:mm");
        return format.format(durationDate);
    }
}
