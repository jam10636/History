package server.doa;
import shared.model.UserMod;
import java.sql.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class UserDao {
    private Database db=new Database();
    protected String creatable="create table if not exists users" +
            "(" +
            "userName varchar(255) NOT NULL primary key, " +
            "password varchar(255) NOT NULL, " +
            "email varchar(255) NOT NULL, " +
            "firstName varchar(255) NOT NULL, " +
            "lastName varchar(255) NOT NULL, " +
            "gender varchar(255) NOT NULL, " +
            "personID varchar(255) NOT NULL" +
            ");";

    /**
     * Constructor for UserDao Class
     */
    public UserDao()
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
     * Function to add UserMod into database
     * @param UserMod
     * @return true if UserMod is added
     */
    public boolean adduser(UserMod UserMod)throws SQLException {
        PreparedStatement stat=null;
        boolean added=false;
        db.openConnection();
        String data = "insert into users (userName, password, email, firstName, lastName, Gender, personID)" + " values (?, ?, ?, ?, ?, ?, ?)";
        try {
            stat = db.conn.prepareStatement(data);
            stat.setString(1, UserMod.getUserName());
            stat.setString(2, UserMod.getPassword());
            stat.setString(3, UserMod.getEmail());
            stat.setString(4, UserMod.getFirstName());
            stat.setString(5, UserMod.getLastName());
            stat.setString(6, UserMod.getGender());
            stat.setString(7, UserMod.getPersonID());
            stat.executeUpdate();
            added=true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
     * Function to LoginService for UserMod
     * @param userName
     * @return personID
     */
    public String login(String userName,String password) throws SQLException
    {
         String find ="SELECT * FROM users WHERE " +
                 "userName = ? "+
                 "and password = ?";
        ResultSet rs=null;
        String userid=null;
        PreparedStatement stat=null;
        db.openConnection();
        try
        {
            stat=db.conn.prepareStatement(find);
            stat.setString(1,userName);
            stat.setString(2,password);
            rs=stat.executeQuery();
            while(rs.next())
            {
                userid=new String(rs.getString("personID"));
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
        return userid;
    }

    /**
     * Check duplicate
     * @param userName
     * @return if true if it has been already registered
     */
    public boolean checkdup(String userName)
    {
        ResultSet rs=null;
        PreparedStatement stat=null;
        String find="SELECT * from users WHERE userName = ?";
        boolean exist=false;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,userName);
            rs= stat.executeQuery();
            if(rs.next())
            {
                    exist = true;
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
        return exist;
    }
    public String getuserName(String personID)
    {
        ResultSet rs=null;
        PreparedStatement stat=null;
        String find="SELECT * from users WHERE personID = ?";
        boolean exist=false;
        String ID=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,personID);
            rs= stat.executeQuery();
            while(rs.next())
            {
                ID=rs.getString("userName");
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
        return ID;
    }
    public String getuserID(String username)
    {
        ResultSet rs=null;
        PreparedStatement stat=null;
        String find="SELECT * from users WHERE userName = ?";
        boolean exist=false;
        String ID=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,username);
            rs= stat.executeQuery();
            while(rs.next())
            {
                ID=rs.getString("personID");
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
        return ID;
    }
}
