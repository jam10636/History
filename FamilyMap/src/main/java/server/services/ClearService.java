package server.services;
import server.doa.Database;
import shared.result.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class ClearService {
    /**
     * Service function of ClearService call
     * @return the result of ClearResult
     */
    public ClearResult result()
    {
        String message;
        Database db=new Database();
        if(db.dropall())
        {
            message=new String("Clear succeeded.");
        }
        else
        {
            return null;
        }
        ClearResult result=new ClearResult(message);
        return result;
    }
}
