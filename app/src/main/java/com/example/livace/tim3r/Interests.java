package com.example.livace.tim3r;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Admin on 19.01.2018.
 */

public class Interests extends AppCompatActivity {
    ListView choiceList;
    TextView selection;
    String[] things = { "Кино", "Театр", "Концерт", "Библиотека", "Клуб",
            "Каток", "Выставка", "Музей", "Кафе", "Что-то еще", "Что-то еще",
            "Что-то еще", "Что-то еще", "Что-то еще", "Что-то еще", "Что-то еще" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);

        selection = (TextView) findViewById(R.id.textView1);
        choiceList = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, things);
        // choiceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        choiceList.setAdapter(adapter);
    }
}
