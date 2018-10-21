package main;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public enum Dir {NW, N, NE, E, SE, S, SW, W}

    private static final String BOARDLINK = "https://visningsrom.stacc.com/dd_server_worms/rest/boards/2";

    public static void main(String[] args) throws IOException {
        System.out.println("Collecting board from API...");
        Board board = GetFromApi.getBoard(BOARDLINK);

        /*
        System.out.println("Creating graph to compute shortest path with Dijkstra...");
        Graph graph = getDijkstraGraph(board.getHighestNumber());
        System.out.println("Calculating shortest path from start to goal with Dijkstra...");
        graph.calculateShortestDistances();
        graph.printGoalResult(board.getStart(), board.getGoal());
        */

        Game.playGame(BOARDLINK, board);
    }

    private static Graph getDijkstraGraph(int highestNumber) throws IOException {
        ArrayList edges = new ArrayList();

        for (int i = 0; i < highestNumber; i++) {
            for (Dir d : Dir.values()) {
                if (GetFromApi.getFromDirection(i + 1, d, BOARDLINK) - 1 >= 0) {
                    edges.add(new Edge(i, (GetFromApi.getFromDirection(i + 1, d, BOARDLINK) - 1), 1));
                }
            }
            if (GetFromApi.getWormHole(i + 1, BOARDLINK) - 1 >= 0) {
                edges.add(new Edge(i, (GetFromApi.getWormHole(i + 1, BOARDLINK) - 1), 0));
            }
        }
        Edge[] edgeArray = (Edge[]) edges.toArray(new Edge[edges.size()]);
        Graph g = new Graph(edgeArray);
        return g;
    }
}


