package shared.result;
import shared.model.*;
import java.util.*;
/**
 * Created by KevinTsou on 2/12/2018.
 */

public class EventResult {
    protected EventMod Event;
    protected ArrayList<EventMod>data;

    /**
     * Set and Return result of Event
     * @param Event
     */
    public EventResult(EventMod Event)
    {
        this.Event=Event;
    }
    public EventResult(ArrayList<EventMod>Eventslist)
    {
        this.data=Eventslist;
    }

    public EventMod getEvent() {
        return Event;
    }

    public void setEvent(EventMod event) {
        Event = event;
    }

    public ArrayList<EventMod> getEventslist() {
        return data;
    }

    public void setEventslist(ArrayList<EventMod> eventslist) {
        data = eventslist;
    }
}
