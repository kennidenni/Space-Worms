package Game;

import main.Board;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static String API;
    private static Scanner sc = new Scanner(System.in);
    private static Board mainBoard;

    /**
     * This will start the game
     * @param boardLink The API link
     * @param board The board used to play on
     */
    public static void playGame(String boardLink, Board board) {
        API = boardLink;
        mainBoard = board;

        System.out.println("\nInitialize  game...");
        ArrayList<Player> playerList = makePlayerList();
        boolean spaceRules = setupForGame();

        System.out.println("\nStarting game...");
        boolean running = true;
        int round = 0;
        Player lastPlayer = null;
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
                    running = p.doNormalPlayerTurn(board, API);
                else
                    running = p.doSpacePlayerTurn(board, API);
            }
        }

        System.out.println(lastPlayer.getName() + " won the game after " + round + " rounds!");
    }

    /**
     * Will print the current player positions
     * @param playerList a list with all the players
     */
    private static void printPlayerPosition(ArrayList<Player> playerList) {
        String output = "";
        for (Player p : playerList) {
            output += (p.getName() + " is on Square: " + p.getPosition() + ", ");
        }
        System.out.println(output);
    }

    /**
     * Will set up the game before start
     * @return boolean that tells if it should be normal or weird rules
     */
    private static boolean setupForGame() {

        System.out.println("Do you want normal snakes-and-latter rules or space rules? (Space=1 / Normal=any number");
        String input = sc.nextLine();

        while (!input.matches("\\d+")) {
            System.out.println("Input should be int, please type 1 or 0");
            input = sc.nextLine();
        }
        if (Integer.valueOf(input) == 1) {
            return true;
        }
        return false;

    }

    /**
     * Will add players with name a the list and return it
     * @return list containing players
     */
    private static ArrayList makePlayerList() {
        ArrayList<Player> playerList = new ArrayList<>();
        int addMorePlayers = 1;
        while (addMorePlayers == 1) {
            sc = new Scanner(System.in);

            System.out.println("Enter your name: ");
            playerList.add(new Player(sc.nextLine(), mainBoard.getStart()));

            for (Player i : playerList) {
                System.out.println(i.getName());
            }

            System.out.println("Do you want to add more players? (Y=1 / N=any number)");
            String input = sc.nextLine();
            while (!input.matches("\\d+")) {
                System.out.println("Input should be int, please type 1 or 0");
                input = sc.nextLine();
            }
            addMorePlayers =  Integer.parseInt(input);
        }
        return playerList;
    }



}
