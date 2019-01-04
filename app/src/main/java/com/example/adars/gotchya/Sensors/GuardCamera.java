package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class GuardCamera {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    public static final int BACK_CAMERA = 0;
    public static final int SELFIE_CAMERA = 1;

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private Context context;
    private Activity activity;
    private String photoPath;
    private int cameraType;
    private SurfaceHolder sHolder;
    private Bitmap lastPhoto;
    private byte[] photoBytes;
    private File file;
    private Handler mBackgroundHandler;
    private int counter = 0;
    private String[] cameraId = null;
    private CameraDevice.StateCallback stateCallback;


    public GuardCamera(Activity activity, Looper looper, int cameraType) throws CameraAccessException {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        this.cameraType = cameraType;
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
            return;
        }

        stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                cameraDevice = camera;
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
                if (cameraDevice != null) cameraDevice.close();
            }

            @Override
            public void onError(CameraDevice camera, int error) {
                if (cameraDevice != null) cameraDevice.close();
            }
        };

        try {
            cameraId = cameraManager.getCameraIdList();
            int a = 6;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        Handler handler = null;

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) { }
        cameraManager.openCamera(cameraId[cameraType == BACK_CAMERA ? 0 : 1], stateCallback, handler);
    }

    public void takePhoto() {

        String currentPath = null;
        if (cameraDevice == null)
            return;

        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraDevice.getId());
            Size[] jpegSizes = null;
            if (characteristics != null)
                jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG);

            //Capture image with custom size
            int width = 640;
            int height = 480;
            if (jpegSizes != null && jpegSizes.length > 0) {
                width = jpegSizes[0].getWidth();
                height = jpegSizes[0].getHeight();
            }
            final ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            List<Surface> outputSurface = new ArrayList<>(2);
            outputSurface.add(reader.getSurface());
            //  outputSurface.add(new Surface(textureView.getSurfaceTexture()));

            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(reader.getSurface());
            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            //Check orientation base on device
            int rotation = this.activity.getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));

            String appDirectory = "/gotchya";
            counter++;
            String title = cameraType == BACK_CAMERA ? "back_camera" : "selfie_camera";
            currentPath = Environment.getExternalStorageDirectory() + "/" + title + counter + ".jpg";
            file = new File(currentPath);

            ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader imageReader) {
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        byte[] bytes = new byte[buffer.capacity()];
                        photoBytes = bytes;
                        buffer.get(bytes);
                        save(bytes);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        {
                            if (image != null)
                                image.close();
                        }
                    }
                }

                private void save(byte[] bytes) throws IOException {
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        outputStream.write(bytes);
                    } finally {
                        if (outputStream != null)
                            outputStream.close();
                    }
                }
            };

            reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);
            final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                }
            };

            cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    try {
                        cameraCaptureSession.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                }
            }, new Handler());


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        photoPath = currentPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public byte[] getPhotoBytes() {
        return photoBytes;
    }
}
/*    public void takePhoto(Context context) {

        SurfaceView sv = new SurfaceView(this.context);
        try {
            camera1.setPreviewDisplay(sv.getHolder());
            parameters = camera1.getParameters();
            camera1.setParameters(parameters);
            camera1.startPreview();

            camera1.takePicture(null, null, mCall);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sHolder = sv.getHolder();
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    Camera.PictureCallback mCall = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
            lastPhoto = BitmapFactory.decodeStream(arrayInputStream);
        }
    };

}*/
