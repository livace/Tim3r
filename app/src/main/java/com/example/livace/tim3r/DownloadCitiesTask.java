package com.example.livace.tim3r;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by livace on 11.01.2018.
 */

public class DownloadCitiesTask extends android.os.AsyncTask<String, Void, ArrayList<City>> {
    private static final String TAG = DownloadCitiesTask.class.getCanonicalName();

    private static final String URL = "https://kudago.com/public-api/v1.3/locations/";

    public DownloadCitiesTask(OnCompleteListener mOnCompleteListener) {
        this.mOnCompleteListener = mOnCompleteListener;
    }

    @Override
    protected ArrayList<City> doInBackground(String... strings) {
        String file = downloadFile(URL);
        if (file == null) {
            return null;
        }

        Log.e(TAG, file);

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(file);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray == null) {
            return null;
        }

        ArrayList<City> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String slug = jsonObject.getString("slug");
                result.add(new City(name, slug));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private final OnCompleteListener mOnCompleteListener;

    private String downloadFile(String stringUrl) {
        String result = null;

        InputStream is = null;

        try {
            URL url = new URL(stringUrl);
            URLConnection connection = (URLConnection) url.openConnection();

            connection.connect();

            is = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"),
                    8);

            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            is.close();

            result = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    interface OnCompleteListener{
        void onComplete(ArrayList<City> cities);
    }

    @Override
    protected void onPostExecute(ArrayList<City> cities) {
        super.onPostExecute(cities);

        if (cities != null) {
            if (mOnCompleteListener != null) {
                mOnCompleteListener.onComplete(cities);
            }
        }
    }
}
