package dao;

import models.UserEntity;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    @Test
    public void readTest() throws Exception {
        // user mahendra should be an admin user.
        UserDAO userDAO = new UserDAO();
        userDAO.setup();
        UserEntity user = userDAO.read("mahendra");
        userDAO.exit();

        Assert.assertEquals("admin", user.getAccType());
    }

}