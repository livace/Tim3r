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

public class Downloader extends android.os.AsyncTask<String, Void, String> {
    private static final String TAG = Downloader.class.getCanonicalName();

    public Downloader(OnCompleteListener mOnCompleteListener) {
        this.mOnCompleteListener = mOnCompleteListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (strings == null || strings[0] == null) {
            return null;
        }

        String url = strings[0];

        return downloadFile(url);
    }


    private static String downloadFile(String stringUrl) {
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

    private final OnCompleteListener mOnCompleteListener;

    interface OnCompleteListener {
        void onComplete(String result);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (mOnCompleteListener != null) {
            mOnCompleteListener.onComplete(result);
        }
    }
}
