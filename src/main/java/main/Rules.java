package main;

import java.util.ArrayList;

public class Rules {
    public static boolean outOfBoardTest(int maxValue, int tryPos) {
        if (tryPos >= maxValue)
            return true;
        return false;
    }

    public static boolean canWalkThisWay(String dir, int square, int die, String API, int maxSquare) {
        int currPos = square;
        for (int i = 0; i < die; i++) {
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

    public static ArrayList<String> findPossiblePaths(int square, int die, String API, int maxSquare) {
        ArrayList<String> possibilities = GetFromApi.getDirectionPossibilities(square, API);
        ArrayList<String> possible = new ArrayList<>();

        for (String dir : possibilities) {
            if (canWalkThisWay(dir, square, die, API, maxSquare))
                possible.add(dir);
        }

        return possible;
    }

}
