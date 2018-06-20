package com.example.kevintsou.image.model;

import android.graphics.PorterDuff;

import com.example.kevintsou.image.proxy.Proxy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Member;
import java.util.ArrayList;

import shared.model.EventMod;
import shared.model.PersonMod;

import static org.junit.Assert.*;

/**
 * Created by KevinTsou on 4/15/2018.
 */
public class TestFunctionTest {
    @Before
    public void setUp() throws Exception {
        Proxy proxy=new Proxy();
        Model.instance().setall("Sheila_Parker","5f94431f-fea7-43ba-a41a-408d65e7bf92");
        Model.instance().setPersonMod(proxy.personResult("192.168.1.250","3000",Model.instance().getAuthtoken(),
                Model.instance().getPersonID()).getPersonMod());
        Model.instance().setPeoples(proxy.peopleResult("192.168.1.250","3000",Model.instance().getAuthtoken()).getPeople());
        Model.instance().setEvents(proxy.eventResult("192.168.1.250","3000",Model.instance().getAuthtoken()).getEventslist());
        Person person=new Person();
        person.setpeoplemap();
    }
    //Test if person has correct family memeber
    @Test
    public void familyrelation() throws Exception {
        TestFunction testFunction=new TestFunction();
        ArrayList<Members>familylist=testFunction.familyrelation("Sheila_Parker");
        //test for mother
        assertEquals("Betty_White",familylist.get(0).getPersonid());
        //test for father
        assertEquals("Blaine_McGary",familylist.get(1).getPersonid());
        //test for spouse
        assertEquals("Davis_Hyer",familylist.get(2).getPersonid());

        //test for person with children
       familylist=testFunction.familyrelation("Blaine_McGary");
        //test for mother
        assertEquals("Mrs_Rodham",familylist.get(0).getPersonid());
        //test for father
        assertEquals("Ken_Rodham",familylist.get(1).getPersonid());
        //test for spouse
        assertEquals("Betty_White",familylist.get(2).getPersonid());
        //test for children
        assertEquals("Sheila_Parker",familylist.get(3).getPersonid());

        //test for person without mom and dad
        familylist=testFunction.familyrelation("Ken_Rodham");
        //test for spouse
        assertEquals("Mrs_Rodham",familylist.get(0).getPersonid());
        //test for children
        assertEquals("Blaine_McGary",familylist.get(1).getPersonid());
    }
    //test for sorted events
    @Test
    public void sortEvent() throws Exception {
        TestFunction testFunction=new TestFunction();
        ArrayList<EventMod>eventMods=new ArrayList<>();
        EventMod event=new EventMod("1","1","1","1","1","1","death","1800");
        eventMods.add(event);
        event=new EventMod("1","1","1","1","1","1","birth","2000");
        eventMods.add(event);
        event=new EventMod("1","1","1","1","1","1","yes","1860");
        eventMods.add(event);
        event=new EventMod("1","1","1","1","1","1","no","1840");
        eventMods.add(event);
        eventMods=testFunction.sortEvent(eventMods);

        //test both birth and death will be first and last regardless of their year and the rest are sorted correctly
        assertEquals("birth",eventMods.get(0).getEventType());
        assertEquals("no",eventMods.get(1).getEventType());
        assertEquals("yes",eventMods.get(2).getEventType());
        assertEquals("death",eventMods.get(3).getEventType());

        //test when two word are the sames but one is capital. Lower case will be higher than capital
        eventMods.get(1).setEventType("No");
        eventMods.get(2).setEventType("no");
        eventMods.get(2).setYear("1840");
        eventMods=testFunction.sortEvent(eventMods);
        assertEquals("birth",eventMods.get(0).getEventType());
        assertEquals("no",eventMods.get(1).getEventType());
        assertEquals("No",eventMods.get(2).getEventType());
        assertEquals("death",eventMods.get(3).getEventType());

    }

    @Test
    public void search() throws Exception {
        TestFunction testFunction=new TestFunction();
        //if empty string is entered, then the size will be 0
        testFunction.search("Sheila_Parker","");
        assertEquals(0,testFunction.getPersonlist().size());
        assertEquals(0,testFunction.getEventlist().size());

        //check if it is case insensitive
        testFunction.search("Sheila_Parker","SHEILA");
        assertEquals(1,testFunction.getPersonlist().size());
        assertEquals("Sheila_Parker",testFunction.getPersonlist().get(0).getPersonID());
        assertEquals(0,testFunction.getEventlist().size());

        //check if none is found
        testFunction.search("Sheila_Parker","wtf?");
        assertEquals(0,testFunction.getPersonlist().size());
        assertEquals(0,testFunction.getEventlist().size());

        //check with rodham
        testFunction.search("Sheila_Parker","rodham");
        assertEquals(2,testFunction.getPersonlist().size());
        assertEquals(0,testFunction.getEventlist().size());

        //chech if events works search work
        testFunction.search("Sheila_Parker","birth");
        assertEquals(0,testFunction.getPersonlist().size());
        assertEquals(3,testFunction.getEventlist().size());

        //making sure both works properly
        testFunction.search("Sheila_Parker","an");
        assertEquals(1,testFunction.getPersonlist().size());
        assertEquals("Frank_Jones",testFunction.getPersonlist().get(0).getPersonID());
        assertEquals(7,testFunction.getEventlist().size());
    }

    @Test
    public void filter() throws Exception {
        Model.instance().getside();
        TestFunction testFunction=new TestFunction();
        //Sort the event dynamically with evetns
        Model.instance().filter.doevent();
        testFunction.filter("dad");
        //Checking the dad side with sheila parker
        assertEquals("Blaine_McGary",testFunction.getPersonlist().get(0).getPersonID());
        assertEquals("Ken_Rodham",testFunction.getPersonlist().get(1).getPersonID());
        assertEquals("Mrs_Rodham",testFunction.getPersonlist().get(2).getPersonID());
        //Checking the mom side with sheila parker
        testFunction.filter("mom");
        assertEquals("Betty_White",testFunction.getPersonlist().get(0).getPersonID());
        assertEquals("Frank_Jones",testFunction.getPersonlist().get(1).getPersonID());
        assertEquals("Mrs_Jones",testFunction.getPersonlist().get(2).getPersonID());

        //Checking if only female events exist
        Model.instance().filter.getEventfilter().put("Male Events",false);
        Model.instance().filter.setfilter();
        for(EventMod e:Model.instance().filter.getFitleredevetn())
        {
            PersonMod personMod=Model.instance().getPerson().get(e.getPersonID());
            assertEquals("f",personMod.getGender());
        }
        Model.instance().filter.getEventfilter().put("Male Events",true);

        //Checking if only males events exist
        Model.instance().filter.getEventfilter().put("Female Events",false);
        Model.instance().filter.setfilter();
        for(EventMod e:Model.instance().filter.getFitleredevetn())
        {
            PersonMod personMod=Model.instance().getPerson().get(e.getPersonID());
            assertEquals("m",personMod.getGender());
        }
        Model.instance().filter.getEventfilter().put("Female Events",true);
        Model.instance().filter.setfilter();

        //originally, it has 16 events associated with sheila parker's events
        assertEquals(16,Model.instance().getFilter().getFitleredevetn().size());

        //2 are filtered out "completed asteroids" and because it is case insensitive
        Model.instance().filter.getEventfilter().put("completed asteroids",false);
        Model.instance().filter.setfilter();
        assertEquals(14,Model.instance().getFilter().getFitleredevetn().size());

        //3 events are filtered out "birth"
        Model.instance().filter.getEventfilter().put("birth",false);
        Model.instance().filter.setfilter();
        assertEquals(11,Model.instance().getFilter().getFitleredevetn().size());

        //5 events are filters out "death" and "marriage"
        Model.instance().filter.getEventfilter().put("death",false);
        Model.instance().filter.getEventfilter().put("marriage",false);
        Model.instance().filter.setfilter();
        assertEquals(6,Model.instance().getFilter().getFitleredevetn().size());

        //0 event left if everything is turned off and it is case insensitive
        Model.instance().filter.getEventfilter().put("Graduated from BYU".toLowerCase(),false);
        Model.instance().filter.getEventfilter().put("Did a backflip".toLowerCase(),false);
        Model.instance().filter.getEventfilter().put("learned Java".toLowerCase(),false);
        Model.instance().filter.getEventfilter().put("Caught a frog".toLowerCase(),false);
        Model.instance().filter.getEventfilter().put("Ate Brazilian Barbecue".toLowerCase(),false);
        Model.instance().filter.getEventfilter().put("Learned to Surf".toLowerCase(),false);
        Model.instance().filter.setfilter();
        assertEquals(0,Model.instance().getFilter().getFitleredevetn().size());




    }

}