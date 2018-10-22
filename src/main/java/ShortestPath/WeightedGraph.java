package ShortestPath;

public class WeightedGraph {

    private int[][] edges;  // adjacency matrix
    private String[] labels;

    /**
     * Creates a new weighted graph
     *
     * @param size total number of nodes
     */
    public WeightedGraph(int size) {
        edges = new int[size][size];
        labels = new String[size];
    }

    /**
     * Returns the size of the graph
     *
     * @return number of vertices
     */
    public int size() {
        return labels.length;
    }

    /**
     * Set the label for a specific vertex
     *
     * @param vertex you want to assign to
     * @param label  String you want to assign
     */
    public void setLabel(int vertex, String label) {
        labels[vertex] = label;
    }

    /**
     * Get the label for a specific vertex
     *
     * @param vertex number
     * @return label to the vertex
     */
    public String getLabel(int vertex) {
        return labels[vertex];
    }

    /**
     * Add an edge with weight between two vertices
     *
     * @param source vertex
     * @param target vertex
     * @param w      weight
     */
    public void addEdge(int source, int target, int w) {
        edges[source][target] = w;
    }

    /**
     * Get edge weight between two nodes
     *
     * @param source vertex
     * @param target vertex
     * @return weight
     */
    public int getWeight(int source, int target) {
        return edges[source][target];
    }

    /**
     * Returns all the neighbours to a specific vertex
     *
     * @param vertex you want to check
     * @return a list containing all the neighbours
     */
    public int[] neighbours(int vertex) {
        int count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) count++;
        }
        int[] answer = new int[count];
        count = 0;
        for (int i = 0; i < edges[vertex].length; i++) {
            if (edges[vertex][i] > 0) answer[count++] = i;
        }
        return answer;
    }

    /**
     * Used to print the graph.
     */
    public void print() {
        for (int j = 0; j < edges.length; j++) {
            System.out.print(labels[j] + ": ");
            for (int i = 0; i < edges[j].length; i++) {
                if (edges[j][i] > 0) System.out.print(labels[i] + ":" + edges[j][i] + " ");
            }
            System.out.println();
        }
    }
}