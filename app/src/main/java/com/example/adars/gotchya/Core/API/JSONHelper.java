package com.example.adars.gotchya.Core.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public abstract class JSONHelper {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int character;
        /* Odczytywanie znak po znaku do wyczerpania danych w czytniku */
        while ((character = rd.read()) != -1) {
            stringBuilder.append((char) character);
        }
        return stringBuilder.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        /* Otwarcie strumienia wejściowego */
        InputStream inputStream = new URL(url).openStream();
        try {
            /* Utworzenie czytnika znaków na podstawie strumienia wejściowego */
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(reader); // odczytanie całego ciągu znaków
            return new JSONObject(jsonText); // zwracanie obiektu typu JSON na podstawie tekstu
        } finally {
            inputStream.close();
        }
    }

    public static JSONArray readJsonFromUrlToArray(String url) throws IOException, JSONException {
        /* Otwarcie strumienia wejściowego */
        InputStream inputStream = new URL(url).openStream();
        try {
            /* Utworzenie czytnika znaków na podstawie strumienia wejściowego */
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(reader); // odczytanie całego ciągu znaków
            return new JSONArray(jsonText); // zwracanie obiektu typu JSON na podstawie tekstu
        } finally {
            inputStream.close();
        }
    }
}
