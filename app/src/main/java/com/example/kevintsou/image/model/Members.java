package com.example.kevintsou.image.model;

import shared.model.PersonMod;

/**
 * Created by KevinTsou on 3/26/2018.
 */

public class Members {
    private String firstname;
    private String lastname;
    private String gender;
    private String tie;
    private String personid;

    public String getFirstname() {
        return firstname;
    }
    public void setinfo(PersonMod person)
    {
        this.firstname=person.getFirstName();
        this.lastname=person.getLastName();
        this.personid=person.getPersonID();
        this.gender=person.getGender();
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }
}
