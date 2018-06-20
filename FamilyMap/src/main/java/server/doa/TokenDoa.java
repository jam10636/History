package server.doa;
import java.sql.*;

/**
 * Created by KevinTsou on 2/11/2018.
 */

public class TokenDoa {
    protected Database db=new Database();
    protected String creatable="create table if not exists authtoken" +
            "(" +
            "userName varchar(255) NOT NULL, " +
            "authtokens varchar(255) NOT NULL primary key" +
            ");";

    /**
     * Constructor for TokenDao Class
     */
    public TokenDoa()
    {
        db.openConnection();
        PreparedStatement stat=null;
        try {
            stat = db.conn.prepareStatement(creatable);
            stat.executeUpdate();

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        db.closeConnection(true);
    }
    /**
     *Function to add token into database
     * @param token
     * @param username
     * @return true if added
     */
    public boolean addtoken(String token,String username)
    {
        PreparedStatement stat=null;
        boolean added=false;
        String data = "insert into authtoken (userName, authtokens) values (?, ?)";
        db.openConnection();
        try
        {
            stat=db.conn.prepareStatement(data);
            stat.setString(1,username);
            stat.setString(2,token);
            stat.executeUpdate();
            added=true;
        }
        catch(SQLException e){
            e.printStackTrace();
    }
        finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        db.closeConnection(true);
        return added;
    }

    /**
     * Function to return a username with tokenID
     * @param token
     * @return specific username
     */
    public String reusername(String token)
    {
        PreparedStatement stat=null;
        ResultSet rs=null;
        String username=null;
        String gettoken = "select * from authtoken where authtokens = ?";
        db.openConnection();
        try
        {
            stat=db.conn.prepareStatement(gettoken);
            stat.setString(1,token);
            rs=stat.executeQuery();
            while(rs.next())
            {
                username=new String(rs.getString("userName"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        db.closeConnection(true);
        return username;
    }
}
