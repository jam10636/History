package shared.result;
import java.util.*;
import shared.model.*;
/**
 * Created by KevinTsou on 2/12/2018.
 */

public class PeopleResult {
    protected ArrayList<PersonMod>data;
    protected PersonMod personMod;

    /**
     * set list of people result
     * @param people
     */
    public PeopleResult(ArrayList<PersonMod>people)
    {
        this.data=people;
    }

    /**
     * Set specific personresult
     * @param PersonMod
     */
    public PeopleResult(PersonMod PersonMod)
    {
        this.personMod=PersonMod;
    }
    public PersonMod getPersonMod()
    {
        return personMod;
    }

    public ArrayList<PersonMod> getPeople() {
        return data;
    }

    public void setPeople(ArrayList<PersonMod> people) {
        this.data = people;
    }

    //public void setPersonMod(PersonMod personMod) {
        //this.personMod = personMod;
    //}
}
