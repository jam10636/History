package com.example.kevintsou.image.model;

import android.text.style.TtsSpan;

import java.util.ArrayList;

import shared.model.EventMod;
import shared.model.PersonMod;

/**
 * Created by KevinTsou on 4/15/2018.
 */

public class TestFunction {
    private ArrayList<Members>memberslist=new ArrayList<>();
    private ArrayList<EventMod>sortlist=new ArrayList<>();
    private ArrayList<EventMod>eventlist=new ArrayList<>();
    private ArrayList<PersonMod>personlist=new ArrayList<>();
    //Function for testing family relation
    public ArrayList<Members> familyrelation(String personid)
    {
        Members members=new Members();
        PersonMod person=Model.instance().getPerson().get(personid);
            memberslist.clear();
            if(person.getMother()!=null)
            {
                members=new Members();
                members.setinfo(Model.instance().getPerson().get(person.getMother()));
                members.setTie("Mother");
                memberslist.add(members);
            }
            if(person.getFather()!=null)
            {
                members=new Members();
                members.setinfo(Model.instance().getPerson().get(person.getFather()));
                members.setTie("Father");
                memberslist.add(members);
            }
            if(person.getSpouse()!=null)
            {
                members=new Members();
                members.setinfo(Model.instance().getPerson().get(person.getSpouse()));
                members.setTie("Spouse");
                memberslist.add(members);
            }
            for(PersonMod p:Model.instance().getPeoples())
            {

                if(p.getFather()!=null)
                {
                    if(p.getFather().equals(person.getPersonID())) {
                        members = new Members();
                        members.setinfo(p);
                        members.setTie("Child");
                        memberslist.add(members);
                    }
                }
                if(p.getMother()!=null)
                {
                    if(p.getMother().equals(person.getPersonID())) {
                        members = new Members();
                        members.setinfo(p);
                        members.setTie("Child");
                        memberslist.add(members);
                    }
                }
            }
            return memberslist;
        }
    //Function for testing sorting events
    public ArrayList<EventMod> sortEvent(ArrayList<EventMod>event)
    {
        ArrayList<EventMod> sortedlist=new ArrayList<>();
        ArrayList<EventMod>events=event;
        for(EventMod e:events)
        {
            if((!e.getEventType().toLowerCase().equals("death"))&&(!e.getEventType().toLowerCase().equals("birth")))
            {
                sortedlist.add(e);
            }
        }
        for(int i=0;i<sortedlist.size()-1;i++)
        {
            for(int a=0;a<sortedlist.size()-1;a++) {
                if (Integer.valueOf(sortedlist.get(a).getYear()) > Integer.valueOf(sortedlist.get(a + 1).getYear())) {
                    EventMod sortevent = sortedlist.get(a);
                    sortedlist.set(a, sortedlist.get(a + 1));
                    sortedlist.set(a + 1, sortevent);
                }
                else if(sortedlist.get(a).getYear().equals(sortedlist.get(a+1).getYear()))
                {
                    if(sortedlist.get(a).getEventType().compareTo(sortedlist.get(a+1).getEventType())<0)
                    {
                        EventMod sortevent = sortedlist.get(a);
                        sortedlist.set(a, sortedlist.get(a + 1));
                        sortedlist.set(a + 1, sortevent);
                    }
                }
            }
        }
        for(EventMod e:events)
        {
            if(e.getEventType().toLowerCase().equals("birth"))
            {
                sortedlist.add(0,e);
            }
            if(e.getEventType().toLowerCase().equals("death"))
            {
                sortedlist.add(e);
            }
        }
        return sortedlist;
    }
    //Function for testing searching
    public void search(String personid,String text)
    {
        eventlist.clear();
        personlist.clear();
        PersonMod person=Model.instance().getPerson().get(personid);
        if(!text.equals("")) {
            for (EventMod e : Model.instance().getEvents()) {
                if (e.getEventType().toLowerCase().contains(text.toLowerCase()) || e.getCountry().toLowerCase().contains(text.toLowerCase()) ||
                        e.getCity().toLowerCase().contains(text.toLowerCase()) ||
                        e.getYear().toLowerCase().contains(text.toLowerCase())) {
                    eventlist.add(e);
                }
            }
            for (PersonMod p : Model.instance().getPeoples()) {
                if (p.getFirstName().toLowerCase().contains(text.toLowerCase()) || p.getLastName().toLowerCase().contains(text.toLowerCase())) {
                    personlist.add(p);
                }
            }
        }

    }
    //Function for testing filter
    public void filter(String sides) {
        personlist.clear();
        if (sides.equals("dad")) {
            for (String id : Model.instance().getDadside()) {
                PersonMod personMod = Model.instance().getPerson().get(id);
                personlist.add(personMod);
            }
        } else {
            for (String id : Model.instance().getMomside()) {
                PersonMod personMod = Model.instance().getPerson().get(id);
                personlist.add(personMod);
            }
        }
    }

    public ArrayList<Members> getMemberslist() {
        return memberslist;
    }

    public ArrayList<EventMod> getSortlist() {
        return sortlist;
    }

    public ArrayList<EventMod> getEventlist() {
        return eventlist;
    }

    public ArrayList<PersonMod> getPersonlist() {
        return personlist;
    }
}
