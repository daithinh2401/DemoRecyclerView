package com.example.demorecyclerview;

import android.util.Log;

import java.util.HashMap;

public class AppUtils {

    // This will make 2 lines if keyword more than one
    public static String replaceString(String origin) {

        char[] org = origin.toCharArray();
        int length = org.length;

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


    // Simple unit test to check above method is returned true value
    // Just use for testing only
    public static void unitTest(){
        String[] data = { "xiaomi", "bitis hunter", "bts", "balo", "bitis hunter x", "tai nghe",
                "harry potter", "anker", "iphone", "balo nữ", "nguyễn nhật ánh", "đắc nhân tâm", "ipad",
                "senka", "tai nghe bluetooth", "son", "maybelline", "laneige", "kem chống nắng",
                "anh chính là thanh xuân của em"
        };

        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("xiaomi", "xiaomi");
        resultMap.put("bitis hunter", "bitis\nhunter");
        resultMap.put("bts", "bts");
        resultMap.put("balo", "balo");
        resultMap.put("bitis hunter x", "bitis\nhunter x");
        resultMap.put("tai nghe", "tai\nnghe");
        resultMap.put("harry potter", "harry\npotter");
        resultMap.put("anker", "anker");
        resultMap.put("iphone", "iphone");
        resultMap.put("balo nữ", "balo\nnữ");
        resultMap.put("nguyễn nhật ánh", "nguyễn\nnhật ánh");
        resultMap.put("đắc nhân tâm", "đắc nhân\ntâm");
        resultMap.put("ipad", "ipad");
        resultMap.put("senka", "senka");
        resultMap.put("tai nghe bluetooth", "tai nghe\nbluetooth");
        resultMap.put("son", "son");
        resultMap.put("maybelline", "maybelline");
        resultMap.put("laneige", "laneige");
        resultMap.put("kem chống nắng", "kem chống\nnắng");
        resultMap.put("anh chính là thanh xuân của em", "anh chính là\nthanh xuân của em");

        for(String str: data){
            String expect = resultMap.get(str);
            String actual = replaceString(str);

            if(!expect.equals(actual)){
                Log.d("TAG" , "AppUtils.unitTest(): Failed in case - Expect: " + expect + " actual: " + actual);
                return;
            }
        }

        Log.d("TAG" , "AppUtils.unitTest(): Pass !");
    }
}