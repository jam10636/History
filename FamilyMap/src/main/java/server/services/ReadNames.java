package server.services;

/**
 * Created by KevinTsou on 2/24/2018.
 */
import java.util.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.JsonArray;
public class ReadNames {
    protected ArrayList<String> data;

    public String getName() {
        Random rand = new Random();
        int number = rand.nextInt(data.size() - 1) + 0;
        return data.get(number);
    }
}
