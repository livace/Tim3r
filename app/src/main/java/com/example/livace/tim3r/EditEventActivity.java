package com.example.livace.tim3r;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditEventActivity extends AppCompatActivity {
    private static String DATE = EditEventActivity.class.getCanonicalName() + "date";

    private EditText mBeginHours;
    private EditText mEndHours;
    private EditText mBeginMinutes;
    private EditText mEndMinutes;

    private Long mDate;

    private EditText mTitle;
    private EditText mDesc;

    private Button mButton;

    private Event event = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        mDate = getIntent().getLongExtra(DATE, Utility.getCurrentDate());

        mTitle = (EditText) findViewById(R.id.edit_text_title);
        mDesc = (EditText) findViewById(R.id.edit_text_desc);
        mBeginHours = (EditText) findViewById(R.id.edit_text_begin_hours);
        mBeginMinutes = (EditText) findViewById(R.id.edit_text_begin_minutes);
        mEndHours = (EditText) findViewById(R.id.edit_text_end_hours);
        mEndMinutes = (EditText) findViewById(R.id.edit_text_end_minutes);

        mBeginHours.setFilters(new InputFilter[]{new MinMaxFilter(0, 23)});
        mEndHours.setFilters(new InputFilter[]{new MinMaxFilter(0, 23)});

        mBeginMinutes.setFilters(new InputFilter[]{new MinMaxFilter(0, 59)});
        mEndMinutes.setFilters(new InputFilter[]{new MinMaxFilter(0, 59)});

        mButton = (Button) findViewById(R.id.btn_ok);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBuilder eb = new EventBuilder();

                Log.d("WTF", "WUT?");

                eb.setTitle(mTitle.getText().toString());
                long beginHours = Long.valueOf(mBeginHours.getText().toString());
                long beginMinutes = Long.valueOf(mBeginHours.getText().toString());
                eb.setTimeBegin(
                        Utility.getTimeStampFromDateHoursMinutes(mDate, beginHours, beginMinutes)
                );
                if (event != null) {
                    DatabaseFunctions.removeFromDb(event);
                }
                DatabaseFunctions.saveToDb(eb.build());
            }
        });
    }

    public static Intent getStartingIntent(Context ctx, long date) {
        Intent intent = new Intent(ctx, EditEventActivity.class);
        intent.putExtra(DATE, date);
        return intent;
    }


    public static Intent getStartingIntent(Context ctx) {
        Intent intent = new Intent(ctx, EditEventActivity.class);
        return intent;
    }

    private class MinMaxFilter implements InputFilter {

        private int mIntMin, mIntMax;

        public MinMaxFilter(int minValue, int maxValue) {
            this.mIntMin = minValue;
            this.mIntMax = maxValue;
        }

        public MinMaxFilter(String minValue, String maxValue) {
            this.mIntMin = Integer.parseInt(minValue);
            this.mIntMax = Integer.parseInt(maxValue);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (dest.length() > 1 && dest.charAt(0) == '0') {
                    return "";
                }
                if (isInRange(input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int val) {
            return val <= mIntMax && val >= mIntMin;
        }
    }
}
