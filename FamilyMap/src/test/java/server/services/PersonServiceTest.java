package server.services;

import org.junit.Before;
import org.junit.Test;

import server.doa.Database;
import server.doa.PersonDao;
import server.doa.TokenDoa;
import server.doa.UserDao;
import shared.model.*;
import shared.result.*;
import server.services.*;
import static org.junit.Assert.*;
import shared.model.*;
/**
 * Created by KevinTsou on 3/1/2018.
 */
public class PersonServiceTest {
    @Before
    public void start() throws Exception
    {
    }
    @Test
    public void result() throws Exception {
        PersonDao Dao=new PersonDao();
        PersonMod person=new PersonMod();
        person.setGender("m");
        person.setPersonID("456");
        person.setFirstName("meng");
        person.setLastName("tsou");
        person.setDescendant("KevinTsou");
        TokenDoa tokenDoa=new TokenDoa();
        tokenDoa.addtoken("123","KevinTsou");
        //adding this person for the first time
        Dao.addperson(person);
        person.setPersonID("kt");
        Dao.addperson(person);
        PersonService service=new PersonService("123","456");
        PeopleResult result=service.result();
        //Making sure the descendant is right
        assertEquals("KevinTsou",result.getPersonMod().getDescendant());
        service.setAuthID("789");
        result=service.result();
        //AuthID and personID does not match up
        assertEquals(null,result);
        service.setPersonid("kee");
        result=service.result();
        //Wrong AuthID or it does not exist
        assertEquals(null,result);
        service.setPersonid(null);
        service.setAuthID("123");
        result=service.result();
        //Make sure I get the right numbers of people back
        assertEquals(2,result.getPeople().size());
        //Make sure the descendant are correct
        assertEquals("KevinTsou",result.getPeople().get(0).getDescendant());
        assertEquals("456",result.getPeople().get(0).getPersonID());
        Database db=new Database();
        db.dropall();

    }

}