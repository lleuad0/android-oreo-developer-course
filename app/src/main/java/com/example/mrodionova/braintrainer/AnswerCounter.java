package com.example.mrodionova.braintrainer;

public class AnswerCounter {
    int right = 0;
    int total = 0;

    void givenRight() {
        right += 1;
        total += 1;
    }

    void givenWrong() {
        total += 1;
    }

    public String message() {
        return String.valueOf(right) + "/" + String.valueOf(total);
    }


}
