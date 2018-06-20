package server.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import server.doa.Database;
import server.doa.UserDao;
import shared.model.EventMod;
import shared.request.LoadRequest;
import shared.result.EventResult;
import shared.result.LoadResult;

import static org.junit.Assert.*;
import shared.model.*;

/**
 * Created by KevinTsou on 3/1/2018.
 */
public class LoadServiceTest {
    @Before
    public void start()
    {
    }
    @Test
    public void reload() throws Exception {
        Database db=new Database();
        db.dropall();
        ArrayList<PersonMod>persons=new ArrayList<>();
        ArrayList<EventMod>event=new ArrayList<>();
        ArrayList<UserMod>user=new ArrayList<>();
        LoadRequest request=new LoadRequest(user,persons,event);
        LoadResult result=new LoadResult(user,persons,event);
        LoadService service=new LoadService();
        result=service.reload(request);
        //If nothing has been added
        assertEquals("Successfully Added 0 Users, 0 people 0 events to the database.",result.returnmessage());
        PersonMod person=new PersonMod();
        person.setGender("m");
        person.setPersonID("456");
        person.setFirstName("meng");
        person.setLastName("tsou");
        person.setDescendant("KevinTsou");
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
        UserMod userMod=new UserMod("KevinTsou","kk123456","jam10","k","e","f");
        user.add(userMod);
        event.add(eventMod);
        persons.add(person);
        request=new LoadRequest(user,persons,event);
        result=service.reload(request);
        //one person and one event and one uesr has been added
        assertEquals("Successfully Added 1 Users, 1 people 1 events to the database.",result.returnmessage());
        db=new Database();
        db.dropall();
    }

}