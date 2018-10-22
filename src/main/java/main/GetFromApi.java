package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class GetFromApi {

    /**
     * Method to get the next square in a specific direction
     *
     * @param squareNumber current square
     * @param dir          direction you want to go
     * @param URL          String API link
     * @return next square in dir direction from squareNumber
     */
    public static int getFromDirection(int squareNumber, String dir, String URL) {
        String sURL = URL + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);
        JsonArray jArr = rootobj.get("links").getAsJsonArray();
        for (int i = 0; i < jArr.size(); i++) {
            JsonObject jObj = jArr.get(i).getAsJsonObject();
            if (jObj.get("direction").getAsString().equals(dir)) {
                return jObj.get("square").getAsInt();
            }
        }
        return -1;
    }

    /**
     * Method to get the possible directions from current square
     *
     * @param squareNumber current square
     * @param URL          String API URL
     * @return List containing all possible directions
     */
    public static ArrayList<String> getDirectionPossibilities(int squareNumber, String URL) {
        String sURL = URL + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);

        JsonArray jArr = rootobj.get("links").getAsJsonArray();
        ArrayList<String> possibilities = new ArrayList<>();

        for (int i = 0; i < jArr.size(); i++) {
            JsonObject jObj = jArr.get(i).getAsJsonObject();
            possibilities.add(jObj.get("direction").getAsString());
        }
        return possibilities;
    }

    /**
     * Method to get the next square from current square
     *
     * @param squareNumber current square
     * @param URL          String API URL
     * @return next square from squareNumber
     */
    public static int getNextOnBoard(int squareNumber, String URL) {
        String sURL = URL + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);

        JsonArray jArr = rootobj.get("links").getAsJsonArray();
        JsonObject jObj = jArr.get(jArr.size() - 1).getAsJsonObject();

        int returnNumber = -1;

        if (rootobj.has("wormhole")) {
            returnNumber = rootobj.get("wormhole").getAsInt();
        } else {
            returnNumber = jObj.get("square").getAsInt();
        }
        return returnNumber;
    }

    /**
     * Method to get the wormhole if it exist
     *
     * @param squareNumber current square
     * @param URL          String API URL
     * @return returns -1 if no wormhole, else wormhole square
     */
    public static int getWormHole(int squareNumber, String URL) {
        String sURL = URL + "/" + squareNumber; //just a string
        JsonObject rootobj = getRootObject(sURL);

        if (rootobj.has("wormhole")) {
            return rootobj.get("wormhole").getAsInt(); //just grab the wormhole
        }
        return -1;
    }

    /**
     * Get the board from API
     *
     * @param URL String API URL
     * @return a new Board with info from API
     */
    public static Board getBoard(String URL) {
        JsonObject rootobj = getRootObject(URL);

        int dimX = rootobj.get("dimX").getAsInt(); //just grab the dimX
        int dimY = rootobj.get("dimY").getAsInt(); //just grab the dimY
        int start = rootobj.get("start").getAsInt(); //just grab the start
        int goal = rootobj.get("goal").getAsInt(); //just grab the goal

        return new Board(dimX, dimY, start, goal);
    }

    /**
     * Helper method to get the first JSON object from API URL
     *
     * @param URL String API URL
     * @return JSON object
     */
    private static JsonObject getRootObject(String URL) {
        // Connect to the URL using java's native library
        try {
            URL url = new URL(URL);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            return root.getAsJsonObject();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        //if it fails, return null
        return null;
    }
}
