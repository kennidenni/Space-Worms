package ShortestPath;

import java.util.ArrayList;

public class Dijkstra {

    /**
     * Will run the Dijkstra algorithm to find the shortest path
     *
     * @param Graph a weighted graph
     * @param start the start node
     * @return an array with preceeding nodes in the path
     */
    public static int[] runDijkstra(WeightedGraph Graph, int start) {
        int[] distance = new int[Graph.size()];  // shortest known distance from "s"
        int[] pred = new int[Graph.size()];  // preceeding node in path
        boolean[] visited = new boolean[Graph.size()]; // all false initially

        //Set all distances as close as possible to infinity
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[start] = 0;

        for (int i = 0; i < distance.length; i++) {
            int next = minVertex(distance, visited);

            // check in case minVertex() returns -1
            if (next != -1) {
                visited[next] = true;

                // The shortest path to next is dist[next] and via pred[next].
                int[] neighbors = Graph.neighbours(next);
                for (int j = 0; j < neighbors.length; j++) {
                    int v = neighbors[j];
                    int d = distance[next] + Graph.getWeight(next, v);
                    if (distance[v] > d) {
                        distance[v] = d;
                        pred[v] = next;
                    }
                }
            }
        }
        return pred;
    }

    /**
     * Returns the next vertex to be used
     *
     * @param distance from start node
     * @param visited  list containing all the vertices
     * @return the vertex with lowest distance
     */
    private static int minVertex(int[] distance, boolean[] visited) {
        int x = Integer.MAX_VALUE;
        int y = -1;   // graph not connected, or no unvisited vertices
        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] < x) {
                y = i;
                x = distance[i];
            }
        }
        return y;
    }

    /**
     * Backtracks the path and prints it from goal to start
     *
     * @param Graph weighted graph
     * @param pred  preceeding nodes from a finished Dijkstra
     * @param goal  goal on the map
     * @param start start on the map
     */
    public static void printPath(WeightedGraph Graph, int[] pred, int goal, int start) {
        ArrayList<String> path = new ArrayList<>();
        while (start != goal) {
            path.add(0, Graph.getLabel(start));
            start = pred[start];
        }
        path.add(0, Graph.getLabel(goal));
        System.out.println(path);
    }

}