package com.example.mrodionova.guessthecelebrity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    WebDownloader webDownloader;
    ArrayList<Celebrity> celebrities;
    Randomizer randomizer;
    ArrayList<Button> buttons;
    ImageView imageView;

    public void checkAnswer(View view) {
        String message;
        if (view.getTag() == Integer.valueOf(1)) {
            message = "Correct!";
        } else {
            message = "Wrong :(";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        new Assigner(buttons, randomizer, imageView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webDownloader = new WebDownloader();
        String pageurl = "http://www.posh24.se/kandisar";
        webDownloader.execute(pageurl);

        buttons = new ArrayList<>();
        buttons.add((Button) findViewById(R.id.button0));
        buttons.add((Button) findViewById(R.id.button1));
        buttons.add((Button) findViewById(R.id.button2));
        buttons.add((Button) findViewById(R.id.button3));
        imageView = findViewById(R.id.imageView);

        celebrities = new ArrayList<>();
        String regex = "\"channelListEntry\"(?>\\n|.)*?img src=\"(.*?)\" alt=\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = null;
        try {
            matcher = pattern.matcher(webDownloader.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (matcher.find()) {
            Celebrity celebrity = new Celebrity(matcher.group(1), matcher.group(2));
            celebrities.add(celebrity);
        }

        randomizer = new Randomizer(celebrities);
        new Assigner(buttons, randomizer, imageView);

    }
}
