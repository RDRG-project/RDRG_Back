package com.rdrg.back.common.util;

import java.util.Random;

public class EmailAuthNumberUtil {

    public static String createNumber() {
        String authNumber = "";
        Random random = new Random();

        for (int index = 0; index < 4; index++) authNumber += random.nextInt(10);
        return authNumber;
    }

    public static String createCode() {
        char[] authChar = new char[4];
        Random random = new Random();

        for (int index = 0; index < authChar.length; index++) 
        authChar[index] = (char)(random.nextInt(26) + 65);

        return new String(authChar);
    }

    public static String createCodeNumber() {
        char[] authChar = new char[4];
        Random random = new Random();

        for (int index = 0; index < authChar.length; index++) {
            boolean flag = random.nextBoolean();
            if (flag) {authChar[index] = (char)(random.nextInt(10) + 48);
            } else {
                authChar[index] = (char)(random.nextInt(26) + 65);
            }
        }
        return new String(authChar);
    }
}
