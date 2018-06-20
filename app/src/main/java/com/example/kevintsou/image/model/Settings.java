package com.example.kevintsou.image.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by KevinTsou on 4/2/2018.
 */

public class Settings {
    Map<String,String> colortype=new TreeMap<String,String>()
    {{
        put("lifestory","Blue");
        put("familystory","Red");
        put("spousestory","Green");
    }};
    Map<String,Boolean> switches=new TreeMap<String,Boolean>()
    {{
        put("lifestory",true);
        put("familystory",true);
        put("spousestory",true);

    }};
    ArrayList<String>colors=new ArrayList<String>()
    {{
        add("Blue");
        add("Red");
        add("Green");
    }};
    String demaptype="Normal";
    Map<String,Integer>colorhue=new TreeMap<String,Integer>()
    {{
        put("Blue",Color.BLUE);
        put("Red",Color.RED);
        put("Green",Color.GREEN);
    }};

    public Map<String, String> getColortype() {
        return colortype;
    }

    public void setColortype(Map<String, String> colortype) {
        this.colortype = colortype;
    }

    public Map<String, Boolean> getSwitches() {
        return switches;
    }

    public void setSwitches(Map<String, Boolean> switches) {
        this.switches = switches;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getDemaptype() {
        return demaptype;
    }

    public void setDemaptype(String demaptype) {
        this.demaptype = demaptype;
    }

    public Map<String, Integer> getColorhue() {
        return colorhue;
    }
}
