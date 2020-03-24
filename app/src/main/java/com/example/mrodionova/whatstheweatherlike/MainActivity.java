package com.example.mrodionova.whatstheweatherlike;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    String inputText;
    String downloaded;
    JSONObject jsonObject;
    String url;
    TextView forecastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastText = findViewById(R.id.forecastText);

    }

    public void getInputText(View view) {
        TextView textView = findViewById(R.id.inputText);
        inputText = textView.getText().toString();
        url = "https://openweathermap.org/data/2.5/weather?q=" + inputText + "&appid=b6907d289e10d714a6e88b30761fae22";
        try {
            downloaded = new JsonDownloader().execute(url).get();
            jsonObject = new JSONObject(downloaded);
            setWeather(jsonObject);
        } catch (Exception e) {
            forecastText.setText(null);
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
        }


        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setWeather(JSONObject jsonObject) {

        try {
            String result = "";
            String weather = jsonObject.getString("weather");
            JSONArray weatherArray = new JSONArray(weather);
            for (int i = 0; i < weatherArray.length(); i++) {
                JSONObject weatherPart = weatherArray.getJSONObject(i);
                String add = weatherPart.getString("main") + ": " + weatherPart.getString("description") + "\n";
                result += add;
            }
            forecastText.setText(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
