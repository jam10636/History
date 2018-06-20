package server.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import server.doa.UserDao;
import shared.model.EventMod;
import shared.model.PersonMod;
import shared.model.UserMod;
import shared.request.LoadRequest;
import shared.request.RegisterRequest;
import shared.result.FillResult;
import shared.result.LoadResult;
import server.doa.*;
import shared.result.RegisterResult;

import static org.junit.Assert.*;
/**
 * Created by KevinTsou on 2/28/2018.
 */
public class FillServiceTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void refill() throws Exception {
        FillService service=new FillService("KevinTsou");
        UserMod userMod=new UserMod("KevinTsou","kk123456","jam10","k","e","f");
        PersonDao Dao=new PersonDao();
        PersonMod person=new PersonMod(userMod);
        TokenDoa tokenDoa=new TokenDoa();
        Dao.addperson(person);
        UserDao userDao=new UserDao();
        userDao.adduser(userMod);
        FillResult result=service.refill();
        //Default generation
        assertEquals("Successfuly added 31 and 124 events to database",result.getmessage());
        service.setUserName("kevin");
        result=service.refill();
        //When username does not exist
        assertEquals(null,result);
        service.setGeneration(0);
        service.setUserName("KevinTsou");
        result=service.refill();
        //When generation is 0
        assertEquals("Successfuly added 1 and 4 events to database",result.getmessage());

    }


    @Test
    public void createevent() throws Exception {
       FillService service=new FillService("KevinTsou");
       EventMod eventMod=service.createevent("KevinTsou","123",1900,"Birth");
       //Making sure Eventmod has been created successfully
       assertEquals("KevinTsou",eventMod.getDescendant());
       assertEquals("123",eventMod.getPersonID());

    }

    @Test
    public void eventype() throws Exception {
        FillService service=new FillService("kevin");
        //Check for the different Year for different event
        assertEquals("920",service.eventype("Birth",1000));
        assertEquals("990",service.eventype("Death",1000));
        assertEquals("928",service.eventype("Baptism",1000));
        assertEquals("950",service.eventype("marriage",1000));
        Database db=new Database();
        db.dropall();
    }

}