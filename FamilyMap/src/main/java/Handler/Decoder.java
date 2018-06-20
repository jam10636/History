package Handler;

import shared.model.UserMod;
import shared.request.LoadRequest;
import shared.request.LoginRequest;
import shared.request.RegisterRequest;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
/**
 * Created by KevinTsou on 2/25/2018.
 */

public class Decoder {
    public LoginRequest Loginrequest(InputStream req)
    {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(req);
        UserMod user = gson.fromJson(reader, UserMod.class);
        LoginRequest request=new LoginRequest(user.getUserName(),user.getPassword());
        return request;
    }
    public RegisterRequest registerRequest(InputStream req)
    {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(req);
        UserMod user = gson.fromJson(reader, UserMod.class);
        RegisterRequest request=new RegisterRequest(user.getUserName(),user.getPassword(),user.getEmail(),user.getFirstName(),user.getLastName(),user.getGender());
        return request;
    }
    public LoadRequest loadRequest(InputStream req)
    {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(req);
        LoadRequest request = gson.fromJson(reader, LoadRequest.class);
        return request;
    }
}
