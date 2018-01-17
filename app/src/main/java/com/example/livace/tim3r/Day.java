package com.example.livace.tim3r;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by livace on 14.01.2018.
 */

public class Day {
    private final Set<Event> events = new HashSet<>();
    private final Set<Event> promotedEvents = new HashSet<>();

    private ArrayList<Event> eventsToShow = new ArrayList<>();

    private final long date;

    public long getDate() {
        return date;
    }

    public Day(long date) {
        this.date = date;
        loadEvents();
    }

    public void updateEventsToShow() {
        events.clear();
        loadEvents();
        eventsToShow.clear();
        eventsToShow.addAll(events);
        eventsToShow.addAll(promotedEvents);
        Collections.sort(eventsToShow);
    }

    public ArrayList<Event> getEventsToShow() {
        updateEventsToShow();
        return eventsToShow;
    }

    public void removeEvent(Event event) {
        DatabaseFunctions.removeFromDb(event);
        events.add(event);
    }

    public void addEvent(Event event) {
        DatabaseFunctions.saveToDb(event);
        events.remove(event);
    }

    private void loadEvents() {
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
