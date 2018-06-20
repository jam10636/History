package com.example.kevintsou.image.proxy;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.*;

import Handler.Decoder;
import clientserver.Server;
import shared.model.PersonMod;
import shared.model.UserMod;
import shared.request.LoginRequest;
import shared.request.RegisterRequest;
import shared.result.EventResult;
import shared.result.LoginResult;
import shared.result.PeopleResult;
import shared.result.RegisterResult;

/**
 * Created by KevinTsou on 3/13/2018.
 */

public class Proxy {
    public LoginResult loginresult(String host, String serverport, LoginRequest request) throws IOException
    {
        Gson gson=new Gson();
        LoginResult result=null;
        URL url=new URL("http://" + host + ":" + serverport + "/user/login");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
        String req=gson.toJson(request);
        wr.write(req);
        wr.close();
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream responseBody = connection.getInputStream();
            gson=new Gson();
            Reader reader = new InputStreamReader(responseBody);
            result = gson.fromJson(reader, LoginResult.class);
        }
        return result;
    }
    public RegisterResult registerResult(String host, String serverport, RegisterRequest request)throws IOException
    {
        RegisterResult result=null;
        Gson gson=new Gson();
        URL url=new URL("http://" + host + ":" + serverport + "/user/register");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
        String req=gson.toJson(request);
        Log.v("h",req);
        wr.write(req);
        wr.close();
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream responseBody = connection.getInputStream();
            gson=new Gson();
            Reader reader = new InputStreamReader(responseBody);
            result = gson.fromJson(reader, RegisterResult.class);
        }
        return result;
    }
    public PeopleResult personResult(String host,String serverport,String Authtoken,String ID) throws IOException {
        PeopleResult result=null;
        Gson gson=new Gson();
        URL url=new URL("http://" + host + ":" + serverport + "/person/"+ID);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty ("Authorization", Authtoken);
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream responseBody = connection.getInputStream();
            gson=new Gson();
            Reader reader = new InputStreamReader(responseBody);
            PersonMod personMod = gson.fromJson(reader, PersonMod.class);
            result=new PeopleResult(personMod);
        }
        return result;
    }
    public PeopleResult peopleResult(String host,String serverport,String Authtoken) throws IOException
    {
        PeopleResult result=null;
        Gson gson=new Gson();
        URL url=new URL("http://" + host + ":" + serverport + "/person");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty ("Authorization", Authtoken);
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream responseBody = connection.getInputStream();
            gson=new Gson();
            Reader reader = new InputStreamReader(responseBody);
            result = gson.fromJson(reader, PeopleResult.class);
        }
        return result;
    }
    public EventResult eventResult(String host,String serverport,String Authtoken) throws  IOException
    {
        EventResult result=null;
        Gson gson=new Gson();
        URL url=new URL("http://" + host + ":" + serverport + "/event");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty ("Authorization", Authtoken);
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
        {
            InputStream responseBody = connection.getInputStream();
            gson=new Gson();
            Reader reader = new InputStreamReader(responseBody);
            result = gson.fromJson(reader, EventResult.class);
        }
        return result;
    }
}
