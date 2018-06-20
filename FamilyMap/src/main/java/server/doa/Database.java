package server.doa;
import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by KevinTsou on 2/18/2018.
 */

public class Database {
    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected Connection conn;
    public void openConnection()  {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMap.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection(boolean commit)  {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean dropall()
    {
        Database db = new Database();
        PreparedStatement stat = null;
        boolean isDeleted=true;
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
                isDeleted=false;
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
        return isDeleted;
    }
}
