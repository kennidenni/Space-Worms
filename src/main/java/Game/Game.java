package Game;

import main.Board;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static Scanner sc = new Scanner(System.in);
    private static Board mainBoard;

    /**
     * This will start the game
     *
     * @param API   The API link
     * @param board The board used to play on
     */
    public static void playGame(String API, Board board, Boolean spaceRules) {
        mainBoard = board;

        System.out.println("\nInitialize  game...");
        ArrayList<Player> playerList = makePlayerList();

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
                if (spaceRules)
                    running = p.doSpacePlayerTurn(board, API);
                else
                    running = p.doNormalPlayerTurn(board, API);
            }
        }
        System.out.println(lastPlayer.getName() + " won the game after " + round + " rounds!");
    }

    /**
     * Will print the current player positions
     *
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
     * Will add players with name a the list and return it
     *
     * @return list containing players
     */
    private static ArrayList<Player> makePlayerList() {
        ArrayList<Player> playerList = new ArrayList<>();
        int addMorePlayers = 1;
        while (addMorePlayers == 1) {
            sc = new Scanner(System.in);

            System.out.println("Enter your name: ");
            playerList.add(new Player(sc.nextLine(), mainBoard.getStart()));

            System.out.println("Do you want to add more players? (Y=1 / N=any number)");
            String input = sc.nextLine();
            while (!input.matches("\\d+")) {
                System.out.println("Input should be int, please type 1 or 0");
                input = sc.nextLine();
            }
            addMorePlayers = Integer.parseInt(input);
        }
        return playerList;
    }
}
