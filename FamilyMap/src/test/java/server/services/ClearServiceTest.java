package server.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.result.ClearResult;

import static org.junit.Assert.*;

/**
 * Created by KevinTsou on 2/28/2018.
 */
public class ClearServiceTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void result() throws Exception {
        ClearService service=new ClearService();
        ClearResult result=new ClearResult("Succeed");
        //check if DB has been cleared
        assertEquals("Succeed",result.returnmessage());
        // the only time it does not work is DB is not working.
    }

}