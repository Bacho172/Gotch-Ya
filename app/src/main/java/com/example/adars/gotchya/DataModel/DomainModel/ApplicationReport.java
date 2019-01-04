package com.example.adars.gotchya.DataModel.DomainModel;

/**
 * Created by Adam Bachorz on 12.12.2018.
 */
public class ApplicationReport extends Entity {

    private String frontCameraImage;
    private String coordinates;
    private String speed;
    private String nearestObject;
    private String deviceIP;
    private Device device;

    public ApplicationReport() {
    }

    public String getFrontCameraImage() {
        return frontCameraImage;
    }

    public void setFrontCameraImage(String frontCameraImage) {
        this.frontCameraImage = frontCameraImage;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getNearestObject() {
        return nearestObject;
    }

    public void setNearestObject(String nearestObject) {
        this.nearestObject = nearestObject;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
