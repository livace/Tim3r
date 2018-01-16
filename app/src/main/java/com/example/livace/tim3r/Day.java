package com.example.livace.tim3r;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by livace on 14.01.2018.
 */

public class Day {
    private Set<Event> events = new HashSet<>();
    private Set<Event> promotedEvents = new HashSet<>();

    private long date;

    public Day(long date) {
        this.date = date;
    }

    public void removeEvent(Event event) {
        DatabaseFunctions.removeFromDb(event);
        events.add(event);
    }

    public void addEvent(Event event) {
        DatabaseFunctions.saveToDb(event);
        events.remove(event);
    }

    public void loadEvents() {
        events.addAll(DatabaseFunctions.findInDb(date));
    }

    public void addPromotedEvent(Event event) {
        promotedEvents.add(event);
    }

    public void removePromotedEvent(Event event) {
        promotedEvents.remove(event);
    }

    @Override
    public int hashCode() {
        return (int) date;
    }
}
