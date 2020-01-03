package graph;

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Daniel Martin
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /**
     * The shortest paths in G from SOURCE.
     */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /**
     * A shortest path in G from SOURCE to DEST.
     */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
    }

    /**
     * The weights of the vertices.
     */
    private double[] weight = new double[_G.maxVertex() + 1];

    /**
     * The edges of predecesor.
     */
    private int[] predecessor = new int[_G.maxVertex() + 1];

    /**
     * Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     * not in the graph, returns positive infinity.
     */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        return weight[v];
    }

    @Override
    protected void setWeight(int v, double w) {
        weight[v] = w;
    }

    @Override
    public int getPredecessor(int v) {
        return predecessor[v];
    }

    @Override
    protected void setPredecessor(int v, int u) {
        predecessor[v] = u;
    }
}
