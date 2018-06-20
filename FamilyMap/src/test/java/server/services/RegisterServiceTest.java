package server.services;

import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;

import server.doa.Database;
import server.doa.UserDao;
import shared.model.UserMod;
import shared.request.RegisterRequest;
import shared.result.RegisterResult;

import static org.junit.Assert.*;

/**
 * Created by KevinTsou on 3/1/2018.
 */
public class RegisterServiceTest {
    @Before
    public void start()throws Exception
    {

    }
    @Test
    public void result() throws Exception {
        RegisterService service=new RegisterService();
        RegisterResult registerResult;
        RegisterRequest request=new RegisterRequest("KevinTsou","kk123456","jam10636","kevin","tsou","m");
        registerResult=service.result(request);
        //Making sure it returns the right userName
        assertEquals("KevinTsou",registerResult.getUsername());
        registerResult=service.result(request);
        //Return null if username has been taken;
        assertEquals(null,registerResult);
        request.setEmail(null);
        registerResult=service.result(request);
        //return null if missing data
        assertEquals(null,registerResult);
        Database db=new Database();
        db.dropall();
    }

}