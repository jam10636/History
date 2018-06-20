package shared.model;
import java.util.*;
/**
 * Created by KevinTsou on 2/11/2018.
 */

public class EventMod {
    private String descendant;
    private String eventID;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String eventType;
    private String year;

    /**
     * setting EventService information
     * @param des
     *
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public EventMod(String des, String personID, String latitude, String longitude, String country, String city, String eventType, String year)
    {
        this.descendant=des;
        this.eventID=UUID.randomUUID().toString();
        this.personID=personID;
        this.latitude=latitude;
        this.longitude=longitude;
        this.country=country;
        this.city=city;
        this.eventType=eventType;
        this.year=year;
    }
    public EventMod(EventMod e)
    {
        this.descendant=e.getDescendant();
        this.eventID=e.getEventID();
        this.personID=e.getPersonID();
        this.latitude=e.getLatitude();
        this.longitude=e.getLongitude();
        this.country=e.getCountry();
        this.city=e.getCity();
        this.eventType=e.getEventType();
        this.year=e.year;
    }
    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public EventMod()
    {};
}
