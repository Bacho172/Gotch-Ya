package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class GuardCamera {
    Camera camera;
    Context context;
    Camera.Parameters parameters;
    SurfaceHolder sHolder;

GuardCamera(){

}
    void takePHoto(Context context) {
        camera = null;
        this.context = context;
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK); // attempt to get a Camera  back instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        SurfaceView sv = new SurfaceView(this.context);
        try {
            camera.setPreviewDisplay(sv.getHolder());
            parameters = camera.getParameters();
            camera.setParameters(parameters);
            camera.startPreview();

            camera.takePicture(null, null, mCall);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sHolder = sv.getHolder();
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
        Camera.PictureCallback mCall=new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //to do
            }
        }

}
