package com.priyanshnama.technical_fest.utils;

import androidx.annotation.NonNull;

public class StringFormatter {

    public static String format(@NonNull String data) {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<data.length();i++){
            if( i!=data.length()-1 && data.charAt(i)=='\\' && data.charAt((i+1))=='n'){
                result.append('\n');
                i++;continue;
            }
            result.append(data.charAt(i));
        }
        return result.toString();
    }

}
