package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private static String name;
    private static int position;

    public Player(String name) {
        this.name = name;
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public static boolean doNormalPlayerTurn(Board board, String API) throws IOException {
        int dieRoll = Game.rollDie();
        int nextPos = position + dieRoll;

        if (Rules.outOfBoardTest(board.getHighestNumber(), nextPos)) {
            nextPos = board.getHighestNumber() - 1;
        }

        if (GetFromApi.getWormHole((nextPos + 1), API) - 1 >= 0) {
            position = GetFromApi.getWormHole((nextPos + 1), API) - 1;
            System.out.println(name + " rolled a " + dieRoll + " and is now on Square " + (position + 1) + " because of a wormhole on Square " + (nextPos + 1));
        } else {
            position = nextPos;
            System.out.println(name + " rolled a " + dieRoll + " and is now on Square " + (position + 1));
        }

        if (position == board.getGoal() - 1) {
            return false;
        }
        return true;
    }

    public static boolean doSpacePlayerTurn(Board board, String API) throws IOException {
        Scanner sc = new Scanner(System.in);
        int dieRoll = Game.rollDie();

        ArrayList possibilities = Rules.findPossiblePaths(position + 1, dieRoll, API, board.getHighestNumber());
        System.out.println(name + " rolled a " + dieRoll + ". What direction do you want to walk? Type one of the directions: " + possibilities);
        String input = sc.nextLine();
        while (!possibilities.contains(input)) {
            System.out.println("Wrong input. Type one of the directions: " + possibilities);
            input = sc.nextLine();
        }

        walkXTimesInDir(dieRoll, input, API);
        if (GetFromApi.getWormHole(position + 1, API) - 1 >= 0) {
            position = GetFromApi.getWormHole(position + 1, API) - 1;
            System.out.println(name + " walked " + input + " " + dieRoll + " times, hit a Wormhole and is now standing on " + (position + 1));
        } else {
            System.out.println(name + " walked " + input + " " + dieRoll + " times and is now standing on Square " + (position + 1));
        }


        if (position == board.getGoal() - 1) {
            return false;
        }
        return true;
    }

    private static void walkXTimesInDir(int dieRoll, String dir, String API) throws IOException {
        while (dieRoll > 0) {
            System.out.println(dieRoll);
            position = GetFromApi.getFromDirection(position + 1, dir, API) - 1;
            dieRoll--;
        }

    }
}
