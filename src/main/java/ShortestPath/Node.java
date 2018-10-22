package ShortestPath;

import java.util.ArrayList;

public class Node {
    private int distanceFromSource = Integer.MAX_VALUE;
    private boolean visited;
    private ArrayList<Edge> edges = new ArrayList<Edge>(); // now we must create edges
    private int parent = -1;

    /**
     * Returns distance from source node
     * @return distance
     */
    public int getDistanceFromSource() {
        return distanceFromSource;
    }

    /**
     * Set distance from source node
     * @param distanceFromSource
     */
    public void setDistanceFromSource(int distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    /**
     * Returns if the node is visited
     * @return visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Set the node to visited or not
     * @param visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Returns all the edges connected to this node
     * @return list of edges
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Set the parent node
     * @param parent
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * Returns the parent node
     * @return parent node
     */
    public int getParent() {
        return parent;
    }
}