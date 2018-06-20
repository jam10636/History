package com.example.kevintsou.image.model;

import android.util.ArraySet;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.*;

import shared.model.*;

/**
 * Created by KevinTsou on 3/17/2018.
 */

public class Model {
    private Map<String,PersonMod> person=new TreeMap<>();
    private Map<String,EventMod>event=new TreeMap<>();;
    private Map<String,ArrayList<PersonMod>>people;
    private Map<String,ArrayList<EventMod>>eventes=new TreeMap<>();;
    private ArrayList<PersonMod>peoples;
    private ArrayList<EventMod>events;
    private Set<String>dadside=new TreeSet<>();
    private Set<String>momside=new TreeSet<>();
    PersonMod personMod;
    Filter filter=new Filter();
    Settings settings=new Settings();
    private Model(){};
    private static Model _instance;
    public static Model instance()
    {
        if(_instance==null)
        {
            _instance=new Model();
        }
        return _instance;
    }

    private String authtoken;
    private String personID;

   public Map<String, PersonMod> getPerson() {
        return person;
    }

    public void setPerson(Map<String, PersonMod> person) {
        this.person = person;
    }

    public Map<String, EventMod> getEvent() {
        return event;
    }

    public void setEvent(Map<String, EventMod> event) {
        this.event = event;
    }

    public PersonMod getPersonMod() {
        return personMod;
    }

    public void setPersonMod(PersonMod personMod) {
        this.personMod = personMod;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public void setall(String ID,String auth)
    {
        this.personID=ID;
        this.authtoken=auth;
    }

    public ArrayList<PersonMod> getPeoples() {
        return peoples;
    }

    public void setPeoples(ArrayList<PersonMod> peoples) {
        this.peoples = peoples;
    }

    public void setEvents(ArrayList<EventMod> events) {
        this.events = events;
    }

    public ArrayList<EventMod> getEvents() {
        return events;
    }

    public Map<String, ArrayList<PersonMod>> getPeople() {
        return people;
    }

    public void setPeople(Map<String, ArrayList<PersonMod>> people) {
        this.people = people;
    }

    public Map<String, ArrayList<EventMod>> getEventes() {
        return eventes;
    }

    public void setEventes(Map<String, ArrayList<EventMod>> eventes) {
        this.eventes = eventes;
    }
    public void getside()
    {
        ArrayList<String>dad=new ArrayList<>();
        ArrayList<String>dadloop=new ArrayList<>();
        if(personMod.getFather()!=null)
        {
            dadside.add(personMod.getFather());
            dadloop.add(personMod.getFather());
            while(dadloop.size()!=0) {
                for (String id : dadloop) {
                    PersonMod p=new PersonMod();
                    p=Model.instance().getPerson().get(id);
                    if(p.getFather()!=null) {
                        dad.add(p.getFather());
                        dadside.add(p.getFather());
                    }
                    if(p.getMother()!=null)
                    {
                        dad.add(p.getMother());
                        dadside.add(p.getMother());
                    }
                }
                dadloop=new ArrayList<>(dad);
                dad.clear();
            }
        }
        dad.clear();
        dadloop.clear();
        if(personMod.getMother()!=null)
        {
            momside.add(personMod.getMother());
            dadloop.add(personMod.getMother());
            while(dadloop.size()!=0) {
                for (String id : dadloop) {
                    PersonMod p=new PersonMod();
                    p=Model.instance().getPerson().get(id);
                    if(p.getFather()!=null) {
                        dad.add(p.getFather());
                        momside.add(p.getFather());
                    }
                    if(p.getMother()!=null)
                    {
                        dad.add(p.getMother());
                        momside.add(p.getMother());
                    }
                }
                dadloop=new ArrayList<>(dad);
                dad.clear();
            }
        }
    }
    public void clear()
    {
        this.peoples.clear();
        this.events.clear();
        this.dadside.clear();
        this.momside.clear();
        this.eventes.clear();
        this.filter=new Filter();
        this.person.clear();
        this.event.clear();
        this.settings=new Settings();
    }

    public Set<String> getDadside() {
        return dadside;
    }

    public Set<String> getMomside() {
        return momside;
    }

    public Filter getFilter() {
        return filter;
    }

    public Settings getSettings() {
        return settings;
    }
}
