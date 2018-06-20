package shared.result;
/**
 * Created by KevinTsou on 2/12/2018.
 */

public class LoginResult {
    protected String username;
    protected String personid;
    protected String authtoken;

    /**
     * set result for LoginService
     * @param username
     * @param personid
     * @param authtoken
     */
    public LoginResult(String username, String personid, String authtoken)
    {
        this.username=username;
        this.personid=personid;
        this.authtoken=authtoken;
    }
    public String getUsername() {
        return username;
    }


    public String getPersonid() {
        return personid;
    }


    public String getAuthtoken() {
        return authtoken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
