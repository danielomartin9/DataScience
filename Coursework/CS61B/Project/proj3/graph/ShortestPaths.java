package graph;

import java.util.List;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.LinkedList;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Daniel Martin
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        fringe = new TreeSet<>(valueTest);
    }

    /** A heuristic value. */
    private final Comparator<Integer> valueTest = (o1, o2) -> {
        /** Double to store the est. distance plus weight of verts. */
        double e1 = estimatedDistance(o1) + getWeight(o1);
        /** Double to store the est. distance plus weight of verts. */
        double e2 = estimatedDistance(o2) + getWeight(o2);

        if (e1 == e2) {
            /** Integer to store the result of v1 - v2. */
            int result = o1 - o2;
            return result;
        } else if (e1 > e2) {
            return 1;
        } else {
            return -1;
        }
    };

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        fringe.add(_source);

        for (int i = 0; i < _G.maxVertex() + 1; i += 1) {
            setWeight(i, Double.POSITIVE_INFINITY);
        }

        setWeight(_source, 0);
        while (!fringe.isEmpty()) {
            int val = fringe.pollFirst();
            if (val == _dest) {
                return;
            } else {

                for (int successors : _G.successors(val)) {
                    double w1 = getWeight(val, successors) + getWeight(val);
                    double w2 = getWeight(successors);

                    if (w1 < w2) {
                        fringe.remove(successors);
                        setWeight(successors, w1);
                        fringe.add(successors);
                        setPredecessor(successors, val);
                    }
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        LinkedList<Integer> minimum = new LinkedList<>();
        while ((getPredecessor(v) != 0)) {
            minimum.addFirst(v);
            v = getPredecessor(v);
        }
        minimum.addFirst(_source);
        return minimum;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** Fringe used for going through the path. */
    private TreeSet<Integer> fringe;
}
