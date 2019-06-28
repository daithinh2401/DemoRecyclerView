package com.example.demorecyclerview;

public class AppUtils {

    // This will make 2 lines if keyword more than one
    public static String replaceString(String origin) {
        int length = origin.length();
        char[] org = origin.toCharArray();
        int index = -1;
        int absoluteValue = 0;

        for (int i = 0; i < length; i++) {
            if (org[i] == ' ') {
                int rightPart = getAbsoluteValue(length - 1, i);

                if (absoluteValue == 0) {
                    absoluteValue = getAbsoluteValue(rightPart, i);
                    index = i;
                } else {
                    int newAbsoluteVal = getAbsoluteValue(rightPart, i);

                    if (newAbsoluteVal <= absoluteValue) {
                        absoluteValue = newAbsoluteVal;
                        index = i;
                    }
                }
            }
        }

        if (index == -1) {
            return origin;
        }

        org[index] = '\n';

        return String.valueOf(org);
    }


    public static int getAbsoluteValue(int a, int b) {
        if (a > b)
            return a - b;
        return b - a;

    }
}