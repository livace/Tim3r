package com.example.livace.tim3r;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by livace on 14.01.2018.
 */

public class Days {
    private final static Map<Long, Day> days = new HashMap<>();

    private static Day createDayFromTimeStamp(long timeStamp, Day.onUpdateListener listener) {
        long date = Utility.getDayFromTimeStamp(timeStamp);
        return new Day(date, listener);
    }

    private static Day createDayFromDate(long date, Day.onUpdateListener listener) {
        return new Day(date, listener);
    }

    public static Day getDayFromTimeStamp(long timeStamp, Day.onUpdateListener listener) {
        long date = Utility.getDayFromTimeStamp(timeStamp);
        return getDayFromDate(date, listener);
    }

    public static Day getDayFromDate(long date, Day.onUpdateListener listener) {
        if (days.containsKey(date)) {
            Day day = days.get(date);
            day.setListener(listener);
            return day;
        }
        Day day = createDayFromDate(date, listener);
        days.put(date, day);
        return day;
    }
}
