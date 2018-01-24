package com.example.livace.tim3r;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 19.01.2018.
 */

public class InterestsActivity extends AppCompatActivity {
    ListView choiceList;
    ArrayList<String> things = new ArrayList<>();

    Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        final ArrayList<EventType> listOfInterests = EventTypes.getEventTypes();

        for (EventType i : listOfInterests){
            things.add(i.name);
        }

        choiceList = (ListView) findViewById(R.id.list_view_interest_chose);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, things);
        choiceList.setAdapter(adapter);

        mButton = (Button) findViewById(R.id.interests_btn_ok);

        final ArrayList<Boolean> checked = new ArrayList<>();

        for (int i = 0; i < listOfInterests.size(); i++) {
            checked.add(false);
        }
        choiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checked.set(position, !checked.get(position));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < checked.size(); i++) {
                    if (checked.get(i)) {
                        if (stringBuilder.toString().equals("")) {
                            stringBuilder.append(listOfInterests.get(i).slug);
                        } else {
                            stringBuilder.append(",").append(listOfInterests.get(i).slug);
                        }
                    }
                }

                User.setInterests(stringBuilder.toString());

                User.log();

                Intent intent = new Intent(InterestsActivity.this, MainActivity.class);
                intent.setFlags(intent.getFlags()
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }
}