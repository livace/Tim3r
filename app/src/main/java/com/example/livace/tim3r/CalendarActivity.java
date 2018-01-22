package com.example.livace.tim3r;

import android.content.Context;
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
    private static String DATE = "date";

    private CalendarView calendarView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_calendar);

        long date = getIntent().getLongExtra(DATE, Utility.getCurrentDate());

        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setDate(Utility.getTimeStampFromDate(date), false, true);

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

                long returningDate;
                returningDate = Utility.getDayFromTimeStamp(mseconds);

                Intent intent = new Intent();
                intent.putExtra("day", returningDate);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static Intent getStartingIntent(Context ctx, long date) {
        Intent intent = new Intent(ctx, CalendarActivity.class);
        intent.putExtra(DATE, date);
        return intent;
    }
}
