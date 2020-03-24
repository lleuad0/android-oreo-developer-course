package com.example.mrodionova.guessthecelebrity;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

class Assigner {
    private Random r;
    private ArrayList<Button> buttons;
    private Randomizer randomizer;
    private ImageView imageView;
    ImageDownloader imageDownloader;
    private ArrayList<String> picked;

    Assigner(ArrayList<Button> inputButtons, Randomizer inputCelebs, ImageView inputImage) {
        buttons = inputButtons;
        randomizer = inputCelebs;
        imageView = inputImage;
        for (Button b : buttons) {
            b.setTag(0);
        }
        picked = new ArrayList<>();
        r = new Random();
        setCorrect();
        setIncorrect();

    }

    private void setCorrect() {
        randomizer.pickOne();
        int index = r.nextInt(buttons.size());
        Button correct = buttons.get(index);
        correct.setText(randomizer.getPickedName());
        correct.setTag(1);
        imageDownloader = new ImageDownloader();
        Bitmap bmp;
        try {
            bmp = imageDownloader.execute(randomizer.getPickedUrl()).get();
            imageView.setImageBitmap(bmp);
            picked.add(randomizer.getPickedName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setIncorrect() {
        for (Button b : buttons) {
            randomizer.pickOne();
            if (!picked.contains(randomizer.getPickedName())) {
                if (b.getTag() != Integer.valueOf(1)) {
                    b.setText(randomizer.getPickedName());
                    picked.add(randomizer.getPickedName());
                }
            }
        }
    }
}
