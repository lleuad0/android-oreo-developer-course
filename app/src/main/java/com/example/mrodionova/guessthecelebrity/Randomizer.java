package com.example.mrodionova.guessthecelebrity;

import java.util.ArrayList;
import java.util.Random;

class Randomizer {
    private Random random = new Random();
    private ArrayList<Celebrity> celebrities;
    private String pickedUrl;
    private String pickedName;

    Randomizer(ArrayList<Celebrity> input) {
        celebrities = input;
    }

    void pickOne() {
        int picked = random.nextInt(celebrities.size());
        pickedUrl = celebrities.get(picked).url;
        pickedName = celebrities.get(picked).name;
    }

    String getPickedName() {
        return pickedName;
    }

    String getPickedUrl() {
        return pickedUrl;
    }

}
