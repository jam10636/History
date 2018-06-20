package server.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import server.doa.Database;
import server.doa.EventDao;
import server.doa.TokenDoa;
import server.doa.UserDao;
import shared.model.*;
import shared.result.EventResult;
import shared.result.PeopleResult;

/**
 * Created by KevinTsou on 2/28/2018.
 */
public class EventServiceTest {
    @Before
    public void setUp() throws Exception {
        Database db=new Database();
        db.dropall();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void result() throws Exception {
        EventService service=new EventService("123");
        EventResult result;
        EventMod eventMod=new EventMod();
        EventDao eventDao=new EventDao();
        eventMod.setEventType("d");
        eventMod.setLongitude("123");
        eventMod.setLatitude("456");
        eventMod.setCountry("ta");
        eventMod.setCity("tai");
        eventMod.setDescendant("kevinTsou");
        eventMod.setPersonID("123");
        eventMod.setEventID("456");
        eventMod.setYear("1890");
        TokenDoa tokenDoa=new TokenDoa();
        tokenDoa.addtoken("123","kevinTsou");
        eventDao.addevent(eventMod);
        eventMod.setEventID("789");
        eventDao.addevent(eventMod);
        result=service.result();
        //Check the number of the list;
        assertEquals(2,result.getEventslist().size());
        //check the descendant
        assertEquals("kevinTsou",result.getEventslist().get(1).getDescendant());
        service.setAuthID("kkk");
        result=service.result();
        //Null if the authtoken does not exist
        assertEquals(null,result);
        service.setAuthID("123");
        service.setEventid("kkk");
        result=service.result();
        //Authtoken and eventID does not match up
        assertEquals(null,result);
        service.setEventid("789");
        result=service.result();
        //Successfully return with correct EventID and AuthID
        assertEquals("kevinTsou",result.getEvent().getDescendant());
        Database db=new Database();
        db.dropall();
    }

}