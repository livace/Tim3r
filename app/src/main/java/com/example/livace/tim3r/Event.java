package com.example.livace.tim3r;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by livace on 14.01.2018.
 */

public class Event {
    private EventType type;
    private Date begin;
    private Date end;
    private Day day;

    public Event(EventType type, Date begin, Date end) {
        this.type = type;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public int hashCode() {
        int result = begin.hashCode();
        result = result * 100007 + end.hashCode();
        return result;
    }
}
