package com.example.adars.gotchya.DataModel.Repository;

import android.os.NetworkOnMainThreadException;

import com.example.adars.gotchya.Core.API.WebServiceAccess;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Adam Bachorz on 12.12.2018.
 */
public final class ApplicationReportRepository implements IRepository<ApplicationReport> {

    private static ApplicationReportRepository instance;
    public static ApplicationReportRepository getInstance() {
        if (instance == null) {
            instance = new ApplicationReportRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<ApplicationReport> getAll() {
        return null;
    }

    public static ApplicationReport example() {
        ApplicationReport report = new ApplicationReport();
        report.setCreatedAt(new Date());
        report.setUpdatedAt(new Date());
        report.setDeviceIP("192.168.1.1");
        report.setSpeed("999999");
        report.setNearestObject("Dom Mietka żula");
        report.setCoordinates("111:111:111");
        report.setFrontCameraImage("https://eatliver.b-cdn.net/wp-content/uploads/2016/03/face1.jpg");
        report.setBackCameraImage("https://www.rako.eu/common/images/realizations/152/gallery/3822.jpg");
        Device device = new Device();
        device.setID(111);
        device.setMacAddress("7e:6e:09:b2:77:f8");
        report.setDevice(device);
        return report;
    }

    public String postImageToServer(String imageURL) throws IOException {
        final String[] sResponse = {null};
        ThreadHelper.runAsync(() -> {
            try {
                WebServiceAccess access = new WebServiceAccess("test");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(access.getURL());
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
    //        try {
    //            reqEntity.addPart("name", new StringBody("Name"));
    //            reqEntity.addPart("Id", new StringBody("ID"));
    //            reqEntity.addPart("title",new StringBody("TITLE"));
    //            reqEntity.addPart("caption", new StringBody("Caption"));
    //
    //        } catch (UnsupportedEncodingException e) {
    //            e.printStackTrace();
    //        }
                try{
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                    byte[] data = bos.toByteArray();
                    ByteArrayBody bab = new ByteArrayBody(data, imageURL);
                    reqEntity.addPart("image", bab);
                }
                catch(Exception e){
                    //Log.v("Exception in Image", ""+e);
                    try {
                        reqEntity.addPart("picture", new StringBody(""));
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
                postRequest.setEntity(reqEntity);
                HttpResponse response = null;
                try {
                    response = httpClient.execute(postRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder s = new StringBuilder();
                while ((sResponse[0] = reader.readLine()) != null) {
                    s = s.append(sResponse[0]);
                }
                System.out.println("URL FROM SERVER........." + sResponse[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            } catch (NetworkOnMainThreadException e) {
                e.printStackTrace();
            }
        });
        return sResponse[0];
    }

    public String postImageToServerV2(String imageURL) throws IOException {
        String response = "";
        //ThreadHelper.runAsync(() ->{
            try {
                WebServiceAccess access = new WebServiceAccess("test");
                URL url = new URL(access.getURL());
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("idApplicationReport", entity.getID().toString()));
                //parameters.add(new BasicNameValuePair("idDevice", entity.getDevice().getID().toString()));
                parameters.add(new BasicNameValuePair("image", imageURL));


                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String queryURL = Functions.getQuery(parameters);
                writer.write(queryURL);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect();

                int responseCode = connection.getResponseCode();


                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                }
                else {
                    response = responseCode + "";
                }
                System.out.println("RESPONSE: " + response);
                return response;
            }
            catch (UnsupportedEncodingException ex) {
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                System.err.println(Functions.getExecutionError(getClass(), methodName, ex));
                ex.printStackTrace();
            }
            catch (IOException ex) {
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                System.err.println(Functions.getExecutionError(getClass(), methodName, ex));
                ex.printStackTrace();
            }
            catch (NetworkOnMainThreadException ex) {
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                System.err.println(Functions.getExecutionError(getClass(), methodName, ex));
                ex.printStackTrace();
            }
        //});
        return response;
    }

    @Override
    public void insert(ApplicationReport entity) {
        ThreadHelper.runAsync(() ->{
            try {
                WebServiceAccess access = new WebServiceAccess("applicationreports");
                URL url = new URL(access.getURL());
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("idApplicationReport", entity.getID().toString()));
                //parameters.add(new BasicNameValuePair("idDevice", entity.getDevice().getID().toString()));
                parameters.add(new BasicNameValuePair("back_camera_image", entity.getBackCameraImage()));
                parameters.add(new BasicNameValuePair("front_camera_image", entity.getFrontCameraImage()));
                parameters.add(new BasicNameValuePair("mac_address", entity.getDevice().getMacAddress()));
                parameters.add(new BasicNameValuePair("coordinates", entity.getCoordinates()));
                parameters.add(new BasicNameValuePair("speed", entity.getSpeed()));
                //parameters.add(new BasicNameValuePair("DeviceIP", entity.getDeviceIP()));
                parameters.add(new BasicNameValuePair("nearest_object", entity.getNearestObject()));
                //parameters.add(new BasicNameValuePair("created_at", entity.getCreatedAt().toString()));
                //parameters.add(new BasicNameValuePair("updated_at", entity.getUpdatedAt().toString()));


                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String queryURL = Functions.getQuery(parameters);
                writer.write(queryURL);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect();

                int responseCode = connection.getResponseCode();
                String response = "";

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                }
                else {
                    response = responseCode + "";
                }
                System.out.println("RESPONSE: " + response);
            }
            catch (UnsupportedEncodingException ex) {
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                System.err.println(Functions.getExecutionError(getClass(), methodName, ex));
                ex.printStackTrace();
            }
            catch (IOException ex) {
                String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
                System.err.println(Functions.getExecutionError(getClass(), methodName, ex));
                ex.printStackTrace();
            }
        });

    }

    @Override
    public void update(ApplicationReport entity) {

    }

    @Override
    public void delete(ApplicationReport entity) {

    }

    @Override
    public String getTableName() {
        return "application_reports";
    }
}
