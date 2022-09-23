package com.alwaysbedream.survbackend.utils;

import java.util.Random;

public class Slug {
    // generate random string from 0 - z for slug
    public static String generateSlug(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        // start generate the string and stored into generatedString variable
        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        // return the generated String.
        return generatedString;
    }
}
