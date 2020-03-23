package com.example.mrodionova.braintrainer;


import java.util.Random;

class MathProblem {
    private Random random = new Random();
    private int numberOne = random.nextInt(31);
    private int sign = random.nextInt(2);
    private int numberTwo = random.nextInt(31);

    private int rightAnswer = numberOne;
    private int potentialAnswer;

    String message = String.valueOf(numberOne);

    MathProblem() {
        switch (sign) {
            case 0:
                message += " - ";
                rightAnswer -= numberTwo;
                break;
            case 1:
                message += " + ";
                rightAnswer += numberTwo;
                break;
        }
        message += numberTwo + " =";
    }

    String getRightAnswer() {
        return String.valueOf(rightAnswer);
    }

    String getPotentialAnswer() {
        potentialAnswer = random.nextInt(100);
        if (potentialAnswer == rightAnswer) {
            getPotentialAnswer();
        }
        return String.valueOf(potentialAnswer);

    }


}
