package server.doa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import shared.model.*;
/**
 * Created by KevinTsou on 2/27/2018.
 */
public class EventDaoTest {
    @Before
    public void setUp() throws Exception {
        Database db=new Database();
        db.openConnection();
    }
    @Test
    public void addevent() throws Exception {
        EventDao Dao=new EventDao();
        EventMod eventMod=new EventMod();
        eventMod.setEventType("d");
        eventMod.setLongitude("123");
        eventMod.setLatitude("456");
        eventMod.setCountry("ta");
        eventMod.setCity("tai");
        eventMod.setDescendant("kevin");
        eventMod.setPersonID("123");
        eventMod.setEventID("456");
        eventMod.setYear("1890");
        assertTrue(Dao.addevent(eventMod));
        //false if the same event is added
        assertFalse(Dao.addevent(eventMod));
        eventMod.setPersonID(null);
        //False if one of the field is null
        assertFalse(Dao.addevent(eventMod));
    }


    @Test
    public void singleevent() throws Exception {

        EventDao Dao=new EventDao();
        //Check if the personID is right;
        assertEquals("123",Dao.singleevent("456").getPersonID());
        //Check the descendant
        assertEquals("kevin",Dao.singleevent("456").getDescendant());
        //NUll if eventID doesn't exist
        //assertEquals(null,Dao.singleevent("123"));
    }


    @Test
    public void getuserName() throws Exception {
        EventDao Dao=new EventDao();
        assertEquals("kevin",Dao.getuserName("456"));
        //null if username doesn't not exist with specfic eventID;
        assertEquals(null,Dao.getuserName("none"));

    }

    @Test
    public void geteventID() throws Exception {
        EventDao Dao=new EventDao();
        assertEquals("456",Dao.geteventID("kevin"));
        //null if eventID doesn't not exist with specfic username;
        assertEquals(null,Dao.geteventID("k"));

    }
    @Test
    public void clean() throws Exception
    {
        Database db=new Database();
        db.dropall();
    }

}
