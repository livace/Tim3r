package com.example.livace.tim3r;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class EditEventFragment extends Fragment {
    private static String DATE = EditEventFragment.class.getCanonicalName() + "date";
    private static String EVENT_ID = EditEventFragment.class.getCanonicalName() + "eventId";

    public static String TAG = EditEventFragment.class.getCanonicalName();

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

    public static Intent getStartingIntentAdd(Context ctx, long date) {
        Intent intent = new Intent(ctx, EditEventFragment.class);
        intent.putExtra(DATE, date);
        return intent;
    }

    public static Intent getStartingIntentAdd(Context ctx) {
        return new Intent(ctx, EditEventFragment.class);
    }

    public static Intent getStartingIntentEdit(Context ctx, Event event) {
        Intent intent = new Intent(ctx, EditEventFragment.class);
        intent.putExtra(EVENT_ID, event.getId());
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//            actionBar.setDisplayHomeAsUpEnabled(true);

        if (getArguments() != null) {
            mDate = getArguments().getLong(DATE, Utility.getCurrentDate());
            mEventId = getArguments().getLong(EVENT_ID, -1);
        }

    }

    public EditEventFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);

        setHasOptionsMenu(true);

        mTitle = (EditText) view.findViewById(R.id.edit_text_title);
        mDesc = (EditText) view.findViewById(R.id.edit_text_desc);
        mBeginHours = (EditText) view.findViewById(R.id.edit_text_begin_hours);
        mBeginMinutes = (EditText) view.findViewById(R.id.edit_text_begin_minutes);
        mEndHours = (EditText) view.findViewById(R.id.edit_text_end_hours);
        mEndMinutes = (EditText) view.findViewById(R.id.edit_text_end_minutes);

        mBeginHours.setFilters(new InputFilter[]{new MinMaxFilter(0, 23)});
        mEndHours.setFilters(new InputFilter[]{new MinMaxFilter(0, 23)});

        mBeginMinutes.setFilters(new InputFilter[]{new MinMaxFilter(0, 59)});
        mEndMinutes.setFilters(new InputFilter[]{new MinMaxFilter(0, 59)});

        mButton = (Button) view.findViewById(R.id.btn_ok);

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

                String title = mTitle.getText().toString();
                if (title.equals("")) title = "Untitled task";
                eb.setTitle(title);

                long beginHours;
                long beginMinutes;
                if (mBeginHours.getText().toString().equals("")) {
                    beginHours = 10;
                } else {
                    beginHours = Long.valueOf(mBeginHours.getText().toString());
                }
                if (mBeginMinutes.getText().toString().equals("")) {
                    beginMinutes = 0;
                } else {
                    beginMinutes = Long.valueOf(mBeginMinutes.getText().toString());
                }

                eb.setTimeBegin(
                        Utility.getTimeStampFromDateHoursMinutes(mDate, beginHours, beginMinutes)
                );

                long endHours;
                long endMinutes;
                if (mEndHours.getText().toString().equals("")) {
                    endHours = 11;
                } else {
                    endHours = Long.valueOf(mEndHours.getText().toString());
                }
                if (mEndMinutes.getText().toString().equals("")) {
                    endMinutes = 0;
                } else {
                    endMinutes = Long.valueOf(mEndMinutes.getText().toString());
                }
                eb.setTimeEnd(
                        Utility.getTimeStampFromDateHoursMinutes(mDate, endHours, endMinutes)
                );


                String desc = mDesc.getText().toString();
                eb.setDescription(desc);

                if (event != null) {
                    DatabaseFunctions.removeFromDb(event);
                }

                event = eb.build();

                event.saveToDb();

                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).selectHome();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mEventId != -1) {
            inflater.inflate(R.menu.delete, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                event.removeFromDb();
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).selectHome();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private class MinMaxFilter implements InputFilter {

        private int mIntMin, mIntMax;

        public MinMaxFilter(int minValue, int maxValue) {
            this.mIntMin = minValue;
            this.mIntMax = maxValue;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int
                dStart, int dEnd) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (dest.length() > 1 && dest.charAt(0) == '0') {
                    return "";
                }
                if (isInRange(input))
                    return null;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return "";
        }

        private boolean isInRange(int val) {
            return val <= mIntMax && val >= mIntMin;
        }
    }

    public static Fragment newInstance(long date) {
        EditEventFragment fragment = new EditEventFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }
    public static Fragment newInstance(Event event) {
        EditEventFragment fragment = new EditEventFragment();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, event.getId());
        fragment.setArguments(args);
        return fragment;
    }
}
