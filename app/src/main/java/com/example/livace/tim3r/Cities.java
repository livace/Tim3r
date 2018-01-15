package com.example.livace.tim3r;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by livace on 15.01.2018.
 */

public class Cities {
    private static ArrayList<City> citiesList = null;
    public static void downloadCities() {
        new Downloader(new Downloader.OnCompleteListener() {
            @Override
            public void onComplete(String result) {
                if (result == null) {
                    return;
                }
                try {
                    citiesList = City.sGetArrayListFromJson(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute(City.API_URL);
    }

    public City getCityBySlug(String slug) {
        for (City x : citiesList) {
            if (x.slug.equals(slug)) {
                return x;
            }
        }
        return null;
    }
}
