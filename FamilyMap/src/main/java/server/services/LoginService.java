package server.services;
import java.sql.SQLException;

import shared.request.*;
import shared.result.*;
import shared.model.*;
import server.doa.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class LoginService {
    /**
     *Service function of LoginService call
     * @param e
     * @return result of LoginService request
     */
    public LoginResult result(LoginRequest e)throws SQLException
    {
        UserDao user=new UserDao();
        String ID=user.login(e.getUsername(),e.getPassword());
        if(ID==null)
        {
            return null;
        }
        TokenDoa token=new TokenDoa();
        TokenMod tokenadded=new TokenMod();
        token.addtoken(tokenadded.getAuthtoken(),e.getUsername());
        LoginResult result=new LoginResult(e.getUsername(),ID,tokenadded.getAuthtoken());
        return result;
    }
}
