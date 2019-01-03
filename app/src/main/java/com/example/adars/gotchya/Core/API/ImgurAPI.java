package com.example.adars.gotchya.Core.API;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam Bachorz on 03.01.2019.
 */
public class ImgurAPI {

    private Activity activity;
    private UploadToImgurTask uploadTask;
    private String accessToken;
    private String refreshToken;
    private String picturePath = "";
    private String uploadedImageUrl = "";

    public static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private static final String AUTHORIZATION_URL = "https://api.imgur.com/oauth2/authorize";
    private static final String CLIENT_ID = "CLIENT_ID";

    public ImgurAPI(Activity activity, String picturePath) {
        this.activity = activity;
        this.picturePath = picturePath;
        init();
    }

    public void init() {
        String action = activity.getIntent().getAction();

        if (action == null || !action.equals(Intent.ACTION_VIEW)) { // We need access token to use Imgur's api

            Uri uri = Uri.parse(AUTHORIZATION_URL).buildUpon()
                    .appendQueryParameter("client_id", CLIENT_ID)
                    .appendQueryParameter("response_type", "token")
                    .appendQueryParameter("state", "init")
                    .build();

            Intent intent = new Intent();
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();

        } else { // Now we have the token, can do the upload

            Uri uri = activity.getIntent().getData();
            System.out.println("Got imgur's access token " + uri.toString());
            String uriString = uri.toString();
            String paramsString = "http://callback?" + uriString.substring(uriString.indexOf("#") + 1);
            System.out.println("paramsString " + paramsString);

            List<NameValuePair> params = URLEncodedUtils.parse(URI.create(paramsString), "utf-8");
            System.out.println("Parametry URI " + Arrays.toString(params.toArray(new NameValuePair[0])));

            for (NameValuePair pair : params) {
                if (pair.getName().equals("access_token")) {
                    accessToken = pair.getValue();
                } else if (pair.getName().equals("refresh_token")) {
                    refreshToken = pair.getValue();
                }
            }

            uploadTask = new UploadToImgurTask(activity, accessToken);

            System.out.println("access_token = " + accessToken);
            System.out.println("refresh_token = " + refreshToken);

            //Wybór zdj
//            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    }

    public void uploadImage() {
        //Wysyłanie zdjęcia
        if (pictureReadyToUpload()) {
            uploadTask.execute(picturePath);
        } else {
            String errorMessage = "Nie można wysłać zdjęcia";
            errorMessage += picturePath == null ? "\n Brak ścieżki do zdjęcia !" : "";
            errorMessage += accessToken == null ? "\n Brak Tokenu dostępu !" : "";
            System.err.println(errorMessage);
        }
    }

    public boolean pictureReadyToUpload() {
        return picturePath != null && picturePath.length() > 0
                && accessToken != null && accessToken.length() > 0;
    }

    public String getPicturePath() {
        return picturePath;
    }
}
