package server.doa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;

import shared.model.UserMod;
import server.doa.*;
import static org.junit.Assert.*;

/**
 * Created by KevinTsou on 2/28/2018.
 */
public class UserDaoTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void adduser() throws Exception {

        UserMod users=new UserMod("kevintsou12345","kk123456","jam10636@gmail.com","Kevin","Tsou","Male");
        users.setPersonID("onedollar");
        UserDao Dao=new UserDao();
        //check for first time registering
        assertTrue(Dao.adduser(users));
        //Check for same username
        assertFalse(Dao.adduser(users));
        users=new UserMod("KevinTsou","kk123456",null,"k","e","f");
        //check for missing value
        assertFalse(Dao.adduser(users));

    }

    @Test
    public void login() throws Exception {
        UserDao Dao=new UserDao();
        //Return not null if login is corret
        assertNotNull(Dao.login("kevintsou12345","kk123456"));
        //Return null if login is bad
        assertEquals(null,Dao.login("kevintsou","kk1234256"));
    }
    @Test
    public void checkdup() throws Exception{
        UserDao Dao=new UserDao();
        assertTrue(Dao.checkdup("kevintsou12345"));
       //Check for duplicate
        assertFalse(Dao.checkdup("kt"));
    }@Test
    public void getuserName() throws Exception
    {
        UserDao Dao=new UserDao();
        //return username with specific ID
        assertEquals("kevintsou12345",Dao.getuserName("onedollar"));
        //return null if not found
        assertEquals(null,Dao.getuserName("f8df2e13asdasdafds-b56c-4379-a8b8-a9d7f0985c36"));
    }
    @Test
    public void getuserID() throws Exception
    {
        UserDao Dao=new UserDao();
        assertEquals("onedollar",Dao.getuserID("kevintsou12345"));
        //wrong uername
        assertEquals(null,Dao.getuserID("f8df2e13asdasdafds-b56c-4379-a8b8-a9d7f0985c36"));
        Database db=new Database();
        db.dropall();
    }



}