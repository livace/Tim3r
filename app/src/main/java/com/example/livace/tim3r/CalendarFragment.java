package com.example.livace.tim3r;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Admin on 14.01.2018.
 */

public class CalendarFragment extends Fragment {
    private static String DATE = "date";

    public static String TAG = CalendarFragment.class.getCanonicalName();

    private CalendarView calendarView = null;

    private long mDate;

    public CalendarFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDate = getArguments().getLong(DATE, Utility.getCurrentDate());
        } else {
            mDate = Utility.getCurrentDate();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = (CalendarView) view.findViewById(R.id.calendar_view);

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
                long milliseconds = calendar.getTimeInMillis();

                long result = Utility.getDayFromTimeStamp(milliseconds);

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity)getActivity()).selectHome(result);
                }
            }
        });

        return view;
    }

    public void setDay(long day) {
        calendarView.setDate(Utility.getTimeStampFromDate(mDate), false, true);
    }

    public static CalendarFragment newInstance(long date) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }
}
