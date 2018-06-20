package com.example.kevintsou.image.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import shared.model.EventMod;

/**
 * Created by KevinTsou on 3/22/2018.
 */

public class Event {
    private ArrayList<EventMod>eventMods;
    protected Map<String,Integer> colortype=new TreeMap<>();
    public Event(){};
    public void docolor()
    {
        eventMods=new ArrayList<>(Model.instance().getEvents());
        int color=0;
        for(EventMod e:eventMods)
        {
            if(!colortype.containsKey(e.getEventType().toLowerCase()))
            {
                colortype.put(e.getEventType().toLowerCase(),color);
                color+=30;
            }
        }
    }
    public void doevent()
    {
        for(EventMod e: Model.instance().getEvents())
        {
           if(Model.instance().getEventes().containsKey(e.getPersonID()))
            {
                Model.instance().getEventes().get(e.getPersonID()).add(e);
            }
            else
            {
                ArrayList<EventMod>eventMods=new ArrayList<>();
                eventMods.add(e);
                Model.instance().getEventes().put(e.getPersonID(),eventMods);
            }
            Model.instance().getEvent().put(e.getEventID(),e);
        }
    }
    public Map<String, Integer> getColortype() {
        return colortype;
    }
}
