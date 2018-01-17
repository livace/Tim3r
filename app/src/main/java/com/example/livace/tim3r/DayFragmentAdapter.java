package com.example.livace.tim3r;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.livace.tim3r.Event;
import com.example.livace.tim3r.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DayFragmentAdapter extends RecyclerView.Adapter<DayFragmentAdapter.ViewHolder> {

    static final String TAG = DayFragmentAdapter.class.getCanonicalName();

    private final List<Event> mDataSet;

    public DayFragmentAdapter() {
        super();
        mDataSet = new ArrayList<Event>();
    }

    public DayFragmentAdapter(List<Event> data) {
        super();
        mDataSet = data;
    }

    public void addData(List<Event> data) {
        mDataSet.addAll(data);
    }
    public void resetData(List<Event> data) {
        mDataSet.clear();
        mDataSet.addAll(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDuration;
        private TextView textViewTime;

        private Event event;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            textViewDuration = (TextView) itemView.findViewById(R.id.text_view_duration);
            textViewTime = (TextView) itemView.findViewById(R.id.text_view_time);
        }

        public static ViewHolder create(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.event_card, parent, false));
        }

        public void setData(Event data) {
            textViewTime.setText(data.getTimeString());
            textViewTitle.setText(data.getTitle());
            textViewDuration.setText(data.getDurationString());
            event = data;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
