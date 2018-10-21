package main;

public class Rules {
    public static boolean outOfBoardTest(int maxValue, int tryPos) {
        if (tryPos >= maxValue)
            return true;
        return false;
    }

}
