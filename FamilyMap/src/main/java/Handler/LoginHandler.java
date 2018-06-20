package Handler;

/**
 * Created by KevinTsou on 2/22/2018.
 */
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import server.services.PersonService;
import shared.request.*;
import shared.result.*;
import server.services.LoginService;
import shared.request.LoginRequest;

public class LoginHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            Headers reqHeaders = httpExchange.getRequestHeaders();
            String response = "";
            InputStream reqBody = httpExchange.getRequestBody();
            Decoder decoder = new Decoder();
            Encoder encoder = new Encoder();
            LoginRequest request = decoder.Loginrequest(reqBody);
            LoginService service = new LoginService();
            LoginResult result = null;
            result = service.result(request);
            if (result == null) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                response = encoder.errorMessage();
            } else {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                response = encoder.loginresponse(result);
            }
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.getResponseBody().close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public String[] parse(HttpExchange httpExchange)
    {
        String[] result = new String[20];
        result= httpExchange.getRequestURI().toString().split("/");
        return result;
    }
}

