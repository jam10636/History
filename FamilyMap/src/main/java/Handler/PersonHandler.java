package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.*;
import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.services.PersonService;
import shared.result.PeopleResult;
import com.google.gson.*;
/**
 * Created by KevinTsou on 2/22/2018.
 */

public class PersonHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        String response="";
        if (reqHeaders.containsKey("Authorization")) {
            String authToken = reqHeaders.getFirst("Authorization");
            String[] url = parse(httpExchange);
            InputStream reqBody = httpExchange.getRequestBody();
            PersonService service = null;
            PeopleResult result = null;
            Gson gson=new Gson();
            Encoder encoder=new Encoder();
            if (url.length==2) {
                service = new PersonService(authToken);
                result = service.result();
            } else if(url.length==3) {
                service = new PersonService(authToken, url[2]);
                result = service.result();
            }
            else{
                result=null;
            }
            if (result==null)
            {
                response=gson.toJson("Error");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            }
            else
            {
                response=encoder.peronsresponse(result);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.getResponseBody().close();
        }
    }
    public String[] parse(HttpExchange httpExchange)
    {
        String[] result = new String[20];
       result= httpExchange.getRequestURI().toString().split("/");
       return result;
    }
}