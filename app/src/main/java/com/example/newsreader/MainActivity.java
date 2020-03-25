package com.example.newsreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ListView listView;
    ArrayList<String> titles;
    ArrayList<String> urls;
    ArrayAdapter arrayAdapter;
    JSONArray bestStoriesJson;
    String serverUrl = "https://hacker-news.firebaseio.com/v0/";
    String bestStories = "topstories.json?print=pretty";
    String itemUrlStart = "item/";
    String itemUrlEnd = ".json?print=pretty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        titles = deserialise(sharedPreferences.getString("serTitles", null));
        urls = deserialise(sharedPreferences.getString("serUrls", null));

        if (titles == null && urls == null) {
            bestStoriesJson = getBestStories();
            titles = getStoriesParameter(bestStoriesJson, "title");
            urls = getStoriesParameter(bestStoriesJson, "url");
        }

        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ArticleContentActivity.class);
                intent.putExtra("url", urls.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        String serTitles = serialise(titles);
        String serUrls = serialise(urls);
        sharedPreferences.edit().putString("serTitles", serTitles).putString("serUrls", serUrls).apply();
        super.onPause();
    }

    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public String serialise(ArrayList arrayList) {
        String result = null;
        try {
            result = ObjectSerializer.serialize(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
            makeToast("Serialising exception occurred");
        }
        return result;
    }

    public ArrayList<String> deserialise(String content) {
        ArrayList<String> result = new ArrayList<>();
        try {
            result = (ArrayList) ObjectSerializer.deserialize(content);
        } catch (Exception e) {
            e.printStackTrace();
            makeToast("Deserialising exception occurred");
        }
        return result;
    }

    public JSONArray getBestStories() {
        JSONArray result = new JSONArray();
        try {
            String content = (String) new DownloadTask(serverUrl + this.bestStories).get();
            JSONArray all = new JSONArray(content);
            for (int i = 0; i < 10; i++) {
                result.put(all.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getStoriesParameter(JSONArray stories, String parameter) {
        JSONObject jsonObject;
        ArrayList<String> results = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                String query = serverUrl + itemUrlStart + stories.getString(i) + itemUrlEnd;
                String content = (String) new DownloadTask(query).get();
                jsonObject = new JSONObject(content);
                String extracted = jsonObject.getString(parameter);
                results.add(extracted);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }


}
