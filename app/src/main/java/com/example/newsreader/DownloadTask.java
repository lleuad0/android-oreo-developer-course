package com.example.newsreader;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownloadTask extends AsyncTask {
    private String urlString;

    @Override
    protected Object doInBackground(Object[] objects) {
        String result = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();

            while (data != -1) {
                char currentSymbol = (char) data;
                result += currentSymbol;
                data = inputStreamReader.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    DownloadTask(String url) {
        urlString = url;
        this.execute();
    }
}
