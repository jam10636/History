package Handler;
import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.io.*;

/**
 * Created by KevinTsou on 2/22/2018.
 */

public class DefaultHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String search= httpExchange.getRequestURI().toString();
        File file;
        String response="";
        if(search.equals("/"))
        {
            file=new File("web/index.html");
        }
        else
        {
            file=new File("web/"+search);
        }
        if(file.isFile())
        {

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            Files.copy(file.toPath(), httpExchange.getResponseBody());
        }
        else
        {
            file=new File("web/HTML/404.html");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            Files.copy(file.toPath(), httpExchange.getResponseBody());
            //httpExchange.getResponseBody().write(response.getBytes());

        }
        httpExchange.getResponseBody().close();
    }
}
