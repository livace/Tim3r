package com.example.livace.tim3r;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by livace and CrazyDream on 11.01.2018.
 */

public class City {
    public final static String API_URL = "https://kudago.com/public-api/v1.3/locations/";

    public final String name;
    public final String slug;

    public City(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public static City sGetCityFromJson(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String slug = jsonObject.getString("slug");
        return new City(name, slug);
    }

    public static ArrayList<City> sGetArrayListFromJson(String file) throws JSONException {
        final ArrayList<JSONObject> objects = Utility.arrayListFromString(file);
        final ArrayList<City> result = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.get(i);
            result.add(sGetCityFromJson(jsonObject));
        }
        return result;
    }

}
