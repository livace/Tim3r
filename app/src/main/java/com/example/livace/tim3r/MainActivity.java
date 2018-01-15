package com.example.livace.tim3r;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getCanonicalName();

    GestureDetector mDetector;

    List<City> mCitiesList;
    List<EventType> mEventTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCitiesList = new ArrayList<>();
        mEventTypes = new ArrayList<>();

        EventTypes.downloadEventTypes();
        Cities.downloadCities();
    }
}
