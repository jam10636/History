package Handler;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.*;
import java.net.HttpURLConnection;
import java.io.*;
import server.doa.*;
import server.services.ClearService;
import shared.result.*;
/**
 * Created by KevinTsou on 2/22/2018.
 */

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
                ClearService service=new ClearService();
                ClearResult result=service.result();
                String response="";
                Encoder encode=new Encoder();
            if (result==null) {
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            }
            else
            {
                response=encode.clearesponse(result);
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            }
             httpExchange.getResponseBody().write(response.getBytes());
            httpExchange.getResponseBody().close();
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
