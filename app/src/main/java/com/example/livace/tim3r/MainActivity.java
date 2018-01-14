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

//    TextView mTextView;
    List<City> mCitiesList;
    List<EventType> mEventTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextView = (TextView) findViewById(R.id.cities);
        mCitiesList = new ArrayList<>();
        mEventTypes = new ArrayList<>();

        new Downloader(new Downloader.OnCompleteListener() {
            @Override
            public void onComplete(String result) {
                if (result == null) {
                    Toast.makeText(MainActivity.this,
                            "Unable to download cities...",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    mCitiesList.addAll(City.sGetArrayListFromJson(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute(City.API_URL);

        new Downloader(new Downloader.OnCompleteListener() {
            @Override
            public void onComplete(String result) {
                Log.e(TAG, result);
                if (result == null) {
                    Toast.makeText(MainActivity.this,
                            "Unable to download categories...",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    mEventTypes.addAll(EventType.sGetArrayListFromJson(result));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                StringBuilder text = new StringBuilder();
                for (EventType eventType : mEventTypes) {
                    text.append(eventType.id).append(":").append(eventType.name).append(':')
                            .append(eventType.slug).append('\n');
                }
//                mTextView.setText(text.toString());
            }
        }).execute(EventType.API_URL);

    }
}
