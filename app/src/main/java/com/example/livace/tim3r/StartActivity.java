package com.example.livace.tim3r;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private final static String USER_LOGGED = "userLogged";
    private final String APP_PREFERENCES = "AppSettings";
    private final static String TAG = StartActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        EventTypes.downloadEventTypes(getApplicationContext());
        Cities.downloadCities(getApplicationContext());

        DatabaseFunctions.setContext(getApplicationContext());

        User.init(getApplicationContext());

        if(!preferences.contains(USER_LOGGED) || true) { // TODO: Change
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(USER_LOGGED, 1);
            editor.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(intent.getFlags() |
                    Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(intent.getFlags() |
                    Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
