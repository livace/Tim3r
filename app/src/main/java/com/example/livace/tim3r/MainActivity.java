package com.example.livace.tim3r;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private long currentDay;

    private Fragment fragment;

    private BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFeed(currentDay);
                    return true;
                case R.id.navigation_calendar:
                    showCalendar(currentDay);
                    return true;
                case R.id.navigation_adding_a_task:
                    showAddEvent(currentDay);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        DatabaseFunctions.setContext(getApplicationContext());

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        showFeed(Utility.getCurrentDate());
    }

    public void showFeed(long day) {
        if (fragment instanceof DayFragment && currentDay == day) {
            return;
        }

        fragment = DayFragment.newInstance(day);
        currentDay = day;

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                fragment, DayFragment.TAG).commit();
    }

    public void showFeed() {
        showFeed(currentDay);
    }

    private void showCalendar(long day) {
        if (fragment instanceof CalendarFragment) {
            ((CalendarFragment) fragment).setDay(day);
            return;
        }

        fragment = CalendarFragment.newInstance(currentDay);
        currentDay = day;

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                fragment, CalendarFragment.TAG).commit();
    }

    private void showAddEvent(long day) {
        if ((fragment instanceof EditEventFragment) && currentDay == day) {
            return;
        }

        fragment = EditEventFragment.newInstance(currentDay);
        currentDay = day;

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                fragment, EditEventFragment.TAG).commit();
    }

    public void showEditEvent(Event event) {
        fragment = EditEventFragment.newInstance(event);
        currentDay = event.getDate();

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                fragment, EditEventFragment.TAG).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if ((fragment instanceof DayFragment) && (currentDay == Utility.getCurrentDate())) {
            super.onBackPressed();
        } else {
            showFeed(Utility.getCurrentDate());
            mNavigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    public void selectHome() {
        mNavigation.setSelectedItemId(R.id.navigation_home);
    }
    public void selectHome(long day) {
        currentDay = day;
        mNavigation.setSelectedItemId(R.id.navigation_home);
    }
    public void selectCalendar() {
        mNavigation.setSelectedItemId(R.id.navigation_calendar);
    }
    public void selectAdd() {
        mNavigation.setSelectedItemId(R.id.navigation_adding_a_task);
    }
}
