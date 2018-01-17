package com.example.livace.tim3r;

import android.content.Context;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DATE = "date";

    public static final String TAG = Fragment.class.getCanonicalName();

    private Day mDay;

    private Long mDate;

    private ArrayList<Event> mEvents;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;

    private DayFragmentAdapter mCustomAdapter;

    public DayFragment() {
        // Required empty public constructor
    }

    private GestureDetector mDetector;

    private GestureDetector.SimpleOnGestureListener listener =
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child == null) {
                        return false;
                    }
                    RecyclerView.ViewHolder holder =
                            mRecyclerView.findContainingViewHolder(child);
                    if (holder instanceof DayFragmentAdapter.ViewHolder) {
                        Toast.makeText(getContext(), "Hi!", Toast.LENGTH_LONG);
                        // TODO: Open edit Activity

                        return true;
                    }

                    return false;
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }
            };


    public static DayFragment newInstance(Long date) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = getArguments().getLong(DATE);
        } else {
            mDate = Utility.getDayFromTimeStamp((new Date()).getTime());
        }
        mDay = Days.getDay(mDate);
        mEvents = mDay.getEventsToShow();
        mCustomAdapter = new DayFragmentAdapter(mEvents);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.setAdapter(mCustomAdapter);

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
