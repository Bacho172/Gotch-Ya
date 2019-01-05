package com.example.adars.gotchya.Core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.example.adars.gotchya.DataModel.DomainModel.User;
import com.example.adars.gotchya.R;

import org.apache.http.NameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public final class Functions {

    public static void setFont(Context context, TextView textView, String font) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), font));
    }

    public static void saveUserData(Context context, User user) {
        try {
            String fileName = context.getString(R.string.user_config_file_name);
            FileOutputStream fileout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            String data = getDataVector(false, user.getID(), user.getLogin(), user.getPassword());
            outputWriter.write(data);
            outputWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "Nieudany zapis do pliku: " + e.toString());
        }
    }

    public static void clearUserDataConfig(Context context) {
        try {
            String fileName = context.getString(R.string.user_config_file_name);
            FileOutputStream fileout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write("");
            outputWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "Nieudane czyszczenie pliku: " + e.toString());
        }
    }

    public static User loadUserData(Context context) {
        String fileName = context.getString(R.string.user_config_file_name);

        try {
            FileInputStream fileIn = context.openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            String result = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                result += readstring;
            }
            InputRead.close();
            return !result.trim().isEmpty() ? splitDataVector(context, result) : null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User splitDataVector(Context context, String input) {
        String[] data = input.split(context.getString(R.string.data_file_separator));
        User user = new User();
        user.setID(Integer.parseInt(data[0]));
        user.setLogin(data[1]);
        user.setPassword(data[2]);
        return user;
    }

    public static String getDataVector(boolean toURL, Object... objects){
        String result = "";
        for (int i = 0; i < objects.length; i++) {
            result += (toURL ? "/" : "") + (i == objects.length - 1 ? objects[i] : objects[i] + (toURL ? "" : ";")) ;
        }
        return result;
    }

//    public static Drawable imageFromURL(String url, int userID) {
//        try {
//            InputStream inputStream = (InputStream) new URL(url).getContent();
//            return Drawable.createFromStream(inputStream, "userImage" + userID);
//        } catch (Exception e) {
//            System.err.println("Nie można wczytać obrazka: \nURL: " + url + "\nUserID: " + userID + "\nPowód: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String getQuery(List<NameValuePair> parameters) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : parameters)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public static Bitmap bitmapFromURI(Activity activity, Uri uri) throws IOException{
        InputStream input = activity.getApplicationContext().getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth)
                ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = originalSize;
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = activity.getApplicationContext().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
        return bos.toByteArray();
    }

    /**
     * Zwraca komunikat błędu wraz z klasa i metodą, w której wystąpił
     *
     * @param c klasa źródłowa
     * @param methodName nazwa metody
     * @param ex wyjątek
     *
     * @return Komunikat błędu wraz z klasa i metodą, w której wystąpił
     *
     * @author Adam Bachorz
     */
    public static String getExecutionError(Class c, String methodName, Exception ex){
        return "\nBŁĄD WYKONYWANIA FUNKCJI !!! \n" +
                "Klasa: " + c.getSimpleName().toUpperCase() + "\n" +
                "Funkcja: " + methodName + "\n" +
                "Komunikat błędu: " + ex.getMessage() + "\n\n";
    }
}

