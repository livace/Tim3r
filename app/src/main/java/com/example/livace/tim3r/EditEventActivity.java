package com.example.livace.tim3r;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EditEventActivity extends AppCompatActivity {
    private static String DATE = EditEventActivity.class.getCanonicalName() + "date";
    private static String EVENT_ID = EditEventActivity.class.getCanonicalName() + "eventId";

    private EditText mBeginHours;
    private EditText mEndHours;
    private EditText mBeginMinutes;
    private EditText mEndMinutes;

    private Long mDate;

    private EditText mTitle;
    private EditText mDesc;

    private Button mButton;

    private Event event = null;

    private Long mEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        ActionBar actionBar = getActionBar();
        if (actionBar == null) {
//            Toast.makeText(getApplicationContext(), "Action bar is null", Toast.LENGTH_SHORT)
//                    .show();
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mDate = intent.getLongExtra(DATE, Utility.getCurrentDate());
        mEventId = getIntent().getLongExtra(EVENT_ID, -1);

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

        if (mEventId != -1) {
            event = DatabaseFunctions.FindEventById(mEventId);
            mDate = event.getDate();


            mBeginHours.setText(event.getBeginHours());
            mBeginMinutes.setText(event.getBeginMinutes());

            mEndHours.setText(event.getEndHours());
            mEndMinutes.setText(event.getEndMinutes());

            mTitle.setText(event.getTitle());
            mDesc.setText(event.getDescription());
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBuilder eb = new EventBuilder();

                eb.setTitle(mTitle.getText().toString());
                long beginHours = Long.valueOf(mBeginHours.getText().toString());
                long beginMinutes = Long.valueOf(mBeginMinutes.getText().toString());

                eb.setTimeBegin(
                        Utility.getTimeStampFromDateHoursMinutes(mDate, beginHours, beginMinutes)
                );
                long endHours = Long.valueOf(mEndHours.getText().toString());
                long endMinutes = Long.valueOf(mEndMinutes.getText().toString());

                eb.setTimeEnd(
                        Utility.getTimeStampFromDateHoursMinutes(mDate, endHours, endMinutes)
                );

                if (event != null) {
                    DatabaseFunctions.removeFromDb(event);
                }

                event = eb.build();

                DatabaseFunctions.saveToDb(event);

                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.event == null) {
            return super.onCreateOptionsMenu(menu);
        }

        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("Menu", "Item Selected");
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                DatabaseFunctions.removeFromDb(event);
                onBackPressed();
                Log.e("Delete", "deleted");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static Intent getStartingIntentAdd(Context ctx, long date) {
        Intent intent = new Intent(ctx, EditEventActivity.class);
        intent.putExtra(DATE, date);
        return intent;
    }

    public static Intent getStartingIntentAdd(Context ctx) {
        return new Intent(ctx, EditEventActivity.class);
    }

    public static Intent getStartingIntentEdit(Context ctx, Event event) {
        Intent intent = new Intent(ctx, EditEventActivity.class);
        intent.putExtra(EVENT_ID, event.getId());
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
