package com.example.livace.tim3r;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final static String APP_PREFERENCES = "AppSettings";
    private final static String USER_LOGGED = "userLogged";

    private long currentDay;

    private Fragment fragment;

    private BottomNavigationView mNavigation;

    private TextView mToolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent = null;

            InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
            if (imm != null && getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
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

        SharedPreferences firstEnter = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(!firstEnter.contains(USER_LOGGED)) {
            SharedPreferences.Editor editor = firstEnter.edit();
            Toast.makeText(this, "First enter", Toast.LENGTH_LONG).show();
            editor.putInt(USER_LOGGED, 1);
            editor.apply();
        }

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        DatabaseFunctions.setContext(getApplicationContext());

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mToolbarTitle = (TextView) findViewById(R.id.text_view_toolbar_title);

        showFeed(Utility.getCurrentDate());
    }

    public void showFeed(long day) {
        if (fragment instanceof DayFragment && ((DayFragment) fragment).getDate() == day) {
            return;
        }

        fragment = DayFragment.newInstance(day);
        currentDay = day;

        String formattedDate = DateFormat.getDateInstance(DateFormat.LONG).format(
                Utility.getTimeStampFromDate(currentDay));
        mToolbarTitle.setText(formattedDate);

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                fragment, DayFragment.TAG).commit();
    }

    public void showFeed() {
        showFeed(currentDay);
    }

    private void showCalendar(long day) {
        mToolbarTitle.setText(R.string.calendar);
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
        mToolbarTitle.setText(R.string.add_task);
        if ((fragment instanceof EditEventFragment) && currentDay == day) {
            return;
        }

        fragment = EditEventFragment.newInstance(currentDay);
        currentDay = day;

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                fragment, EditEventFragment.TAG).commit();
    }

    public void showEditEvent(Event event) {
        mToolbarTitle.setText(R.string.edit_task);
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
            selectHome(Utility.getCurrentDate());
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
