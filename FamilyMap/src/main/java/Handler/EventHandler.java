package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import server.services.EventService;
import server.services.LoadService;
import server.services.PersonService;
import shared.request.LoadRequest;
import shared.result.EventResult;
import shared.result.LoadResult;
import shared.result.PeopleResult;

/**
 * Created by KevinTsou on 2/22/2018.
 */

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        String response="";
        if (reqHeaders.containsKey("Authorization")) {
            String authToken = reqHeaders.getFirst("Authorization");
            String[] url = parse(httpExchange);
            InputStream reqBody = httpExchange.getRequestBody();
            EventService service = null;
            EventResult result = null;
            Encoder encoder=new Encoder();
            if (url.length == 2) {
                service = new EventService(authToken);
                result = service.result();
            } else if (url.length == 3) {
                service = new EventService(authToken, url[2]);
                result = service.result();
            } else {
                result = null;
            }
            if (result==null)
            {
                response=encoder.errorMessage();
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            }
            else
            {
                response=encoder.eventresponse(result);
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
