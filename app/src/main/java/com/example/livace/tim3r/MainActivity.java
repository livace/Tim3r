package com.example.livace.tim3r;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_calendar:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_adding_a_task:
                    Intent intent = EditEventActivity.getStartingIntentAdd(MainActivity.this);
                    startActivity(intent);
                    return false;
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

}
