package com.example.adars.gotchya.DataModel.DataModel;

import com.example.adars.gotchya.DataModel.DomainModel.User;
import com.example.adars.gotchya.DataModel.Repository.UserRepository;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public final class UserModel {

    private User currentUser;

    private static UserModel instance;
    public static UserModel getInstance(){
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    public User logIn(String login, String password) {
        User incommingUser = UserRepository.getInstance().getOneByLoginAndPassword(login, password);
        if (incommingUser == null) return null;
        currentUser = incommingUser;
        return incommingUser;
    }

    public User logOut() {
        User user = currentUser;
        currentUser = null;
        return user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
