package server.services;
import server.doa.*;
import shared.result.*;

/**
 * Created by KevinTsou on 2/11/2018.
 */

public class PersonService {
    protected String personid=null;
    protected String authID=null;
    /**
     * Construct for request for single PersonMod
     * @param personid
     * @param authID
     */
   public PersonService(String authID, String personid)
    {
        this.personid=personid;
        this.authID=authID;
    }

    /**
     * Construct for request for array of PersonMod
     * @param authID
     */
    public PersonService(String authID)
    {
        this.authID=authID;
    };
    /**
     *Service function of getperson call
     * @return result of PersonMod request
     */
    public PeopleResult result()
    {
        PeopleResult result;
        TokenDoa token=new TokenDoa();
        PersonDao person=new PersonDao();
        UserDao user=new UserDao();
        if(token.reusername(authID)==null)
        {
            return null;
        }
        //make sure to check personid if it is valid;
        if(personid!=null)
        {
            if(!token.reusername(authID).equals(person.getuserName(personid)))
            {
                return null;
            }
            result=new PeopleResult(person.Person(personid));
        }
        else
        {
            result=new PeopleResult(person.people(token.reusername(authID)));
        }
        return result;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }
}
