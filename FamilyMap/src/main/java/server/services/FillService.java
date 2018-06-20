package server.services;
import server.doa.UserDao;
import shared.result.*;
import server.doa.*;
import java.io.*;
import java.io.IOException;
import java.util.*;
import shared.model.*;
import com.google.gson.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class FillService {
    protected String userName;
    protected int generation=4;
    protected List<String> types = new ArrayList<String>() {{
        add("Death");
        add("Marriage");
        add("Baptism");
        add("Birth");
    }};

    /**
     * construct the FillService class with generation and username
     * @param username
     * @param generation
     */
        public FillService(String username, int generation)
        {
            this.userName=username;
            this.generation=generation;
        }
        public FillService(String userName)
        {
            this.userName=userName;
        }

    /**
     *Service function of FillService call
     * @return result of FillService
     */
    public FillResult refill() throws IOException {
            UserDao user=new UserDao();
            PersonDao person=new PersonDao();
            PersonMod Person=new PersonMod();
            EventDao event=new EventDao();
            String personID=user.getuserID(userName);
            Person=person.Person(personID);
            person.deleteuserrow(userName);
            event.deleteuserevent(userName);
            //Set spouse father mother to null
            Person.setSpouse(null);
            Person.setFather(null);
            Person.setMother(null);
            person.addperson(Person);
            double gen=0;
            ArrayList<String>generationID=new ArrayList<>();
            ArrayList<String>ancesterID=new ArrayList<>();
            if(generation<0||user.getuserID(userName)==null)
            {
                return null;
            }
            generationID.add(user.getuserID(userName));
            for(String t:types)
            {
                event.addevent(createevent(Person.getDescendant(),Person.getPersonID(),2028,t));
            }
            int genyear=1988;
            int personcount=0;
        for(int i=1;i<=generation;i++)
            {
                int size=generationID.size();
                for(int x=0;x<size;x++)
                    {
                        personcount+=2;
                        PersonMod father=createfather(userName);
                        PersonMod mother=creatermother(userName);
                        for(String t:types)
                        {
                            event.addevent(createevent(father.getDescendant(),father.getPersonID(),genyear,t));
                            event.addevent(createevent(mother.getDescendant(),mother.getPersonID(),genyear,t));
                        }
                        setspouse(father,mother);
                        ancesterID.add(father.getPersonID());
                        ancesterID.add(mother.getPersonID());
                        person.updateParent(generationID.get(x),father.getPersonID(),mother.getPersonID());
                        person.addperson(father);
                        person.addperson(mother);
                    }
                    generationID=new ArrayList<>(ancesterID);
                    ancesterID.clear();
                    genyear-=30;
            }
            FillResult result=new FillResult(personcount+1,(1+personcount)*4);
            return result;
        }
    public PersonMod createfather(String userName) throws IOException {
        Gson gson=new Gson();
        PersonMod father=new PersonMod();
        Reader reader = new FileReader("names/mnames.json");
        ReadNames readNames = gson.fromJson(reader, ReadNames.class);
        father.setGender("m");
        father.setDescendant(userName);
        father.setFirstName(readNames.getName());
        reader=new FileReader("names/snames.json");
        readNames=gson.fromJson(reader,ReadNames.class);
        father.setLastName(readNames.getName());
        father.setPersonID(UUID.randomUUID().toString());
        return father;
    }
    public PersonMod creatermother(String userName) throws IOException {
        Gson gson=new Gson();
        PersonMod mohter=new PersonMod();
        Reader reader = new FileReader("names/fnames.json");
        ReadNames readNames = gson.fromJson(reader, ReadNames.class);
        mohter.setGender("f");
        mohter.setDescendant(userName);
        mohter.setFirstName(readNames.getName());
        reader=new FileReader("names/snames.json");
        readNames=gson.fromJson(reader,ReadNames.class);
        mohter.setLastName(readNames.getName());
        mohter.setPersonID(UUID.randomUUID().toString());
        return mohter;
    }
    public void setspouse(PersonMod father,PersonMod mother)
    {
        father.setSpouse(mother.getPersonID());
        mother.setSpouse(father.getPersonID());
    }
    public EventMod createevent(String userName,String personID,int year,String type)throws IOException
    {
        Gson gson=new Gson();
        Reader reader = new FileReader("names/locations.json");
        ReadLocation location = gson.fromJson(reader, ReadLocation.class);
        location=location.getLocations();
        EventMod event=new EventMod(userName,personID,Double.toString(location.latitude),Double.toString(location.longitude),
                location.country,location.city,type,eventype(type,year));
        return event;
    }
    public String eventype(String type, int year)
    {
        int eventyear=year;
        if(type.equals("Birth"))
        {
            eventyear-=80;
        }
        else if(type.equals("Death"))
        {
            eventyear-=10;
        }
        else if(type.equals("Baptism"))
        {
            eventyear-=72;
        }
        else
        {
            eventyear-=50;
        }
        return String.valueOf(eventyear);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
