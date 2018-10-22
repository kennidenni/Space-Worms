package main;

import Game.Game;
import Game.Rules;
import ShortestPath.Edge;
import ShortestPath.Graph;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String BOARDLINK = "https://visningsrom.stacc.com/dd_server_worms/rest/boards/2";

    public enum Dir {NW, N, NE, E, SE, S, SW, W}

    private static Scanner sc;

    public static void main(String[] args) {
        System.out.println("Collecting board from API...");
        Board board = GetFromApi.getBoard(BOARDLINK);

        boolean running = true;
        while (running) {
            sc = new Scanner(System.in);
            System.out.println("\nType with number what action you want to do");
            System.out.println("1: Find shortest path from start to goal with space rules");
            System.out.println("2: Find shortest path from start to goal with normal rules");
            System.out.println("3: Play the game");
            System.out.println("All other numbers: Exit");

            String input = sc.nextLine();

            while (!input.matches("\\d+")) {
                System.out.println();
                input = sc.nextLine();
            }
            int number = Integer.valueOf(input);
            Graph graph = null;
            switch (number) {
                case 1:
                    System.out.println("Creating graph to compute shortest path with Dijkstra...");
                    graph = getDijkstraSpaceRulesGraph(board.getHighestNumber());
                    System.out.println("Calculating shortest path from start to goal with Dijkstra...");
                    graph.calculateShortestDistances();
                    graph.printGoalResult(board.getStart(), board.getGoal());
                    break;
                case 2:
                    System.out.println("Creating graph to compute shortest path with Dijkstra...");
                    graph = getDijkstraNormalGraph(board.getHighestNumber());
                    System.out.println("Calculating shortest path from start to goal with Dijkstra...");
                    graph.calculateShortestDistances();
                    graph.printGoalResult(board.getStart(), board.getGoal());
                    break;
                case 3:
                    Game.playGame(BOARDLINK, board);
                    break;
                default:
                    running = false;
            }
        }
    }

    /**
     * Will make a graph from all the squares on the board
     *
     * @param highestNumber the highest number in the graph
     * @return a new graph
     */
    private static Graph getDijkstraSpaceRulesGraph(int highestNumber) {
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < highestNumber; i++) {
            for (String d : (ArrayList<String>) GetFromApi.getDirectionPossibilities(i + 1, BOARDLINK)) {
                if (!d.equals("next"))
                    edges.add(new Edge(i, GetFromApi.getFromDirection(i + 1, d, BOARDLINK) - 1, 1));
            }
            if (GetFromApi.getWormHole(i + 1, BOARDLINK) >= 0) {
                edges.add(new Edge(i, GetFromApi.getWormHole(i + 1, BOARDLINK) - 1, 0));
            }
        }

        Edge[] edgeArray = edges.toArray(new Edge[edges.size()]);
        Graph g = new Graph(edgeArray);
        return g;
    }


    private static Graph getDijkstraNormalGraph(int highestNumber) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < highestNumber; i++) {
            for (int j = 1; j <= 6 && i + j < highestNumber; j++) {
                int nextPos = i + j;
                if (GetFromApi.getWormHole(nextPos + 1, BOARDLINK) != -1) {
                    nextPos = GetFromApi.getWormHole(nextPos + 1, BOARDLINK) - 1;
                    System.out.println("i " + i + " nextpos " + nextPos);
                    if (i > nextPos) {
                        edges.add(new Edge(i, nextPos, 2));
                    } else {
                        edges.add(new Edge(i, nextPos, 0));
                    }

                } else {
                    System.out.println("fuck me i " + i + " nextpos " + nextPos);
                    edges.add(new Edge(i, nextPos, 1));
                }
            }
        }

        Edge[] edgeArray = edges.toArray(new Edge[edges.size()]);
        Graph g = new Graph(edgeArray);
        return g;
    }
}



