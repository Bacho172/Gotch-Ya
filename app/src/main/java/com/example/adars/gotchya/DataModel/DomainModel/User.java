package com.example.adars.gotchya.DataModel.DomainModel;

/**
 * Created by Adam Bachorz on 06.11.2018.
 */
public class User extends Entity {
    private String login;
    private String password;

    public User(){}
    public User(Integer ID, String login, String password) {
        super(ID);
        this.login = login;
        this.password = password;
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
}
