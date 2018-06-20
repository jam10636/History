package shared.request;
import java.util.*;
import shared.model.*;
/**
 * Created by KevinTsou on 2/12/2018.
 */

public class LoadRequest {
    protected ArrayList<UserMod> users;
    protected ArrayList<PersonMod>persons;
    protected ArrayList<EventMod>events;

    /**
     *  Request body with parameter and data information
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(ArrayList<UserMod> users, ArrayList<PersonMod>persons, ArrayList<EventMod>events)
    {
        this.users = users;
        this.persons=persons;
        this.events=events;
    }
    public ArrayList<UserMod> getUserMods() {
        return users;
    }

    public void setUserMods(ArrayList<UserMod> userMods) {
        this.users = userMods;
    }

    public ArrayList<PersonMod> getPeople() {
        return persons;
    }

    public void setPeople(ArrayList<PersonMod> people) {
        this.persons = people;
    }

    public ArrayList<EventMod> getEvent() {
        return events;
    }

    public void setEvent(ArrayList<EventMod> event) {
        this.events = event;
    }
}
