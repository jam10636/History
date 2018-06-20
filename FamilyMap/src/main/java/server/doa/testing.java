package server.doa;
import java.io.FileReader;
import java.io.Reader;

import java.io.*;
import com.google.gson.*;
import server.services.*;
import shared.model.PersonMod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by KevinTsou on 2/18/2018.
 */

public class testing{
    public static void main(String args[]) throws FileNotFoundException {
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
        System.out.print(Dao.people("kevin").size());
    }
}
