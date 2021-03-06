package com.example.livace.tim3r;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by livace on 15.01.2018.
 */

public class Cities {
    private static ArrayList<City> citiesList = null;
    public static void downloadCities(Context ctx) {
        String json = ctx.getResources().getString(R.string.json_cities);
        try {
            citiesList = City.sGetArrayListFromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static City getCityBySlug(String slug) {
        for (City x : citiesList) {
            if (x.slug.equals(slug)) {
                return x;
            }
        }
        return null;
    }

    public static String getSlugByName(String name) {
        for (City x : citiesList) {
            if (x.name.equals(name)) {
                return x.slug;
            }
        }
        return "";
    }
    public static ArrayList<String> getCitiesNames() {
        ArrayList<String> names = new ArrayList<>();
        for (City city : citiesList) {
            names.add(city.name);
        }
        return names;
    }
}
