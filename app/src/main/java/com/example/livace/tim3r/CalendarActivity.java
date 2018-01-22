package com.example.livace.tim3r;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Admin on 14.01.2018.
 */

public class CalendarActivity  extends AppCompatActivity {
    long returningDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_calendar);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                long mseconds = calendar.getTimeInMillis();
               // Toast.makeText(getApplicationContext(), String.valueOf(mseconds), Toast.LENGTH_LONG).show();

                returningDate = Utility.getDayFromTimeStamp(mseconds);

                Intent intent = new Intent();
                intent.putExtra("day", returningDate);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
