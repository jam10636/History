package server.doa;
import shared.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */
public class EventDao {
    protected String creatable="create table if not exists event" +
            "(" +
            "eventID varchar(255) NOT NULL primary key, " +
            "descendant varchar(255) NOT NULL, " +
            "personID varchar(255) NOT NULL, " +
            "latitude varchar(255) NOT NULL, " +
            "longitude varchar(255) NOT NULL, " +
            "country varchar(255) NOT NULL, "+
            "city varchar(255) not null, " +
            "eventType varchar(255) NOT NULL, " +
            "year varchar(255) NOT NULL" +
            ");";
    protected Database db=new Database();
    /**
     * Function to add EventMod into database
     * @return true if EventMod is added
     */
    public EventDao()
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
    public boolean addevent(EventMod e)
    {
        PreparedStatement stat=null;
        boolean added=false;
        db.openConnection();
        String data = "insert into event (eventID, descendant, personID, latitude, longitude, country, city, eventType, year)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stat = db.conn.prepareStatement(data);
            stat.setString(1, e.getEventID());
            stat.setString(2,e.getDescendant());
            stat.setString(3, e.getPersonID());
            stat.setString(4, e.getLatitude());
            stat.setString(5, e.getLongitude());
            stat.setString(6, e.getCountry());
            stat.setString(7, e.getCity());
            stat.setString(8, e.getEventType());
            stat.setString(9, e.getYear());
            stat.executeUpdate();
            added=true;
        } catch (SQLException x) {
            x.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                }
            } catch (SQLException x) {
                x.printStackTrace();
            }
        }
        db.closeConnection(true);
        return added;

    }

    /**
     * Function to return list of Event
     *@param userName
     * @return Array of even object
     */
    public ArrayList<EventMod> Event(String userName)
    {
        PreparedStatement stmt=null;
        db.openConnection();
        String find ="SELECT * FROM event WHERE " +
                "descendant = ?";
        ResultSet rs=null;
        ArrayList<EventMod>events=new ArrayList<>();
        try {
            stmt=db.conn.prepareStatement(find);
            stmt.setString(1,userName);
            rs=stmt.executeQuery();
            EventMod event=new EventMod();
                while(rs.next())
                {
                    event=new EventMod();
                    event.setEventID(rs.getString("eventID"));
                    event.setDescendant(rs.getString("descendant"));
                    event.setPersonID(rs.getString("personID"));
                    event.setLatitude(rs.getString("latitude"));
                    event.setLongitude(rs.getString("longitude"));
                    event.setCountry(rs.getString("country"));
                    event.setCity(rs.getString("city"));
                    event.setEventType(rs.getString("eventType"));
                    event.setYear(rs.getString("year"));
                    events.add(event);
                }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
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
        return events;
    }

    /**
     * Function to return specific event
     * @param eventID
     * @return return specific EventResult with specfic eventid
     */
    public EventMod singleevent(String eventID)
    {
        String find ="SELECT * FROM event WHERE " +
                "eventID = ?";
        ResultSet rs=null;
        PreparedStatement stat=null;
        db.openConnection();
        EventMod event=new EventMod();
        try
        {
            stat=db.conn.prepareStatement(find);
            stat.setString(1,eventID);
            rs=stat.executeQuery();
            while(rs.next())
            {
                event.setEventID(eventID);
                event.setDescendant(rs.getString("descendant"));
                event.setPersonID(rs.getString("personID"));
                event.setLatitude(rs.getString("latitude"));
                event.setLongitude(rs.getString("longitude"));
                event.setCountry(rs.getString("country"));
                event.setCity(rs.getString("city"));
                event.setEventType(rs.getString("eventType"));
                event.setYear(rs.getString("year"));
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
        return event;
    }

    /**
     * delete EventMod row for specific UserMod
     * @param userName
     */
    public void deleteuserevent(String userName)
    {
        String find ="delete FROM event WHERE " +
                "descendant = ?";
        ResultSet rs=null;
        PreparedStatement stat=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,userName);
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
    public String getuserName(String eventID)
    {
        ResultSet rs=null;
        PreparedStatement stat=null;
        String find="SELECT * from event WHERE eventID = ?";
        boolean exist=false;
        String ID=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,eventID);
            rs= stat.executeQuery();
            while(rs.next())
            {
                ID=rs.getString("descendant");
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
    public String geteventID(String userName)
    {
        ResultSet rs=null;
        PreparedStatement stat=null;
        String find="SELECT * from event WHERE descendant = ?";
        String ID=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,userName);
            rs= stat.executeQuery();
            while(rs.next())
            {
                ID=rs.getString("eventID");
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
