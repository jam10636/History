package com.example.kevintsou.image.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import shared.model.EventMod;
import shared.model.PersonMod;

/**
 * Created by KevinTsou on 4/1/2018.
 */

public class Filter {
    Map<String,Boolean> eventfilter=new TreeMap<>();
    ArrayList<EventMod>fitleredevetn=new ArrayList<>();
    Filter()
    {};
    public void doevent()
    {
        for(EventMod e: Model.instance().getEvents())
        {
            if(!eventfilter.containsKey(e.getEventType().toLowerCase()))
            {
                eventfilter.put(e.getEventType().toLowerCase(),true);
            }
        }
        eventfilter.put("Father's side",true);
        eventfilter.put("Mother's side",true);
        eventfilter.put("Male Events",true);
        eventfilter.put("Female Events",true);
    }
    public void setfilter()
    {
        fitleredevetn.clear();
        boolean istrue=true;
        for(EventMod e:Model.instance().getEvents())
        {
            istrue=true;
            if(Model.instance().getFilter().getEventfilter().get(e.getEventType().toLowerCase())==false)
            {
                istrue=false;
            }
            if(Model.instance().getFilter().getEventfilter().get("Male Events")==false)
            {
                PersonMod personMod=Model.instance().getPerson().get(e.getPersonID());
                if(personMod.getGender().equals("m"))
                {
                    istrue=false;
                }
            }
            if(Model.instance().getFilter().getEventfilter().get("Female Events")==false)
            {
                PersonMod personMod=Model.instance().getPerson().get(e.getPersonID());
                if(personMod.getGender().equals("f"))
                {
                    istrue=false;
                }
            }
            if(Model.instance().getFilter().getEventfilter().get("Mother's side")==false)
            {
                if(Model.instance().getMomside().contains(e.getPersonID()))
                {
                    Log.v("Mom","6");
                    istrue=false;
                }
            }
            if(Model.instance().getFilter().getEventfilter().get("Father's side")==false)
            {

                if(Model.instance().getDadside().contains(e.getPersonID()))
                {
                    Log.v("here",Boolean.toString(Model.instance().getDadside().contains(e.getPersonID())));
                    istrue=false;
                }
            }
            if(istrue==true)
            {
                fitleredevetn.add(e);
            }
        }
    }
    public Map<String, Boolean> getEventfilter() {
        return eventfilter;
    }

    public ArrayList<EventMod> getFitleredevetn() {
        return fitleredevetn;
    }
}
