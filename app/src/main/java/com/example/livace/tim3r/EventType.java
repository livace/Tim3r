package com.example.livace.tim3r;

import android.content.Intent;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by livace on 12.01.2018.
 */

public class EventType implements Comparable<EventType>{
    public static String API_URL = "https://kudago.com/public-api/v1.3/event-categories/";

    public final Integer id;
    public final String name;
    public final String slug;



    public EventType(Integer id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    public static EventType sGetCityFromJson(JSONObject jsonObject) throws JSONException {
        Integer id = Integer.valueOf(jsonObject.getString("id"));
        String name = jsonObject.getString("name");
        String slug = jsonObject.getString("slug");
        return new EventType(id, name, slug);
    }

    public static ArrayList<EventType> sGetArrayListFromJson(String file) throws JSONException {
        final ArrayList<JSONObject> objects = Utility.arrayListFromString(file);
        final ArrayList<EventType> result = new ArrayList<>();
        for (JSONObject jsonObject : objects) {
            result.add(sGetCityFromJson(jsonObject));
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public int compareTo(@NonNull EventType eventType) {
        return this.name.compareTo(eventType.name);
    }
}
