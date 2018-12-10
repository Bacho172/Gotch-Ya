package com.example.adars.gotchya.DataModel.Repository;

import com.example.adars.gotchya.DataModel.DomainModel.User;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public final class UserRepository implements IRepository<User> {

    private static UserRepository instance;
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    @Override
    public ArrayList<User> getAll() {
        return new ArrayList<>(Arrays.asList(
                new User(1, "user1", "pass1", "u1@gmail.com"),
                new User(2, "user2", "pass2", "u2@gmail.com"),
                new User(3, "user3", "pass3", "u3@gmail.com")
        ));
    }

    @Override
    public User getOneByID(Integer ID) {
        for (User user : getAll()) {
            if (user.getID() == ID) return user;
        }
        return null;
    }

    public User getOneByLoginAndPassword(String login, String password) { //TODO: Przerobić na JDBC po implementacji bazy
        for (User user : getAll()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void insert(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public String getTableName() {
        return "Users";
    }
}
