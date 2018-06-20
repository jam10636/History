package server.doa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import shared.model.*;
/**
 * Created by KevinTsou on 2/28/2018.
 */
public class TokenDoaTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addtoken() throws Exception {
        TokenDoa Doa=new TokenDoa();
        TokenMod tokenMod=new TokenMod();
        tokenMod.setAuthtoken("onedollar");
        //Adding token for first time
        assertTrue(Doa.addtoken(tokenMod.getAuthtoken(),"jam10636"));
        //Adding token with same token
        assertFalse(Doa.addtoken(tokenMod.getAuthtoken(),"jam10636"));
        //Trying to add with missing value
        assertFalse(Doa.addtoken(tokenMod.getAuthtoken(),null));
        //Trying to add with missing value
        assertFalse(Doa.addtoken(null,"jam10636"));
    }
    @Test
    public void getUsername() throws Exception{
        TokenDoa Doa=new TokenDoa();
        //Test it by inserting data into db straight up
        assertEquals("jam10636",Doa.reusername("onedollar"));
        //Wrong token number will return null
        assertEquals(null,Doa.reusername("69a42952-61d4-4b4a-aac1-85a0f31fdd32asdasd"));
        Database db=new Database();
        db.dropall();
    }


}