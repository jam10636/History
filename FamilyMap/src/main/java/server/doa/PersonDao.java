package server.doa;
import java.sql.*;
import java.util.*;
import shared.model.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */
import shared.model.*;
public class PersonDao {
    /**
     * Function to add PersonMod into database
     * @param Person
     * @return true if a PersonMod has been added, false otherwise
     */
    protected Database db=new Database();
    protected String creatable="create table if not exists person" +
            "(" +
            "descendant varchar(255) NOT NULL, " +
            "personID varchar(255) NOT NULL primary key, " +
            "firstName varchar(255) NOT NULL, " +
            "lastName varchar(255) NOT NULL, " +
            "gender varchar(255) NOT NULL, " +
            "father varchar(255), " +
            "mother varchar(255), " +
            "spouse varchar(255) " +
            ");";

    /**
     * /**
     * Constructor for PersonDao Class
     */
    public PersonDao()
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
    public boolean addperson(PersonMod PersonMod)
    {
        db.openConnection();
        PreparedStatement stat=null;
        boolean added=false;
        String data = "insert into person (descendant, personID, firstName, lastName, Gender, father, mother, spouse)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stat = db.conn.prepareStatement(data);
            stat.setString(1, PersonMod.getDescendant());
            stat.setString(2, PersonMod.getPersonID());
            stat.setString(3, PersonMod.getFirstName());
            stat.setString(4, PersonMod.getLastName());
            stat.setString(5, PersonMod.getGender());
            stat.setString(6, PersonMod.getFather());
            stat.setString(7, PersonMod.getMother());
            stat.setString(8, PersonMod.getSpouse());
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
     * Function to return a list of people
     *@param userName
     * @return list of persons
     */
    public ArrayList<PersonMod>people(String userName)
    {
        PreparedStatement stmt=null;
        db.openConnection();
        String find ="SELECT * FROM person WHERE " +
                "descendant = ?";
        ResultSet rs=null;
        ArrayList<PersonMod>peoples=new ArrayList<>();
        try {
            stmt=db.conn.prepareStatement(find);
            stmt.setString(1,userName);
                rs=stmt.executeQuery();
                PersonMod p=new PersonMod();
                while(rs.next())
                {
                    p=new PersonMod();
                    p.setDescendant(rs.getString("descendant"));
                    p.setPersonID(rs.getString("personID"));
                    p.setFirstName(rs.getString("firstName"));
                    p.setLastName(rs.getString("lastName"));
                    p.setGender(rs.getString("gender"));
                    p.setFather(rs.getString("father"));
                    p.setMother(rs.getString("mother"));
                    p.setSpouse(rs.getString("spouse"));
                    peoples.add(p);
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
        return peoples;

    }

    /**
     * Function to return specific person
     * @param personID
     * @return specfic persons information
     */
    public PersonMod Person(String personID)
    {
        String find ="SELECT * FROM person WHERE " +
                "personID = ?";
        ResultSet rs=null;
        String userid=null;
        PreparedStatement stat=null;
        db.openConnection();
        PersonMod p=new PersonMod();
        try
        {
            stat=db.conn.prepareStatement(find);
            stat.setString(1,personID);
            rs=stat.executeQuery();
            while(rs.next())
            {
                p.setDescendant(rs.getString("descendant"));
                p.setPersonID(rs.getString("personID"));
                p.setFirstName(rs.getString("firstName"));
                p.setLastName(rs.getString("lastName"));
                p.setGender(rs.getString("gender"));
                p.setFather(rs.getString("father"));
                p.setMother(rs.getString("mother"));
                p.setSpouse(rs.getString("spouse"));
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
        return p;
    }

    /**
     * Delete whole row with specfic userrow
     * @param personID
     */
    public String getuserName(String personID)
    {
        ResultSet rs=null;
        PreparedStatement stat=null;
        String find="SELECT * from person WHERE personID = ?";
        boolean exist=false;
        String ID=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,personID);
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
    public void deleteuserrow(String userName)
    {
        String find ="DELETE FROM person WHERE " +
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
    public void updateParent(String personID,String fatherID,String motherID)
    {
        String find="update person set father = ?, mother = ? where personID = ?";
        PreparedStatement stat=null;
        db.openConnection();
        try{
            stat=db.conn.prepareStatement(find);
            stat.setString(1,fatherID);
            stat.setString(2,motherID);
            stat.setString(3,personID);
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
}
