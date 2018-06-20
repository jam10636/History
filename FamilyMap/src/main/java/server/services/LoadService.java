package server.services;
import java.sql.SQLException;
import java.util.ArrayList;

import server.doa.Database;
import shared.result.LoadResult;
import shared.request.*;
import shared.model.*;
import server.doa.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class LoadService {
    /**
     *Service function of LoadService call
     * @param e
     * @return result of LoadService
     */
    public LoadResult reload(LoadRequest e)throws SQLException
    {
        Database db=new Database();
        db.dropall();
        ArrayList<UserMod> userlist =e.getUserMods();
        ArrayList<PersonMod> personlist=e.getPeople();
        ArrayList<EventMod> eventlist=e.getEvent();
        UserDao user=new UserDao();
        PersonDao person=new PersonDao();
        EventDao event=new EventDao();
        for(UserMod u:userlist)
        {
            if(!user.adduser(u))
            {
                return null;
            }
        }
        for(PersonMod p:personlist)
        {
            if(!person.addperson(p))
            {
                return null;
            }
        }
        for(EventMod in:eventlist)
        {
            if(!event.addevent(in))
            {
                return null;
            }
        }
        LoadResult result=new LoadResult(userlist,personlist,eventlist);
        return result;
    }
}
