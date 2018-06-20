package shared.result;

/**
 * Created by KevinTsou on 2/22/2018.
 */

public class FillResult {
    protected String message;
    public FillResult(int persons,int events)
    {
        message="Successfuly added "+persons+" and "+events+" events to database";
    }
    public String getmessage()
    {
        return message;
    }
}
