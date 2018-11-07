package com.example.adars.gotchya;

import com.example.adars.gotchya.DataModel.DataModel.UserModel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Adam Bachorz on 07.11.2018.
 */
public class UserModelUnitTest {

    @Test
    public void correct_UserLogInAndOut(){
        UserModel.getInstance().logIn("user1", "pass1");

        assertNotNull(UserModel.getInstance().getCurrentUser());
        assertEquals("user1", UserModel.getInstance().getCurrentUser().getLogin());

        UserModel.getInstance().logOut();
        assertNull(UserModel.getInstance().getCurrentUser());
    }
}
