package com.example.adars.gotchya.Core.API;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Adam Bachorz on 03.01.2019.
 */
public class UploadToImgurTask extends AsyncTask<String, Void, Boolean> {

    private Activity activity;
    private String uploadedImageUrl = "";
    private String accessToken = "";

    public UploadToImgurTask(Activity activity, String accessToken) {
        this.activity = activity;
        this.accessToken = accessToken;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        final String upload_to = "https://api.imgur.com/3/upload";

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(upload_to);

        try {
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("image", new FileBody(new File(params[0])))
                    .build();

            httpPost.setHeader("Authorization", "Bearer " + accessToken);
            httpPost.setEntity(entity);

            final HttpResponse response = httpClient.execute(httpPost,
                    localContext);

            final String response_string = EntityUtils.toString(response
                    .getEntity());

            final JSONObject json = new JSONObject(response_string);
            System.out.println("json string " + json.toString());

            JSONObject data = json.optJSONObject("data");
            uploadedImageUrl = data.optString("link");
            System.out.println("uploaded image url : " + uploadedImageUrl);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean.booleanValue()) {
            Intent intent = new Intent();
            intent.setData(Uri.parse(uploadedImageUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
//        if (aBoolean.booleanValue()) { // after sucessful uploading, show the image in web browser
//            Button openBrowser = new Button(OAuthTestActivity.this);
//            rootView.addView(openBrowser);
//            openBrowser.setText("Open Browser");
//            openBrowser.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
    }

    public String getUploadedImageUrl() {
        return uploadedImageUrl;
    }
}
