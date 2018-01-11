package com.example.livace.tim3r;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new DownloadCitiesTask(new DownloadCitiesTask.OnCompleteListener() {
            @Override
            public void onComplete(ArrayList<City> cities) {
                TextView textView = (TextView) findViewById(R.id.cities);
                StringBuilder result = new StringBuilder();
                for (City city : cities) {
                    result.append(city.slug).append(":").append(city.name).append("\n");
                }
                textView.setText(result.toString());
            }
        }).execute();
    }
}
