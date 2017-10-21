package com.dominik.backend.util;

import java.util.Random;

public class Password {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;
    private static final String DICTIONARY[] = {"a","b","c","d","e","f","g","h","j","k","m","n","p","q",
            "r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","J","K","M","N","P","Q",
            "R","S","T","U","V","W","X","Y","Z","2","3","4","5","6","7","8","9","+","@"};

    public static String generatePassword() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        int passwordLength = random.nextInt(MAX_LENGTH + 1 - MIN_LENGTH) + MIN_LENGTH;
        int index = 0;

        for (int i = 0; i < passwordLength; i++) {
            index = (int) (random.nextDouble() * DICTIONARY.length);
            stringBuilder.append(DICTIONARY[index]);
        }

        return stringBuilder.toString();
    }
}
