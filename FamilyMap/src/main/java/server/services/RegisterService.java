package server.services;
import java.io.IOException;
import java.sql.SQLException;

import server.doa.PersonDao;
import server.doa.TokenDoa;
import server.doa.UserDao;
import shared.model.PersonMod;
import shared.model.TokenMod;
import shared.model.UserMod;
import shared.result.*;
import shared.request.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class RegisterService {
    /**
     *Service function of RegisterService call
     * @param e
     * @return result of RegisterService
     */
    public RegisterResult result(RegisterRequest e) throws SQLException {
        UserDao user = new UserDao();
        RegisterResult result;
        UserMod users = new UserMod(e.getUsername(), e.getPassword(), e.getEmail(), e.getFirstname(), e.getLastname(), e.getGender());
        if(!e.getGender().equals("f")&&!e.getGender().equals("m"))
        {
            return null;
        }
        if (user.checkdup(e.getUsername())||!user.adduser(users)) {
            return null;
        }
        TokenMod token = new TokenMod();
        TokenDoa t = new TokenDoa();
        t.addtoken(token.getAuthtoken(), e.getUsername());
        PersonMod person = new PersonMod(users);
        PersonDao pe = new PersonDao();
        pe.addperson(person);
        FillService f=new FillService(e.getUsername(),4);
        try {
            f.refill();
        }
        catch(IOException a)
        {
            a.printStackTrace();
        }
        result = new RegisterResult(e.getUsername(), users.getPersonID(), token.getAuthtoken());
        return result;
    }
}
