package com.example.livace.tim3r;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private long dayToShow;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_calendar:
                    Intent intentCalendar = new Intent(MainActivity.this, CalendarActivity.class);
                    startActivityForResult(intentCalendar, 1);
                    return true;
                case R.id.navigation_adding_a_task:
                    Intent intent = new Intent(MainActivity.this, EditEventActivity.class);
                    intent.putExtra("dayToShow", dayToShow);
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

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        DatabaseFunctions.setContext(getApplicationContext());

        setDayToShow(Utility.getCurrentDate());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Intent login = new Intent(this, LoginActivity.class);
        //startActivity(login);

        Fragment dayFragment = getFragmentManager().findFragmentById(R.id.fragment_placeholder);

        if (!(dayFragment instanceof DayFragment)) {
            dayFragment = new DayFragment();
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                dayFragment, DayFragment.TAG).commit();
    }

    public void setDayToShow (long day){
        dayToShow = day;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Fragment dayFragment = DayFragment.newInstance(dayToShow);
        getFragmentManager().beginTransaction().replace(R.id.fragment_placeholder,
                dayFragment, DayFragment.TAG).commit();
    }
}
