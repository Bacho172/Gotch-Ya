package com.example.adars.gotchya.DataModel.Repository;

import android.graphics.drawable.Drawable;

import com.example.adars.gotchya.Core.API.WebServiceAccess;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DomainModel.Privilege;
import com.example.adars.gotchya.DataModel.DomainModel.Status;
import com.example.adars.gotchya.DataModel.DomainModel.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        ArrayList<User> list = new ArrayList<>();
        try {

            WebServiceAccess access = new WebServiceAccess("users");
            JSONArray jsonArray = access.getJsonArray();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                User user = new User();
                user.setID(jsonObject.getInt("iduser"));
                user.setLogin(jsonObject.getString("name"));
                user.setEmail(jsonObject.getString("email"));
                Drawable image = Functions.imageFromURL(jsonObject.getString("image"), user.getID());
                user.setImage(image);
                user.setPrivilege(new Privilege(jsonObject.getString("privilege")));
                user.setStatus(new Status(jsonObject.getString("status")));


                list.add(user);
            }

        }
        catch (JSONException e) {
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            System.err.println(Functions.getExecutionError(getClass(), methodName, e));
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getOneByID(Integer ID) {
        for (User user : getAll()) {
            if (user.getID() == ID) return user;
        }
        return null;
    }

    public User getOneByLoginAndPassword(String login, String password) {
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
        return "users";
    }
}
