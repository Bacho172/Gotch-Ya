package com.example.adars.gotchya.DataModel.DomainModel;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public class User extends Entity {
    private String login;
    private String password;

    public static User currentUser;

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
}
