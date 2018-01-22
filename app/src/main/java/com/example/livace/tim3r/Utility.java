package com.example.livace.tim3r;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by livace on 13.01.2018.
 */

public class Utility {
    public static ArrayList<JSONObject> arrayListFromString(String text) throws JSONException {
        if (text == null) {
            return null;
        }

        JSONArray jsonArray = null;

        jsonArray = new JSONArray(text);

        ArrayList<JSONObject> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(jsonObject);
        }
        return result;
    }

    public static long getDayFromTimeStamp(long timeStamp) {
        return timeStamp / (1000 * 60 * 60 * 24);
    }

    public static long getTimeStampFromDate(long date) {
        return date * 86400 * 1000;
    }

    public static long getCurrentTime() {
        return (new Date()).getTime();
    }

    public static long getCurrentDate() {
        return getDayFromTimeStamp(getCurrentTime());
    }

    public static long getTimeStampFromDateHoursMinutes(long date, long hours, long minutes) {

        Date dDate = new Date();
        dDate.setTime(getTimeStampFromDate(date));

        Log.e("Date", SimpleDateFormat.getDateTimeInstance().format(dDate));

        TimeZone timeZone = TimeZone.getDefault();
        long timeOfTimezone = timeZone.getRawOffset() / (60 * 60 * 1000);

        return getTimeStampFromDate(date) + 3600 * 1000 * hours + 60 * 1000 * minutes -
                timeOfTimezone;
    }
}
