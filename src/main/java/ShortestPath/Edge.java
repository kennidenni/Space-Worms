package ShortestPath;

public class Edge {
    private int fromNodeIndex;
    private int toNodeIndex;
    private int length;

    /** Creates a new Edge connecting two nodes
     * @param fromNodeIndex
     * @param toNodeIndex
     * @param length the length or weight on the Edge
     */
    public Edge(int fromNodeIndex, int toNodeIndex, int length) {
        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.length = length;
    }

    /**
     * Returns the first node
     * @return int
     */
    public int getFromNodeIndex() {
        return fromNodeIndex;
    }

    /**
     * Returns the second node
     * @return int
     */
    public int getToNodeIndex() {
        return toNodeIndex;
    }

    /**
     * Returns the length/weight of the Edge
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * Determines the neighbouring node of a supplied node, based on the two nodes connected by this edge
     * @param nodeIndex int to compare with
     * @return node1 or node2
     */
    public int getNeighbourIndex(int nodeIndex) {
        if (this.fromNodeIndex == nodeIndex) {
            return this.toNodeIndex;
        } else {
            return this.fromNodeIndex;
        }
    }
}