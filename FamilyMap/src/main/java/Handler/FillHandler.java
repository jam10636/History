package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import server.services.FillService;
import server.services.PersonService;
import shared.result.FillResult;
import shared.result.PeopleResult;

/**
 * Created by KevinTsou on 2/22/2018.
 */

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers reqHeaders = httpExchange.getRequestHeaders();
        String response = "";
        String[] url = parse(httpExchange);
        InputStream reqBody = httpExchange.getRequestBody();
        FillService service = null;
        FillResult result = null;
        Encoder encoder = new Encoder();
        if (url.length == 3) {
            service = new FillService(url[2]);
            result = service.refill();
        } else if (url.length == 4) {
            service = new FillService(url[2], Integer.valueOf(url[3]));
            result = service.refill();
        } else {
            result = null;
        }
        if (result == null) {
            response = encoder.errorMessage();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
        } else {
            response = encoder.fillresponse(result);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        httpExchange.getResponseBody().write(response.getBytes());
        httpExchange.getResponseBody().close();
    }

    public String[] parse(HttpExchange httpExchange) {
        String[] result = new String[20];
        result = httpExchange.getRequestURI().toString().split("/");
        return result;
    }
}
