package main;

import java.io.IOException;

public class Board {
    int dimX;
    int dimY;
    int start;
    int goal;
    int highestNumber;
    String printBoard;

    public Board(int dimX, int dimY, int start, int goal) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.start = start;
        this.goal = goal;
        this.highestNumber = dimX * dimY;
        this.printBoard = "";
    }

    public int getGoal() {
        return goal;
    }

    public int getHighestNumber() {
        return highestNumber;
    }

    public int getStart() {
        return start;
    }

    public void printBoard(String API) throws IOException {
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
