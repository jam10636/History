package shared.model;

import java.util.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */
public class UserMod {
    protected String userName;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected String personID;
    /**
     * set UserMod info.
     * @param username
     * @param password
     * @param email
     * @param firstname
     * @param lastname
     * @param gender
     */
    public UserMod(String username, String password, String email, String firstname, String lastname, String gender)
    {
        this.userName=username;
        this.password=password;
        this.email=email;
        this.firstName=firstname;
        this.lastName=lastname;
        this.gender=gender;
        this.personID= UUID.randomUUID().toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public UserMod(){};


}
