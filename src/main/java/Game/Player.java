package Game;

import main.Board;
import main.GetFromApi;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private int position;

    /**
     * Creates a new player with a name and starting position
     *
     * @param name        player name
     * @param startingPos starting position on the board
     */
    Player(String name, int startingPos) {
        this.name = name;
        position = startingPos;
    }

    /**
     * Get the current position
     *
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Get the name of the player
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Do a turn with rolling a die and moving with normal rules
     *
     * @param board playingBoard
     * @param API   String with link to api
     * @return if player won return false, else return true
     */
    public boolean doNormalPlayerTurn(Board board, String API) {
        int dieRoll = rollDie();
        int nextPos = position + dieRoll;

        if (Rules.outOfBoard(board.getHighestNumber(), nextPos)) {
            nextPos = board.getHighestNumber();
        }

        if (GetFromApi.getWormHole(nextPos, API) >= 0) {
            position = GetFromApi.getWormHole(nextPos, API);
            System.out.println(name + " rolled a " + dieRoll + " and is now on Square " + position + " because of a wormhole on Square " + nextPos);
        } else {
            position = nextPos;
            System.out.println(name + " rolled a " + dieRoll + " and is now on Square " + position);
        }

        return position != board.getGoal();
    }

    /**
     * Do a turn with rolling a die and moving with space rules
     *
     * @param board playingBoard
     * @param API   String with link to api
     * @return if player won return false, else return true
     */
    public boolean doSpacePlayerTurn(Board board, String API) {
        Scanner sc = new Scanner(System.in);
        int dieRoll = rollDie();

        ArrayList<String> possibilities = Rules.findPossiblePaths(position, dieRoll, API, board.getHighestNumber());
        System.out.println(name + " rolled a " + dieRoll + ". What direction do you want to walk? Type one of the directions: " + possibilities);
        String input = sc.nextLine();
        while (!possibilities.contains(input)) {
            System.out.println("Wrong input. Type one of the directions: " + possibilities);
            input = sc.nextLine();
        }

        walkXTimesInDir(dieRoll, input, API);
        if (GetFromApi.getWormHole(position, API) >= 0) {
            position = GetFromApi.getWormHole(position, API);
            System.out.println(name + " walked " + input + " " + dieRoll + " times, hit a Wormhole and is now standing on " + position);
        } else {
            System.out.println(name + " walked " + input + " " + dieRoll + " times and is now standing on Square " + position);
        }

        return position != board.getGoal();
    }

    /**
     * Walk n-times in a specific direction
     *
     * @param times times walking
     * @param dir   direction
     * @param API   String URL link
     */
    private void walkXTimesInDir(int times, String dir, String API) {
        while (times > 0) {
            position = GetFromApi.getFromDirection(position, dir, API);
            times--;
        }
    }

    /**
     * Roll a die
     *
     * @return die roll
     */
    private int rollDie() {
        int DICE = 6;
        return (int) (Math.random() * DICE) + 1;
    }
}
