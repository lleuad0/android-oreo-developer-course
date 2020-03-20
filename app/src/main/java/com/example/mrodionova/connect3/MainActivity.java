package com.example.mrodionova.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //0:yellow, 1:red
    int currentPlayerFlag = 1;
    HashMap<Double, Integer> gameField = new HashMap<>();

    public int getCurrentPlayer() {
        if (currentPlayerFlag == 0) {
            currentPlayerFlag++;
            return R.drawable.yellow;
        } else {
            currentPlayerFlag--;
            return R.drawable.red;
        }
    }


    public void putCounter(View view) {
        ImageView section = (ImageView) view;
        Double currentCounter = Double.parseDouble(view.getTag().toString());
        int currentValue = getCurrentPlayer();

        if (section.getDrawable() == null && !isWon()) {
            section.setTranslationY(-500);
            section.setImageResource(currentValue);
            section.animate().translationYBy(500).setDuration(500);
            gameField.put(currentCounter, currentValue);
            if (isWon()) {
                TextView winner = findViewById(R.id.winnerText);
                String message = "";
                if (currentValue == R.drawable.yellow) {
                    message += "Yellow";
                } else {
                    message += "Red";
                }
                message += " player wins!";

                winner.setText(message);
                winner.setVisibility(View.VISIBLE);
                findViewById(R.id.playAgainButton).setVisibility(View.VISIBLE);

            }

        }
    }

    public boolean isWon() {
        //rows
        if (gameField.get(0.0) != null && gameField.get(0.0).equals(gameField.get(0.1)) && gameField.get(0.0).equals(gameField.get(0.2))) {
            return true;
        }
        if (gameField.get(1.0) != null && gameField.get(1.0).equals(gameField.get(1.1)) && gameField.get(1.0).equals(gameField.get(1.2))) {
            return true;
        }
        if (gameField.get(2.0) != null && gameField.get(2.0).equals(gameField.get(2.1)) && gameField.get(2.0).equals(gameField.get(2.2))) {
            return true;
        }
        //columns
        if (gameField.get(0.0) != null && gameField.get(0.0).equals(gameField.get(1.0)) && gameField.get(0.0).equals(gameField.get(2.0))) {
            return true;
        }
        if (gameField.get(0.1) != null && gameField.get(0.1).equals(gameField.get(1.1)) && gameField.get(0.1).equals(gameField.get(2.1))) {
            return true;
        }
        if (gameField.get(0.2) != null && gameField.get(0.2).equals(gameField.get(1.2)) && gameField.get(0.2).equals(gameField.get(2.2))) {
            return true;
        }
        //diagonals
        if (gameField.get(0.0) != null && gameField.get(0.0).equals(gameField.get(1.1)) && gameField.get(0.0).equals(gameField.get(2.2))) {
            return true;
        }
        if (gameField.get(0.2) != null && gameField.get(0.2).equals(gameField.get(1.1)) && gameField.get(0.2).equals(gameField.get(2.0))) {
            return true;
        }
        return false;
    }

    public void resetGame(View view) {
        findViewById(R.id.winnerText).setVisibility(View.INVISIBLE);
        findViewById(R.id.playAgainButton).setVisibility(View.INVISIBLE);
        gameField.clear();
        LinearLayout table = (LinearLayout) findViewById(R.id.linearLayout);
        for (int i = 0; i < table.getChildCount(); i++) {
            LinearLayout row = (LinearLayout) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                ImageView image = (ImageView) row.getChildAt(j);
                image.setImageDrawable(null);
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
