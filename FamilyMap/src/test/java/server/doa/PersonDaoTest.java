package server.doa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;

import static org.junit.Assert.*;
import shared.model.*;
import server.doa.*;
/**
 * Created by KevinTsou on 3/1/2018.
 */
public class PersonDaoTest {
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void addperson() throws Exception {
        Database db=new Database();
        db.dropall();
        PersonDao Dao=new PersonDao();
        PersonMod person=new PersonMod();
        person.setGender("m");
        person.setPersonID("123");
        person.setFirstName("meng");
        person.setLastName("tsou");
        person.setDescendant("kevin");
        //adding this person for the first time
        assertTrue(Dao.addperson(person));
        //Adding the same person
        assertFalse(Dao.addperson(person));
        person.setDescendant(null);
        //False if one of the data is null and invalid
        assertFalse(Dao.addperson(person));
        person.setDescendant("kevin");
        person.setPersonID("456");
        assertTrue(Dao.addperson(person));
        person.setPersonID("789");
        assertTrue(Dao.addperson(person));

    }

    @Test
    public void people() throws Exception {
        PersonDao Dao=new PersonDao();
        //If username is not found, the people array will be 0
        assertEquals(0,Dao.people("k").size());
        //if username is found. I added different entry with different ID in the database straight up
         assertEquals(3,Dao.people("kevin").size());
        //Make sure the descendant matchs the expected one
        assertEquals("kevin",Dao.people("kevin").get(1).getDescendant());
    }

    @Test
    public void person() throws Exception {
        PersonDao Dao=new PersonDao();
        //Checking if the right one is returned
        assertEquals("kevin",Dao.Person("123").getDescendant());
        //Return null if the personID doesn't not exist
//        assertEquals(null,Dao.Person("notexist"));

    }

    @Test
    public void getuserName() throws Exception {
        PersonDao Dao=new PersonDao();
        //if it person doesn't exist
        assertEquals(null,Dao.getuserName("kevintsou"));
        //if it exists
        assertEquals("kevin",Dao.getuserName("123"));
    }
    @Test
    public void clean() throws Exception
    {
        Database db=new Database();
        db.dropall();
    }

}