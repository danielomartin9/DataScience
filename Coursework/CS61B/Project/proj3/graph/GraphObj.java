package graph;

import java.util.ArrayList;
import java.util.Collections;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Daniel Martin
 */
abstract class GraphObj extends Graph {

    /** ArrayList to store the vertices of the graph. */
    private ArrayList<Integer> vertices = new ArrayList<>();

    /** ArrayList to store the neighboring vertices from a given vertex. */
    private ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

    /** ArrayList to store the removed vertices of the graph. */
    private ArrayList<Integer> removedVert = new ArrayList<>();

    /** Integer to store the number of vertices. */
    private int sizeVert;

    /** Integer to store the number of total edges. */
    private int totalEdge;

    /** A new, empty Graph. */
    GraphObj() {
        sizeVert = 0;
        totalEdge = 0;
        vertices.add(0, 0);
        edges.add(0, new ArrayList<>());
    }

    /** Method to get access to edges ArrayList.
     * @return edges
     * */
    public ArrayList<ArrayList<Integer>> getEdges() {
        return edges;
    }

    @Override
    public int vertexSize() {
        return sizeVert;
    }

    @Override
    public int maxVertex() {
        return Collections.max(vertices);
    }

    @Override
    public int edgeSize() {
        return  totalEdge;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (vertices.isEmpty() || !vertices.contains(v)) {
            return 0;
        } else  {
            return edges.get(v).size();
        }
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return vertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            return edges.get(u).contains(v);
        }
        return false;
    }

    @Override
    public int add() {
        sizeVert += 1;
        int maxVert = maxVertex();
        int minVert = -1;
        if (!removedVert.isEmpty()) {
            minVert = Collections.min(removedVert);
            vertices.set(Collections.min(removedVert),
                    Collections.min(removedVert));
            edges.set(Collections.min(removedVert),
                    new ArrayList<>());
            removedVert.remove(
                    removedVert.indexOf(Collections.min(removedVert)));
        } else {
            edges.add(new ArrayList<>());
            vertices.add(maxVert + 1, maxVert + 1);
            return maxVert + 1;
        }
        return minVert;
    }


    @Override
    public int add(int u, int v) {
        if (contains(u) && contains(v) && !contains(u, v)) {
            totalEdge += 1;
            edges.get(u).add(v);
            if (!isDirected() && u != v && !contains(v, u)) {
                edges.get(v).add(u);
            }
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            removedVert.add(v);
            for (int vert : vertices) {
                if (vert != 0) {
                    remove(v, vert);
                    remove(vert, v);
                }
            }

            vertices.set(v, 0);
            edges.set(v, new ArrayList<>());
            sizeVert -= 1;
        }
    }

    @Override
    public void remove(int u, int v) {
        int pos = edges.get(u).indexOf(v);
        if (contains(u, v)) {
            if (!isDirected() && u != v) {
                int pos1 = edges.get(v).indexOf(u);
                edges.get(v).remove(pos1);
            }
            edges.get(u).remove(pos);
            totalEdge -= 1;
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> vert = new ArrayList<>();
        for (int i = 1; i < vertices.size(); i += 1) {
            int v = vertices.get(i);
            if (v != 0) {
                vert.add(v);
            }
        }
        return Iteration.iteration(vert);

    }

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> temp = new ArrayList<>();
        if (contains(v)) {
            for (int i: edges.get(v)) {
                temp.add(i);
            }
        } else if (!contains(v)) {
            return Iteration.iteration(new ArrayList<>());
        }
        return Iteration.iteration(temp);
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> temp = new ArrayList<>();
        for (int i = 1; i < edges.size(); i += 1) {
            if (!edges.get(i).isEmpty()) {
                for (int j: edges.get(i)) {
                    int[] temp1 = new int[2];
                    if (isDirected()) {
                        temp1[0] = i;
                        temp1[1] = j;
                        temp.add(temp1);
                    } else {
                        if (j >= i) {
                            temp1[0] = i;
                            temp1[1] = j;
                            temp.add(temp1);
                        }
                    }
                }
            }
        }
        return Iteration.iteration(temp);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!contains(v)) {
            throw new Error("V is not one of the vertices.");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (isDirected()) {
            return (u + v) * (u + v + 1) / 2 + v;
        } else {
            int max = Math.max(u, v);
            int min = Math.min(u, v);
            return (max + min) * (max + min + 1) / 2 + min;
        }
    }
}
