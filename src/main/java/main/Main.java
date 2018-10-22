package main;

import Game.Game;
import ShortestPath.Dijkstra;
import ShortestPath.WeightedGraph;

import java.util.Scanner;

public class Main {
    private static final String BOARDLINK = "https://visningsrom.stacc.com/dd_server_worms/rest/boards/2";

    public static void main(String[] args) {
        System.out.println("Collecting board from API...");
        Board board = GetFromApi.getBoard(BOARDLINK);

        boolean running = true;
        while (running) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\nType with number what action you want to do");
            System.out.println("1: Find shortest path from start to goal with space rules");
            System.out.println("2: Find shortest path from start to goal with normal rules");
            System.out.println("3: Play the game with space rules");
            System.out.println("4: Play the game with normal rules");
            System.out.println("All other numbers: Exit");

            String input = sc.nextLine();

            while (!input.matches("\\d+")) {
                System.out.println();
                input = sc.nextLine();
            }
            int number = Integer.valueOf(input);

            switch (number) {
                case 1:
                    System.out.println("Creating graph to compute shortest path with Dijkstra...");
                    WeightedGraph spaceGraph = getDijkstraSpaceRulesWGraph(board.getHighestNumber());
                    System.out.println("Calculating shortest path from start to goal with Dijkstra...");
                    int[] pred1 = Dijkstra.runDijkstra(spaceGraph, 0);
                    System.out.println("Printing path...");
                    Dijkstra.printPath(spaceGraph, pred1, 0, board.getGoal() - 1);
                    break;
                case 2:
                    System.out.println("Creating graph to compute shortest path with Dijkstra...");
                    WeightedGraph normalGraph = getDijkstraNormalWGraph(board.getHighestNumber());
                    System.out.println("Calculating shortest path from start to goal with Dijkstra...");
                    int[] pred2 = Dijkstra.runDijkstra(normalGraph, 0);
                    System.out.println("Printing path...");
                    Dijkstra.printPath(normalGraph, pred2, 0, board.getGoal() - 1);
                    break;
                case 3:
                    Game.playGame(BOARDLINK, board, true);
                    break;
                case 4:
                    Game.playGame(BOARDLINK, board, false);
                    break;
                default:
                    running = false;
            }
        }
    }

    /**
     * Will make a graph from all the squares on the board with normal rules
     *
     * @param highestNumber the highest number on the board
     * @return a new weighted graph
     */
    private static WeightedGraph getDijkstraNormalWGraph(int highestNumber) {
        WeightedGraph WG = new WeightedGraph(highestNumber);

        for (int i = 0; i < highestNumber; i++) {
            for (int j = 1; j <= 6 && i + j < highestNumber; j++) {
                int nextPos = i + j;
                if (GetFromApi.getWormHole(i + 1, BOARDLINK) != -1) {
                    int next = GetFromApi.getWormHole(i + 1, BOARDLINK);
                    WG.setLabel(i, "w" + (i + 1));
                    WG.addEdge(i, next - 1, 1);
                } else {
                    WG.setLabel(i, String.valueOf(i + 1));
                    WG.addEdge(i, nextPos, 2);
                }

            }
        }
        return WG;
    }


    /**
     * Will make a graph from all the squares on the board with space rules
     *
     * @param highestNumber the highest number on the board
     * @return a new weighted graph
     */
    private static WeightedGraph getDijkstraSpaceRulesWGraph(int highestNumber) {
        WeightedGraph WG = new WeightedGraph(highestNumber);

        for (int i = 0; i < highestNumber; i++) {
            if (GetFromApi.getWormHole(i + 1, BOARDLINK) != -1) {
                int next = GetFromApi.getWormHole(i + 1, BOARDLINK);
                WG.setLabel(i, "w" + (i + 1));
                WG.addEdge(i, next - 1, 1);
            } else {
                for (String d : GetFromApi.getDirectionPossibilities(i + 1, BOARDLINK)) {
                    if (!d.equals("next")) {
                        int next = GetFromApi.getFromDirection(i + 1, d, BOARDLINK) - 1;
                        WG.setLabel(i, String.valueOf(i + 1));
                        WG.addEdge(i, next, 2);
                    }
                }
            }
        }
        return WG;
    }
}



