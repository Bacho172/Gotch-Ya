package com.example.adars.gotchya.DataModel.DomainModel;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public class User extends Entity {
    private String login;
    private String password;
    private String email;
    private Image image;
    private Privilege privilege;
    private Status status;
    private ArrayList<Device> devices;

    public User(){}

    public User(Integer ID, String login, String password, String email) {
        super(ID);
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }
}
