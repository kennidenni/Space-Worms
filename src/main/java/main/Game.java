package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static String API;
    private static Scanner sc = new Scanner(System.in);
    private static Board mainBoard;

    public static void playGame(String boardLink, Board board) throws IOException {
        API = boardLink;
        mainBoard = board;

        System.out.println("\nInitialize  game...");
        ArrayList<Player> playerList = new ArrayList<>();

        boolean spaceRules = setupForGame(playerList);

        System.out.println("\nStarting game...");
        boolean running = true;
        int round = 0;
        Player lastPlayer;
        while (running) {
            round++;
            mainBoard.printBoard(API);
            System.out.println("Round: " + round + "\n");
            printPlayerPosition(playerList);
            for (Player p : playerList) {
                lastPlayer = p;
                System.out.println(p.getName() + "'s turn. Press Enter to roll a die");
                sc.nextLine();
                if (!spaceRules)
                    running = doNormalPlayerTurn(p, board);
                else
                    running = doSpacePlayerTurn(p, board);
            }
        }

        System.out.println(lastPlayer.getName() + " won the game after " + round + " rounds!");
    }

    private static boolean doSpacePlayerTurn(Player p, Board board) {
        int dieRoll = rollDie();


        return true;
    }

    private static boolean doNormalPlayerTurn(Player p, Board board) throws IOException {
        int dieRoll = rollDie();
        int nextPos = p.getPosition()+ dieRoll;

        if (Rules.outOfBoardTest(board.getHighestNumber(), nextPos)) {
            nextPos = board.getHighestNumber()-1;
        }

        if (GetFromApi.getWormHole((nextPos +1), API) - 1 >= 0) {
            p.setPosition(GetFromApi.getWormHole((nextPos+ 1), API) - 1);
            System.out.println(p.getName() + " rolled a " + dieRoll + " and is now on Square " + (p.getPosition() + 1) + " because of a wormhole");
        } else {
            p.setPosition(nextPos);
            System.out.println(p.getName() + " rolled a " + dieRoll + " and is now on Square " + (p.getPosition() + 1));
        }

        if (p.getPosition() == board.getGoal() - 1) {
            return false;
        }
        return true;
    }


    private static void printPlayerPosition(ArrayList<Player> playerList) {
        String output = "";
        for (Player p : playerList) {
            output += (p.getName() + " is on Square: " + (p.getPosition() + 1) + ", ");
        }
        System.out.println(output);
    }

    private static boolean setupForGame(ArrayList playerList) {
        addPlayerToList(playerList);
        System.out.println("Do you want normal snakes-and-latter rules or space rules? (Space=1 / Normal=any number");
        String input = sc.nextLine();
        while (!input.matches("\\d+")) {
            System.out.println("Input should be int, please type 1 or 0");
            input = sc.nextLine();
        }
        if (Integer.valueOf(input) == 1){
            return true;
        } else {
            return false;
        }
    }

    private static void addPlayerToList(ArrayList<Player> list) {
        sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        list.add(new Player(sc.nextLine()));

        System.out.println("Do you want to add more players? (Y=1 / N=any number)");
        String input = sc.nextLine();
        while (!input.matches("\\d+")) {
            System.out.println("Input should be int, please type 1 or 0");
            input = sc.nextLine();
        }

        if (Integer.valueOf(input) == 1) {
            addPlayerToList(list);
        }
    }

    private static int rollDie() {
        return (int) (Math.random() * 6) + 1;
    }
}
