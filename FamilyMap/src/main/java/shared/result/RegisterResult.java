package shared.result;

/**
 * Created by KevinTsou on 2/12/2018.
 */

public class RegisterResult {
    protected String username;
    protected String personid;
    protected String authtoken;

    /**
     * set data for RegisterService result
     * @param username
     * @param personid
     * @param authtoken
     */
    public RegisterResult(String username, String personid, String authtoken)
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
}
