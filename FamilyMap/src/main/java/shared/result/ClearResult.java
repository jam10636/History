package shared.result;

/**
 * Created by KevinTsou on 2/13/2018.
 */

public class ClearResult {
    String message;
    public ClearResult(String in)
    {
        this.message=in;
    }
    /**
     * Set and return message of success or failuer for clearresult.
     * @return message if it succeed or it fails
     */
    public String returnmessage()
    {
        return message;
    }
}
