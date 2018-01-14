package com.example.livace.tim3r;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by livace on 14.01.2018.
 */

public class Days {
    private Map<Long, Day> days = new HashMap<>();

    private Day createDay(long timeStamp) {
        long date = Utility.getDayFromTimeStamp(timeStamp);
        return new Day(date);
    }

    public Day getDay(long timeStamp) {
        long date = Utility.getDayFromTimeStamp(timeStamp);
        if (days.containsKey(date)) {
            return days.get(date);
        }
        return createDay(timeStamp);
    }
}
