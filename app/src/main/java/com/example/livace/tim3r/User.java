package com.example.livace.tim3r;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by livace on 12.01.2018.
 */

public class User {
    private static String PREFERENCES = "userPreferences";
    private static String USER_NAME = "userName";
    private static String CITY_SLUG = "citySlug";
    private static String INTERESTS = "interests";
    private static String LOGGED = ""

    private static SharedPreferences preferences = null;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_NAME, name);
        editor.apply();
    }
    public static void setCitySlug(String slug) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CITY_SLUG, slug);
        editor.apply();
    }
    public static void setInterests(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(INTERESTS, value);
        editor.apply();
    }
}
