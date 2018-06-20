package server.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.doa.Database;
import server.doa.UserDao;
import shared.model.UserMod;
import shared.request.LoginRequest;
import shared.request.RegisterRequest;
import shared.result.LoginResult;
import shared.result.RegisterResult;

import static org.junit.Assert.*;

/**
 * Created by KevinTsou on 3/1/2018.
 */
public class LoginServiceTest {
    @Before
    public void setUp() throws Exception {

    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void result() throws Exception {
        UserDao userDao=new UserDao();
        UserMod userMod=new UserMod("KevinTsou","kk123456","jam10636","kevin","tsou","m");
        userDao.adduser(userMod);
        LoginService service=new LoginService();
        LoginRequest request=new LoginRequest("KevinTsou","kk123456");
        LoginResult result=service.result(request);
        //Successful. Already added user in the database beforehand
        assertEquals("KevinTsou",result.getUsername());
        request.setPassword(null);
        //Return null if missing value
        result=service.result(request);
        assertEquals(null,result);
        request.setPassword("123");
        result=service.result(request);
        //Return null if wrong password or username
        assertEquals(null,result);
        request.setUsername("k");
        result=service.result(request);
        //Return null if wrong password or username
        assertEquals(null,result);
        Database db=new Database();
        db.dropall();
    }

}