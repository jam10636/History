package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.*;
import java.sql.SQLException;

import server.services.LoginService;
import server.services.RegisterService;
import shared.request.LoginRequest;
import shared.request.RegisterRequest;
import shared.result.LoginResult;
import shared.result.RegisterResult;
import com.google.gson.*;
/**
 * Created by KevinTsou on 2/22/2018.
 */

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            //make sure to check for the gender!
            InputStream reqBody = httpExchange.getRequestBody();
            Decoder decode = new Decoder();
            Encoder encoder=new Encoder();
            RegisterRequest req = decode.registerRequest(reqBody);
            RegisterService service = new RegisterService();
            RegisterResult result = service.result(req);
            Gson gson=new Gson();
            String response="";
            if (result==null) {
                response=gson.toJson("Error");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            }
            else
            {
                response=encoder.registerespose(result);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.getResponseBody().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
