package server.services;
import server.doa.PersonDao;
import server.doa.TokenDoa;
import server.doa.UserDao;
import shared.result.*;
import server.doa.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class EventService {
    protected String eventid=null;
    protected String authID=null;
    /**
     * construct for single EventMod request
     * @param eventid
     * @param authID
     */
    public EventService(String authID, String eventid)
    {
        this.eventid=eventid;
        this.authID=authID;
    }

    /**
     * construct for array of all EventMod request
     * @param authID
     */
    public EventService(String authID)
    {
        this.authID=authID;
    };

    /**
     *Service function of EventMod call
     * @return result of EventMod
     */
    public EventResult result()
    {
        EventResult result;
        TokenDoa token=new TokenDoa();
        EventDao event=new EventDao();
        UserDao user=new UserDao();
        if(token.reusername(authID)==null)
        {
            return null;
        }
        if(eventid!=null)
        {
            if(!token.reusername(authID).equals(event.getuserName(eventid)))
            {
                return null;
            }
            result=new EventResult(event.singleevent(eventid));
        }
        else
        {
            result=new EventResult(event.Event(token.reusername(authID)));
        }
        return result;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }
}
