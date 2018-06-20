package shared.model;

import java.io.Serializable;

/**
 * Created by KevinTsou on 2/11/2018.
 */

public class PersonMod implements Serializable{
    protected String descendant;
    protected String personID=null;
    protected String firstName=null;
    protected String lastName=null;
    protected String gender=null;
    protected String father=null;
    protected String mother=null;
    protected String spouse=null;

    /**
     * set Person info
     * @param UserMod
     */
    public PersonMod(UserMod UserMod)
    {
        this.descendant= UserMod.userName;
        this.personID= UserMod.personID;
        this.firstName= UserMod.firstName;
        this.lastName= UserMod.lastName;
        this.gender= UserMod.gender;
    }
    public PersonMod(){};
    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
