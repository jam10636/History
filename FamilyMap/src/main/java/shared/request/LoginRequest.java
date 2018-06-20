package shared.request;

/**
 * Created by KevinTsou on 2/12/2018.
 */

public class LoginRequest {
    protected String userName;
    protected String password;

    /**
     * Login request with parameter and data
     * @param username
     * @param password
     */
    public LoginRequest(String username, String password)
    {
        this.userName=username;
        this.password=password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
