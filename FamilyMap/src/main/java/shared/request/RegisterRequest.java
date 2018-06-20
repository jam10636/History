package shared.request;

/**
 * Created by KevinTsou on 2/12/2018.
 */

public class RegisterRequest {
    protected String userName;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected String gender;

    /**
     * Register request with parameter and data
     * @param username
     * @param password
     * @param email
     * @param firstname
     * @param lastname
     * @param gender
     */
    public RegisterRequest(String username, String password, String email, String firstname, String lastname, String gender)
    {
        this.userName=username;
        this.password=password;
        this.email=email;
        this.firstName=firstname;
        this.lastName=lastname;
        this.gender=gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
