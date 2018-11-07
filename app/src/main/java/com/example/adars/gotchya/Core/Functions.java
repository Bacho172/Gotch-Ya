package com.example.adars.gotchya.Core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adars.gotchya.DataModel.DomainModel.User;
import com.example.adars.gotchya.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public final class Functions {

    public static void setFont(Context context, TextView textView, String font){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), font));
    }

    public static void saveUserData(Context context, User user) { //TODO: test
        try {
            String fileName = context.getString(R.string.user_config_file_name);
            String path = context.getString(R.string.user_config_path);

            FileOutputStream fileout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            String data = getDataVector(user.getID(), user.getLogin(), user.getPassword());
            outputWriter.write(data);
            outputWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "Nieudany zapis do pliku: " + e.toString());
        }
    }

    public static User loadUserData(Context context) { //TODO: Test
        String fileName = context.getString(R.string.user_config_file_name);

        try {
            FileInputStream fileIn = context.openFileInput(fileName);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            String result = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer,0,charRead);
                result += readstring;
            }
            InputRead.close();
            return splitDataVector(context, result);

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

    public static String getDataVector(Object... data) {
        String result = "";
        for (Object o : data) {
            result += o.toString() + (!o.equals(data[data.length-1]) ? ";" : "");
        }
        return result;
    }
}
