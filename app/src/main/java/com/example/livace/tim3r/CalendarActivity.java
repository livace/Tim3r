package com.example.livace.tim3r;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_calendar);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int day) {

//                int mYear = year;
//                int mMonth = month;
//                int mDay = dayOfMonth;
//                long mseconds = 0;
//                if (month < 3) {
//                    month += 12;
//                    year -= 1;
//                }
//                mseconds = ((dayOfMonth + (153 * month - 457) / 5 + 365 * year + year / 4 - year / 100 + year / 400 - 306) - 693596);
//                Toast.makeText(getApplicationContext(), String.valueOf(mseconds), Toast.LENGTH_LONG).show();
//                String selectedDate = new StringBuilder().append(mMonth + 1)
//                        .append("-").append(mDay).append("-").append(mYear)
//                        .append(" ").toString();
//                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                long mseconds = calendar.getTimeInMillis();
                Toast.makeText(getApplicationContext(), String.valueOf(mseconds), Toast.LENGTH_LONG).show();
            }
        });
    }
}
