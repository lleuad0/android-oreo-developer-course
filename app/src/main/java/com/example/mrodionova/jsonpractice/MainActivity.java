package com.example.mrodionova.jsonpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton button0;
    RadioButton button1;

    TextView inputName;

    String nameId;
    String groupId;

    JSONArray employees;
    JSONArray managers;

    JSONObject complete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        button0 = findViewById(R.id.radioButton);
        button1 = findViewById(R.id.radioButton2);

        inputName = findViewById(R.id.inputName);

        employees = new JSONArray();
        managers = new JSONArray();

        complete = new JSONObject();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == button0.getId()) {
                    groupId = button0.getText().toString();
                } else {
                    groupId = button1.getText().toString();
                }
            }
        });

    }

    public void submitClick(View view) {
        nameId = inputName.getText().toString();
        JSONObject workerName = new JSONObject();
        if (nameId.isEmpty() || groupId == null) {
            Toast.makeText(getApplicationContext(), "Please fill all the info", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String firstName = nameId.split(" ")[0];
                String lastName = nameId.split(" ")[1];
                workerName.put("firstName", firstName);
                workerName.put("lastName", lastName);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            if (groupId == button0.getText().toString()) {
                employees.put(workerName);
            } else {
                managers.put(workerName);
            }
        }
    }

    public void showAll(View view) {
        try {
            complete.put("employees", employees);
            complete.put("managers", managers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: save JSON to a file + prettify

        Toast.makeText(getApplicationContext(), complete.toString(), Toast.LENGTH_SHORT).show();
    }
}
