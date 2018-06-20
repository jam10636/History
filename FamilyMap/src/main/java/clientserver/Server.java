package clientserver;

/**
 * Created by KevinTsou on 2/25/2018.
 */
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import Handler.ClearHandler;
import Handler.DefaultHandler;
import Handler.EventHandler;
import Handler.FillHandler;
import Handler.LoadHandler;
import Handler.LoginHandler;
import Handler.PersonHandler;
import Handler.RegisterHandler;
import server.doa.Database;
import server.doa.EventDao;
import server.doa.PersonDao;
import server.doa.TokenDoa;
import server.doa.UserDao;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        UserDao u = new UserDao();
        server.setExecutor(null);
        System.out.println("Creating contexts");
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login",  new LoginHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load",new LoadHandler());
        server.createContext("/person",new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/",new DefaultHandler());
        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    // "main" method for the server program
    // "args" should contain one command-line argument, which is the port number
    // on which the server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
