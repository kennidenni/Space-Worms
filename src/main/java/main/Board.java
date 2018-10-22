package main;

public class Board {
    private int dimX;
    private int dimY;
    private int start;
    private int goal;
    private int highestNumber;
    private String printBoard;

    /**
     * Will create a new Board
     * @param dimX
     * @param dimY
     * @param start
     * @param goal
     */
    public Board(int dimX, int dimY, int start, int goal) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.start = start;
        this.goal = goal;
        this.highestNumber = dimX * dimY;
        this.printBoard = "";
    }

    /**
     * Returns the goal on the map
     * @return goal
     */
    public int getGoal() {
        return goal;
    }

    /**
     * Returns the highest square number
     * @return highest number
     */
    public int getHighestNumber() {
        return highestNumber;
    }

    /**
     * Returns the start on the map
     * @return start
     */
    public int getStart() {
        return start;
    }

    /**
     * Prints the board in the terminal
     * @param API string to the API
     */
    public void printBoard(String API) {
        if (printBoard == "") {
            printBoard += ("-----------------------------------------------------Game Board-----------------------------------------------------\n");
            for (int i = dimY - 1; i >= 0; i--) {
                boolean reverse = false;
                if (i % 2 == 1) {
                    reverse = true;
                }

                if (reverse) {
                    for (int j = dimX; j > 0; j--) {
                        int number = (i * (dimY + 1) + j);
                        if (number <= highestNumber) {
                            int nextSquare = GetFromApi.getNextOnBoard(number, API);
                            printBoard += ("| " + number + " -> " + nextSquare + "\t");
                        }
                    }

                } else {
                    for (int j = 1; j < dimX + 1; j++) {
                        int number = (i * (dimY + 1) + j);
                        if (number <= highestNumber) {
                            int nextSquare = GetFromApi.getNextOnBoard(number, API);
                            printBoard += ("| " + number + " -> " + nextSquare + "\t");
                        }
                    }
                }

                printBoard += ("|\n");
                for (int j = 0; j < 12 * dimX / 4; j++) {
                    printBoard += ("-   ");
                }
                printBoard += "\n";
            }

            System.out.println(printBoard);
        } else {
            System.out.println(printBoard);
        }

    }
}
