package com.example.englishorspanish;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String language = (String) item.getTitle();
        setLanguage(language);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applyLanguage();
    }

    public void applyLanguage() {
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String language = sharedPreferences.getString("language", null);

        if (language == null) {
            makeAlert();
        } else {
            TextView textView = findViewById(R.id.textView);
            textView.setText("Language selected: " + language);
        }
    }

    public void setLanguage(String language) {
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        sharedPreferences.edit().putString("language", language).apply();
        applyLanguage();
    }

    public void makeAlert() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Language")
                .setMessage("Please choose your language")
                .setPositiveButton("English", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLanguage("English");
                        applyLanguage();
                    }
                })
                .setNegativeButton("Spanish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setLanguage("Spanish");
                        applyLanguage();
                    }
                })
                .setCancelable(false)
                .show();

    }
}
