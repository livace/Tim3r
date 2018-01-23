package com.example.livace.tim3r;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private long dayToShow;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_calendar:
                    intent = CalendarActivity.getStartingIntent(MainActivity.this,
                            dayToShow);
                    startActivityForResult(intent, 1);
                    return false;
                case R.id.navigation_adding_a_task:
                    intent = EditEventActivity.getStartingIntentAdd(MainActivity.this, dayToShow);
                    startActivity(intent);
                    return false;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        long day = data.getLongExtra("day", 1);
        setDayToShow(day);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String APP_PREFERENCES = "AppSettings";
        SharedPreferences firstEnter = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(!firstEnter.contains("firstEnter")) {

            //To do

            SharedPreferences.Editor editor = firstEnter.edit();
            String nameOfFirstEnter = "firstEnter";
            editor.putInt(nameOfFirstEnter, 1);
            editor.apply();
        }

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        DatabaseFunctions.setContext(getApplicationContext());

        setDayToShow(Utility.getCurrentDate());

        Date date = new Date(Utility.getTimeStampFromDate(dayToShow));
        String myString = DateFormat.getDateInstance(DateFormat.LONG).format(date);
        TextView showDate = findViewById(R.id.text_view_date);
        showDate.setText(myString);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Intent login = new Intent(this, LoginActivity.class);
        //startActivity(login);
    }

    public void setDayToShow (long day){
        dayToShow = day;

        Date date = new Date(Utility.getTimeStampFromDate(dayToShow));
        String myString = DateFormat.getDateInstance(DateFormat.LONG).format(date);
        TextView showDate = findViewById(R.id.text_view_date);
        showDate.setText(myString);

        Fragment dayFragment = DayFragment.newInstance(dayToShow);
        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                dayFragment, DayFragment.TAG).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (dayToShow == Utility.getCurrentDate()) {
            super.onBackPressed();
        } else {
            setDayToShow(Utility.getCurrentDate());
        }
    }
}
