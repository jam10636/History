package shared.result;

import java.util.ArrayList;

import shared.model.*;

/**
 * Created by KevinTsou on 2/13/2018.
 */

public class LoadResult {
    int usersize;
    int peoplesize;
    int eventsize;

    /**
     * Set result for LoadService
     * @param UserMods
     * @param people
     * @param event
     */
    public LoadResult(ArrayList<UserMod> UserMods, ArrayList<PersonMod>people, ArrayList<EventMod>event)
    {
        this.usersize= UserMods.size();
        this.peoplesize=people.size();
        this.eventsize=event.size();
    }

    /**
     *return the message depends on if it is ClearService or LoadService method
     * @return Successfully added X UserMods, Y persons, and Z events to the database.
     */
    public String returnmessage()
    {
        StringBuilder sb=new StringBuilder();
        sb.append("Successfully Added "+usersize+" Users, "+peoplesize+" people "+eventsize+" events to the database.");
        return sb.toString();
    }
}
