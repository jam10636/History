package Handler;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import server.services.FillService;
import server.services.LoadService;
import shared.request.LoadRequest;
import shared.result.FillResult;
import shared.result.LoadResult;

/**
 * Created by KevinTsou on 2/22/2018.
 */

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try{
        Headers reqHeaders = httpExchange.getRequestHeaders();
        String response = "";
        String[] url = parse(httpExchange);
        InputStream reqBody = httpExchange.getRequestBody();
        LoadService service = new LoadService();
        LoadRequest request=null;
        LoadResult result = null;
        Encoder encoder = new Encoder();
        Decoder decoder=new Decoder();
        result = service.reload(decoder.loadRequest(reqBody));
            if (result == null) {
                response = encoder.errorMessage();
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            } else {
                response = encoder.loadresponse(result);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.getResponseBody().close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String[] parse(HttpExchange httpExchange) {
        String[] result = new String[20];
        result = httpExchange.getRequestURI().toString().split("/");
        return result;
    }
}
