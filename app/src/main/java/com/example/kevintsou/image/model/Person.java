package com.example.kevintsou.image.model;

import android.util.Log;

import java.util.ArrayList;

import shared.model.PersonMod;

/**
 * Created by KevinTsou on 3/22/2018.
 */

public class Person {
    public Person(){};
    public void setpeoplemap()
    {
        for(PersonMod p:Model.instance().getPeoples())
        {
            Model.instance().getPerson().put(p.getPersonID(),p);
        }
    }
}
