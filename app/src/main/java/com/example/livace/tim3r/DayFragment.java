package com.example.livace.tim3r;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.DateFormat;
import java.util.ArrayList;

public class DayFragment extends Fragment {
    private static final String DATE = "date";
    public static final String TAG = Fragment.class.getCanonicalName();

    private Day mDay;
    private Long mDate;

    public Long getDate() {
        return mDate;
    }

    private ArrayList<Event> mEvents;

    private RecyclerView mRecyclerView;

    private DayFragmentAdapter mCustomAdapter;

    public DayFragment() {
        // Required empty public constructor
    }

    private GestureDetector mDetector;

    private void updateEvents() {
        mDay.updateEventsToShow();
    }

    private GestureDetector.SimpleOnGestureListener mListener = new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child == null) {
                        return false;
                    }
                    RecyclerView.ViewHolder holder =
                            mRecyclerView.findContainingViewHolder(child);
                    if (holder instanceof DayFragmentAdapter.ViewHolder) {
                        Event event = ((DayFragmentAdapter.ViewHolder) holder).getEvent();
                        if (event.isPromoted()) {
                            showAddingDialog(event);
                            return true;
                        }
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).showEditEvent(event);
                        }

                        return true;
                    }

                    return false;
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }
            };

    private void showAddingDialog(final Event event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.add_task_dialog_message)
                .setPositiveButton(R.string.add_task_dialog_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        event.saveToDb();
                        mCustomAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.add_task_dialog_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public static DayFragment newInstance(Long date) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    public static DayFragment newInstance() {
        return new DayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDate = getArguments().getLong(DATE);
        } else {
            mDate = Utility.getCurrentDate();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        mCustomAdapter = null;
        mDay = Days.getDayFromDate(mDate, new Day.onUpdateListener() {
            @Override
            public void onUpdate() {
                if (mCustomAdapter != null) {
                    mCustomAdapter.notifyDataSetChanged();
                }
            }
        });
        mEvents = mDay.getEventsToShow();
        mCustomAdapter = new DayFragmentAdapter(mEvents);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mCustomAdapter);

        updateEvents();

        mDetector = new GestureDetector(getActivity(), mListener);

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                mDetector.onTouchEvent(e);

                return super.onInterceptTouchEvent(rv, e);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
