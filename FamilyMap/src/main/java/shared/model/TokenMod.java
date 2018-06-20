package shared.model;
import java.util.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class TokenMod {
    protected String authtoken;
    /**
     * Constructor for TokenMod class
     * @return a randomid;
     */
    public TokenMod()
    {
        authtoken=UUID.randomUUID().toString();
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }
}
