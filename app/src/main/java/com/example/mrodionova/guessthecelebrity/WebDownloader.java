package com.example.mrodionova.guessthecelebrity;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class WebDownloader extends AsyncTask<String, Void, String> {

    URL url;
    HttpURLConnection httpURLConnection;
    InputStream inputStream;
    InputStreamReader inputStreamReader;
    String content = "";

    @Override
    protected String doInBackground(String... strings) {
        int current = 0;
        try {
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            while (current != -1) {
                current = inputStreamReader.read();
                content += (char) current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}
