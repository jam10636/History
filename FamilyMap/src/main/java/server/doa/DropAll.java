package server.doa;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
/**
 * Created by KevinTsou on 2/20/2018.
 */

public class DropAll {
    public DropAll() {
        Database db = new Database();
        PreparedStatement stat = null;
        db.openConnection();
        Set<String> drop = new TreeSet<>();
        drop.add("users");
        drop.add("person");
        drop.add("event");
        drop.add("authtoken");
        for (int i = 0; i < 4; i++) {
            StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS " + drop.toArray()[i]);
            try {
                stat = db.conn.prepareStatement(sb.toString());
                stat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        db.closeConnection(true);
    }
}
