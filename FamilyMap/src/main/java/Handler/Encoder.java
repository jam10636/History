package Handler;
import com.google.gson.*;
import shared.result.*;

/**
 * Created by KevinTsou on 2/25/2018.
 */

public class Encoder {
    public String clearesponse(ClearResult in) {
        Gson gson = new Gson();
        return gson.toJson(in);
    }

    public String loadresponse(LoadResult in) {
        Gson gson = new Gson();
        return gson.toJson(in);
    }

    public String fillresponse(FillResult in) {
        Gson gson = new Gson();
        return gson.toJson(in);
    }

    public String peronsresponse(PeopleResult in) {
        Gson gson = new Gson();
        if(in.getPeople()!=null)
        {
            return gson.toJson(in);
        }
        else {
            return gson.toJson(in.getPersonMod());
        }
    }

    public String eventresponse(EventResult in) {
        Gson gson = new Gson();
        return gson.toJson(in);
    }

    public String loginresponse(LoginResult in) {
        Gson gson = new Gson();
        return gson.toJson(in);
    }

    public String registerespose(RegisterResult in) {
        Gson gson = new Gson();
        return gson.toJson(in);
    }
    public String errorMessage()
    {
        Gson gson=new Gson();
        return gson.toJson("Error");
    }
}
