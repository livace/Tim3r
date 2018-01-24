package com.example.livace.tim3r;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
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

    private onUpdateListener listener;

    public long getDate() {
        return date;
    }

    public Day(long date, onUpdateListener listener) {
        this.date = date;
        this.listener = listener;
        updateEventsToShow();
    }

    public void setListener(onUpdateListener listener) {
        this.listener = listener;
    }

    private int iteration = 0;

    private void pushEvent(Event event, int iteration) {
        if (iteration != this.iteration) {
            return;
        }
        eventsToShow.add(event);
        Collections.sort(eventsToShow);
        notifyListener();
    }

    private void updatePromoted(final int iteration) {
        for (PairLong x : FreeTimeIntervals()) {
            Event.findInApi(x.getFirst(),
                    x.getSecond(),
                    new Event.onDownloadEventListener() {
                        @Override
                        public void onComplete(Event event) {
                            if (event != null) {
                                promotedEvents.add(event);
                                pushEvent(event, iteration);
                            }
                        }
                    });
        }
    }

    public void updateEventsToShow() {
        eventsToShow.clear();

        iteration++;

        loadEvents(iteration);
        updatePromoted(iteration);
    }

    public ArrayList<Event> getEventsToShow() {
        return eventsToShow;
    }

    private void notifyListener() {
        if (listener != null) {
            listener.onUpdate();
        }
    }

    public void removeEvent(Event event) {
        DatabaseFunctions.removeFromDb(event);
        events.remove(event);
    }

    public void addEvent(Event event) {
        DatabaseFunctions.saveToDb(event);
        pushEvent(event, iteration);
    }

    private void loadEvents(int iteration) {
        events.clear();
        events.addAll(DatabaseFunctions.findInDb(date));
        for (Event event : events) {
            pushEvent(event, iteration);
        }
    }

//        public void addPromotedEvent(Event event) {
//        promotedEvents.add(event);
//    }
//
//    public void removePromotedEvent(Event event) {
//        promotedEvents.remove(event);
//    }

    @Override
    public int hashCode() {
        return (int) date;
    }

    interface onUpdateListener {
        void onUpdate();
    }

    private ArrayList<PairLong> FreeTimeIntervals() {
        ArrayList<PairLong> freeTimeIntervals = new ArrayList<PairLong>();
        Long timeStart = Math.max(
                Utility.getTimeStampFromDateHoursMinutes(date, 8, 30),
                Utility.getCurrentTime());
        for (Event i : this.eventsToShow) {
            PairLong pair = new PairLong();
            pair.setFirst(timeStart);
            pair.setSecond(i.getTimeBegin());
            if (pair.hasEnoughTime()) freeTimeIntervals.add(pair);

            timeStart = Math.max(timeStart, i.getTimeEnd());
        }

        PairLong pair = new PairLong();
        pair.setFirst(timeStart);
        Long timeEnd = Utility.getTimeStampFromDateHoursMinutes(date, 22, 30);
        pair.setSecond(timeEnd);
        if (pair.hasEnoughTime()) freeTimeIntervals.add(pair);

        return freeTimeIntervals;
    }
}
