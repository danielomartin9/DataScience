package graph;

import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Daniel Martin
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int count = 0;
        Iteration<Integer> iter = predecessors(v);
        if (contains(v)) {
            while (iter.hasNext()) {
                iter.next();
                count += 1;
            }
        }
        return count;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> p = new ArrayList<>();
        for (int i = 1; i < getEdges().size(); i += 1) {
            if (getEdges().get(i).contains(v)) {
                p.add(i);
            }
        }
        return Iteration.iteration(p);
    }
}
