package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetFromApi {

    public static int getFromDirection(int squareNumber, Main.Dir d, String link) throws IOException {
        String sURL = link + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);
        JsonArray jArr = rootobj.get("links").getAsJsonArray();
        for (int i = 0; i < jArr.size(); i++) {
            JsonObject jObj = jArr.get(i).getAsJsonObject();
            if (jObj.get("direction").getAsString().equals(d.toString())) {
                return jObj.get("square").getAsInt();
            }
        }
        return -1;
    }


    public static int getNextOnBoard(int squareNumber, String link) throws IOException {
        String sURL = link + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);
        JsonArray jArr = rootobj.get("links").getAsJsonArray();

        JsonObject jObj = jArr.get(jArr.size() - 1).getAsJsonObject();

        int returnNumber = -1;
        returnNumber = jObj.get("square").getAsInt();
        if (rootobj.has("wormhole")) {
            returnNumber = rootobj.get("wormhole").getAsInt(); //just grab the zipcode
        }
        return returnNumber;
    }

    public static int getWormHole(int squareNumber, String link) throws IOException {
        String sURL = link + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);

        if (rootobj.has("wormhole")) {
            return rootobj.get("wormhole").getAsInt(); //just grab the zipcode
        }
        return -1;
    }

    public static Board getBoard(String link) throws IOException {
        JsonObject rootobj = getRootObject(link);

        //String zipcode = rootobj.get("id").getAsString(); //just grab the zipcode
        int dimX = rootobj.get("dimX").getAsInt(); //just grab the zipcode
        int dimY = rootobj.get("dimY").getAsInt(); //just grab the zipcode
        int start = rootobj.get("start").getAsInt(); //just grab the zipcode
        int goal = rootobj.get("goal").getAsInt(); //just grab the zipcode

        return new Board(dimX, dimY, start, goal);
    }

    private static JsonObject getRootObject(String URL) throws IOException {

        // Connect to the URL using java's native library
        URL url = new URL(URL);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        return root.getAsJsonObject(); //May be an array, may be an object.
    }
}
