package com.example.livace.tim3r;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

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
        return timeStamp / 86400 / 1000;
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
}
