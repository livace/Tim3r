package com.example.livace.tim3r;

import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by livace on 15.01.2018.
 */

public class EventTypes {
    private static ArrayList<EventType> eventTypes = null;

    public static void downloadEventTypes() {
        new Downloader(new Downloader.OnCompleteListener() {
            @Override
            public void onComplete(String result) {
                if (result == null) {
                    return;
                }
                try {
                    eventTypes = EventType.sGetArrayListFromJson(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute(EventType.API_URL);
    }

    public EventType getEventTypeById(int id) {
        for (EventType x : eventTypes) {
            if (x.id == id) {
                return x;
            }
        }
        return null;
    }
}
