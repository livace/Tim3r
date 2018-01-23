package com.example.livace.tim3r;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 19.01.2018.
 */

public class Interests extends AppCompatActivity {
    ListView choiceList;
    TextView selection;
    ArrayList<String> things = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);

        ArrayList<EventType> listOfInterests = EventTypes.getEventTypes();

        for (EventType i : listOfInterests){
            things.add(i.name);
        }


        selection = (TextView) findViewById(R.id.textView1);
        choiceList = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, things);
        choiceList.setAdapter(adapter);

        choiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                SparseBooleanArray chosen = ((ListView) parent).getCheckedItemPositions();
                for (int i = 0; i < chosen.size(); i++) {
                    if (chosen.valueAt(i)) {

                    }
                }
            }
        });
    }
}
