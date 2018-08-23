package com.android.kaushiktiwari.bmi_calculator;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListView lvdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);
        lvdata = (ListView) findViewById(R.id.lvdata);
        String data = MainActivity.db.viewperson();
        ArrayList<String> info = new ArrayList<>();
        if (data.length() == 0) {

            info.add("No Records To Show");

        } else {
            info.add(data);


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
        lvdata.setAdapter(adapter);
    }
}
