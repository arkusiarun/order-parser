package com.parser.orderparser.util;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class CommonUtils {

    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static String getJson(Object obj) {
        return gson.toJson(obj);
    }

    public static FileReader readSourceFile(String fileName) throws FileNotFoundException {
        return new FileReader(fileName);
    }
}