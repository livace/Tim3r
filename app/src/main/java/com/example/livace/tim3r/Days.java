package com.example.livace.tim3r;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by livace on 14.01.2018.
 */

public class Days {
    private final static Map<Long, Day> days = new HashMap<>();

    private static Day createDayFromTimeStamp(long timeStamp) {
        long date = Utility.getDayFromTimeStamp(timeStamp);
        return new Day(date);
    }

    private static Day createDayFromDate(long date) {
        return new Day(date);
    }

    public static Day getDayFromTimeStamp(long timeStamp) {
        long date = Utility.getDayFromTimeStamp(timeStamp);
        return getDayFromDate(date);
    }

    public static Day getDayFromDate(long date) {
        if (days.containsKey(date)) {
            return days.get(date);
        }
        Day day = createDayFromDate(date);
        days.put(date, day);
        return day;
    }
}
