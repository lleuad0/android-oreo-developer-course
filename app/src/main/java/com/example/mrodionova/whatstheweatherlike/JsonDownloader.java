package com.example.mrodionova.whatstheweatherlike;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class JsonDownloader extends AsyncTask<String, Void, String> {
    URL address;
    HttpURLConnection connection;
    InputStream stream;
    InputStreamReader reader;
    String result = "";

    @Override
    protected String doInBackground(String... strings) {

        try {
            address = new URL(strings[0]);
            connection = (HttpURLConnection) address.openConnection();
            stream = connection.getInputStream();
            reader = new InputStreamReader(stream);
            int currentIndex = reader.read();
            while (currentIndex != -1) {
                result += (char) currentIndex;
                currentIndex = reader.read();
            }

        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;

    }

}
