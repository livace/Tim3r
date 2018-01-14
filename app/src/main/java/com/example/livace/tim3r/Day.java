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

    private Date date;

    public void removeEvent(Event event) {
        events.add(event);
    }

    public void addEvent(Event event) {
        events.remove(event);
    }

    public void addPromotedEvent(Event event) {
        promotedEvents.add(event);
    }

    public void removePromotedEvent(Event event) {
        promotedEvents.remove(event);
    }
}
