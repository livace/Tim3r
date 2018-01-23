package com.example.livace.tim3r;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by livace on 14.01.2018.
 */

public class Event implements Comparable {
//    private static final String API_URL =
//            "https://kudago.com/public-api/v1.3/events/?fields=short_title,dates,title," +
//                    "description&text_format=text&actual_since=%d&actual_until=%d&categories" +
//                    "=&location=sochi";
//
    private static final String API_URL =
            "https://kudago.com/public-api/v1.3/events/?fields=short_title,dates,title," +
                    "description&text_format=text&actual_since=%d&actual_until=%d&categories" +
                    "=concert&location=";

    private EventType type;
    private Long timeBegin;
    private Long timeEnd;
    private String title;
    private String description;
    private Bitmap image;
    private City city;
    private String imageUrl;
    private Long id;

    private Long date;

    private boolean promoted;

    public Event(EventType type,
                 Long timeBegin,
                 Long timeEnd,
                 String title,
                 String description,
                 String imageUrl,
                 City city,
                 boolean promoted,
                 long id) {
        this.type = type;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.title = title;
        this.description = description;
        this.date = Utility.getDayFromTimeStamp(timeBegin);
        this.imageUrl = imageUrl;
        this.city = city;
        this.promoted = promoted;
        this.id = id;
    }
    private static Event parseFromJson(final String json, final Long timeBegin, final Long timeEnd)
            throws JSONException {
        JSONObject mainObject = new JSONObject(json);

        int count = mainObject.getInt("count");

        if (count == 0) {
            return null;
        }

        JSONArray results = mainObject.getJSONArray("results");

        JSONObject eventObject = results.getJSONObject(0);

        EventBuilder eb = new EventBuilder();

        String title = eventObject.getString("short_title");
        eb.setTitle(title);

        JSONArray dates = eventObject.getJSONArray("dates");

        Long resultTimeBegin = timeEnd;
        Long resultTimeEnd = timeBegin;

        Log.e("Parse", String.format("Found %d dates", dates.length()));

        for (int i = 0; i < dates.length(); ++i) {
            JSONObject date = dates.getJSONObject(i);
            resultTimeBegin = Math.min(date.getLong("start") * 1000, resultTimeBegin);
            resultTimeEnd = Math.max(date.getLong("end") * 1000, resultTimeEnd);
            // API gives seconds, need milliseconds
        }

        eb.setTimeBegin(timeBegin);
        eb.setTimeEnd(timeEnd);

        String desc = eventObject.getString("description");

        eb.setPromoted(true);

        eb.setDescription(desc);

        return eb.build();
    }

    public static void findInApi(final Long timeBegin,
                                 final Long timeEnd,
                                 final onDownloadEventListener listener) {
        final Long askTimeBegin = timeBegin / 1000;
        final Long askTimeEnd = timeEnd / 1000; // Time is in milliseconds, api want in seconds

        String url = String.format(API_URL, askTimeBegin, askTimeEnd);

        final Event[] event = {null};

        new Downloader(new Downloader.OnCompleteListener() {
            @Override
            public void onComplete(String result) {
                if (result == null) {
                    return;
                }
                try {
                    event[0] = Event.parseFromJson(result, timeBegin, timeEnd);
                    if (listener != null) listener.onComplete(event[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute(url);
    }

    public Long getDate() {
        return date;
    }

    public EventType getType() {
        return type;
    }

    public Long getTimeBegin() {
        return timeBegin;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public City getCity() {
        return city;
    }

    public Long getId() {
        return id;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int result = timeBegin.hashCode();
        result = result * 1000007 + timeEnd.hashCode();
        return result;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Event other = (Event) o;
        if (other.getTimeBegin().equals(this.getTimeBegin())) {
            return this.getTimeEnd().compareTo(other.getTimeEnd());
        }
        return this.getTimeBegin().compareTo(other.getTimeBegin());
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", timeBegin=" + timeBegin +
                ", timeEnd=" + timeEnd +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", city=" + city +
                ", imageUrl='" + imageUrl + '\'' +
                ", id=" + id +
                ", date=" + date +
                '}';
    }

    public String getTimeString() {
        Date beginDate = new Date(timeBegin);
        Date endDate = new Date(timeEnd);

        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance(DateFormat.SHORT);

        return String.format("%s - %s", format.format(beginDate), format.format(endDate));
    }

    private String getHours(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = (SimpleDateFormat) new SimpleDateFormat("HH");
        return format.format(date);
    }

    private String getMinutes(Long time) {
        Date date = new Date(time);
        SimpleDateFormat format = (SimpleDateFormat) new SimpleDateFormat("mm");
        return format.format(date);
    }

    public String getBeginHours() {
        return getHours(timeBegin);
    }

    public String getEndHours() {
        return getHours(timeEnd);
    }

    public String getBeginMinutes() {
        return getMinutes(timeBegin);
    }

    public String getEndMinutes() {
        return getMinutes(timeEnd);
    }

    public String getDurationString() {
        Long duration = timeEnd - timeBegin;
        Date durationDate = new Date();

        TimeZone timeZone = TimeZone.getDefault();
        long timeMinus = -timeZone.getRawOffset();

        durationDate.setTime(duration + timeMinus);

        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat
                .getTimeInstance(DateFormat.SHORT);
        return format.format(durationDate);
    }

    public void saveToDb() {
        this.promoted = false;
        DatabaseFunctions.saveToDb(this);
    }

    public void removeFromDb() {
        DatabaseFunctions.removeFromDb(this);
    }

    interface onDownloadEventListener {
        void onComplete(Event event);
    }
}
