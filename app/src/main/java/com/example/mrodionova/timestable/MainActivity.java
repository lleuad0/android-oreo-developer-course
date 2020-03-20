package com.example.mrodionova.timestable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    ListView timesTable;
    ArrayAdapter arrayAdapter;
    int timesNumber;
    ArrayList<Integer> timesContent;

    public void refresh(int number, ArrayList arrayList) {
        for (int i = 0; i < 10; ) {
            arrayList.add(++i * number);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timesContent = new ArrayList<>();
        timesNumber = 1;
        refresh(timesNumber, timesContent);

        seekBar = (SeekBar) findViewById(R.id.numberSeekBar);
        seekBar.setMin(1);
        seekBar.setMax(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timesNumber = progress;
                timesContent.clear();
                refresh(timesNumber, timesContent);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        timesTable = findViewById(R.id.timesTable);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_selectable_list_item, timesContent);
        timesTable.setAdapter(arrayAdapter);
    }

}
