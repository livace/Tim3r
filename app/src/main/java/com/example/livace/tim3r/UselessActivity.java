package com.example.livace.tim3r;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.TextView;

public class UselessActivity extends AppCompatActivity {
    private static String TAG = UselessActivity.class.getCanonicalName();

    GestureDetector mDetector;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useless_main);

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        mTextView = (TextView) findViewById(R.id.test);

    }
}
