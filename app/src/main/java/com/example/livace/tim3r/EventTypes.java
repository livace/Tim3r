package com.example.livace.tim3r;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by livace on 15.01.2018.
 */

public class EventTypes {
    private static String TAG = EventType.class.getCanonicalName();
    private static ArrayList<EventType> eventTypes = null;

    private static EventType emptyEventType = new EventType(0, "Not event", "no");
    public static void downloadEventTypes(Context ctx) {
        String json = ctx.getResources().getString(R.string.json_event_types);
        try {
            eventTypes = EventType.sGetArrayListFromJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static EventType getEventTypeById(int id) {
        for (EventType x : eventTypes) {
            if (x.id == id) {
                return x;
            }
        }
        return emptyEventType;
    }

    public static ArrayList<EventType> getEventTypes() {
        return eventTypes;
    }
}
