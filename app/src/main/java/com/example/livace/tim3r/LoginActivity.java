package com.example.livace.tim3r;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText mLogin;
    AutoCompleteTextView mCity;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogin = (EditText) findViewById(R.id.login);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, Cities.getCitiesNames());

        mCity = (AutoCompleteTextView) findViewById(R.id.city);
        mCity.setThreshold(0);

        mCity.setAdapter(adapter);

        mButton = (Button) findViewById(R.id.btn_ok);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.setName(mLogin.getText().toString());
                User.setCitySlug(Cities.getSlugByName(mCity.getText().toString()));

                Intent intent = new Intent(LoginActivity.this, Interests.class);
                intent.setFlags(intent.getFlags());
                startActivity(intent);
            }
        });
    }
}
