package Game;

import main.GetFromApi;

import java.util.ArrayList;

public class Rules {

    /**
     * Checks if the position you are trying to go to is out of bounds
     * @param maxValue max value on the board
     * @param tryPos position you want to check on
     * @return true if tryPos is higher else false
     */
    public static boolean outOfBoardTest(int maxValue, int tryPos) {
        if (tryPos > maxValue)
            return true;
        return false;
    }

    /**
     * Check if you can walk in a specific direction from current square n-times
     * @param dir direction you want to test
     * @param square current square
     * @param times how many times you want to walk that direction
     * @param API String API URL
     * @param maxSquare the highest square on the board
     * @return true if you can go direction n-times, else false
     */
    public static boolean canWalkThisWay(String dir, int square, int times, String API, int maxSquare) {
        int currPos = square;
        for (int i = 0; i < times; i++) {
            if (outOfBoardTest(maxSquare, currPos + i)) {
                return false;
            }
            if (GetFromApi.getFromDirection(currPos + i, dir, API) == -1) {
                return false;
            }
            currPos = GetFromApi.getFromDirection(currPos + i, dir, API) - 1;
        }
        return true;
    }

    /**
     * Find all possible paths it's legal to go from a list of directions n-times
     * @param square current square
     * @param times how many times you want to walk that direction
     * @param API String API URL
     * @param maxSquare the highest square on the board
     * @return list with possible directions
     */
    public static ArrayList<String> findPossiblePaths(int square, int times, String API, int maxSquare) {
        ArrayList<String> possibilities = GetFromApi.getDirectionPossibilities(square, API);
        ArrayList<String> possible = new ArrayList<>();

        for (String dir : possibilities) {
            if (canWalkThisWay(dir, square, times, API, maxSquare))
                possible.add(dir);
        }

        return possible;
    }

}
