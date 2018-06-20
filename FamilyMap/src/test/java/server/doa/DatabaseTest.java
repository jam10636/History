package server.doa;

import org.junit.Test;

/**
 * Created by KevinTsou on 2/20/2018.
 */
public class DatabaseTest {

    @Test
    public void test(){
        Database db=new Database();
        db.openConnection();
        db.closeConnection(true);
        assert true;
    }

}