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

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        mTextView = (TextView) findViewById(R.id.test);

        EventType et = EventTypes.getEventTypeById(2);
//        if (et == null) {
//            Log.e(TAG, "WTF");
//        } else {
//            Log.e(TAG, "WTFx2");
//        }
        if (et != null) {
            String text = String.valueOf(et.id) + " " + et.name + " " + et.slug;
            mTextView.setText(text);
        } else {
            mTextView.setText("Something gone wrong :(");
        }
    }
}
