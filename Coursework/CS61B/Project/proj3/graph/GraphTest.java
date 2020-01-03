package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Daniel Martin
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void inOutUD() {
        UndirectedGraph ug = new UndirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            ug.add();
        }

        ug.add(1, 2);
        ug.add(1, 4);
        ug.add(2, 4);
        ug.add(3, 5);

        assertTrue(2 == ug.inDegree(1));
        assertTrue(2 == ug.outDegree(1));

        ug.remove(1, 4);
        assertTrue(1 == ug.outDegree(1));
        assertTrue(1 == ug.outDegree(1));
    }

    @Test
    public void unDirGraphSuc() {
        UndirectedGraph ug = new UndirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            ug.add();
        }

        ug.add(1, 3);
        ug.add(1, 4);
        ug.add(1, 2);
        ug.add(2, 4);
        ug.add(3, 5);

        for (int i : ug.successors(1)) {
            assertTrue(ug.contains(i, 1));
            assertTrue(ug.contains(1, i));
        }

        ug.remove(1);

        for (int i : ug.successors(2)) {
            assertTrue(ug.contains(i, 2));
            assertTrue(ug.contains(2, i));
        }
    }

    @Test
    public void unDirGraphPred() {
        UndirectedGraph ug = new UndirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            ug.add();
        }

        ug.add(1, 3);
        ug.add(1, 4);
        ug.add(1, 2);
        ug.add(2, 4);
        ug.add(3, 5);

        for (int i : ug.predecessors(1)) {
            assertTrue(ug.contains(i, 1));
            assertTrue(ug.contains(1, i));
        }
    }

    @Test
    public void dirGraphSuc() {
        DirectedGraph dg = new DirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            dg.add();
        }

        dg.add(1, 3);
        dg.add(1, 4);
        dg.add(1, 2);
        dg.add(2, 4);
        dg.add(3, 5);

        for (int i : dg.successors(1)) {
            assertFalse(dg.contains(i, 1));
            assertTrue(dg.contains(1, i));
        }
    }

    @Test
    public void dirGraphPred() {
        DirectedGraph dg = new DirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            dg.add();
        }

        dg.add(1, 3);
        dg.add(1, 4);
        dg.add(1, 2);
        dg.add(2, 4);
        dg.add(3, 5);

        assertFalse(dg.predecessors(1).hasNext());
        for (int i : dg.predecessors(3)) {
            assertTrue(dg.contains(i, 3));
        }
        dg.remove(1);
        for (int i : dg.predecessors(3)) {
            assertTrue(dg.contains(i, 3));
        }
    }

    @Test
    public void unDirGraphEdge() {
        UndirectedGraph ug = new UndirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            ug.add();
        }

        ug.add(1, 3);
        ug.add(1, 4);
        ug.add(1, 2);
        ug.add(2, 4);
        ug.add(3, 5);
        ug.add(2, 1);

        for (int[] elem : ug.edges()) {
            assertTrue(ug.contains(elem[0], elem[1]));
        }
    }


    @Test
    public void unDirGraphBreath() {
        UndirectedGraph ug = new UndirectedGraph();

        for (int i = 0; i < 9; i += 1) {
            ug.add();
        }

        ug.add(1, 2);
        ug.add(2, 3);
        ug.add(2, 5);
        ug.add(3, 4);
        ug.add(5, 6);
        ug.add(2, 7);
        ug.add(7, 8);
        ug.add(7, 9);

        BreadthFirstTraversal bft = new BreadthFirstTraversal(ug) {
            @Override
            protected boolean visit(int v) {
                assertTrue(ug.contains(v));
                return super.visit(v);
            }
        };
        bft.traverse(ug.maxVertex());
    }


    @Test
    public void dirGraphEdge() {
        DirectedGraph dg = new DirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            dg.add();
        }

        dg.add(1, 3);
        dg.add(1, 4);
        dg.add(1, 2);
        dg.add(2, 4);
        dg.add(3, 5);
        dg.add(2, 1);

        int k = 0;
        for (int[] elem : dg.edges()) {
            assertTrue(dg.contains(elem[0], elem[1]));
            k += 1;
        }
    }

    @Test
    public void unDirGraphDepth() {
        UndirectedGraph ug = new UndirectedGraph();

        for (int i = 0; i < 9; i += 1) {
            ug.add();
        }

        ug.add(1, 2);
        ug.add(2, 3);
        ug.add(2, 5);
        ug.add(3, 4);
        ug.add(5, 6);
        ug.add(2, 7);
        ug.add(7, 8);
        ug.add(7, 9);

        DepthFirstTraversal dft = new DepthFirstTraversal(ug) {
            @Override
            protected boolean visit(int v) {
                assertTrue(ug.contains(v));
                return super.visit(v);
            }
        };
        dft.traverse(ug.maxVertex());
    }

    @Test
    public void addRemoveTest() {
        DirectedGraph dg = new DirectedGraph();

        assertTrue(1 == dg.add());
        assertTrue(2 == dg.add());
        assertTrue(3 == dg.add());
        assertTrue(4 == dg.add());
        assertTrue(5 == dg.add());
        assertTrue(dg.contains(1));
        assertTrue(dg.contains(2));
        assertTrue(dg.contains(3));
        assertTrue(dg.contains(4));
        assertTrue(dg.contains(5));

        dg.remove(1);

        assertTrue(dg.contains(2));
        assertTrue(dg.contains(3));
        assertTrue(dg.contains(4));
        assertTrue(dg.contains(5));
        assertFalse(dg.contains(1));

        dg.add();

        assertTrue(dg.contains(1));
    }

    @Test
    public void addEdgeTest() {
        DirectedGraph dg = new DirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            dg.add();
        }

        dg.add(1, 3);
        dg.add(1, 4);
        dg.add(1, 2);
        dg.add(2, 3);
        dg.add(4, 5);

        assertTrue(5 == dg.edgeSize());
        assertTrue(dg.contains(1, 3));
        assertTrue(dg.contains(1, 4));
        assertTrue(dg.contains(4, 5));
        assertFalse(dg.contains(2, 4));
    }

    @Test
    public void removeVertexTest() {
        DirectedGraph dg = new DirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            dg.add();
        }

        dg.add(1, 3);
        dg.add(1, 4);
        dg.add(1, 2);
        dg.add(2, 3);
        dg.add(4, 5);
        dg.remove(1);

        assertTrue(2 == dg.edgeSize());
        assertFalse(dg.contains(1, 3));
        assertFalse(dg.contains(1, 4));
        assertFalse(dg.contains(1, 2));
        assertTrue(dg.contains(4, 5));
    }

    @Test
    public void removeEdgeTest() {
        DirectedGraph dg = new DirectedGraph();

        for (int i = 0; i < 5; i += 1) {
            dg.add();
        }

        dg.add(1, 2);
        dg.add(1, 3);
        dg.add(1, 4);
        dg.add(2, 3);
        dg.add(4, 5);
        dg.remove(1, 3);

        assertFalse(dg.contains(1, 3));
        assertTrue(dg.contains(1, 4));
        assertTrue(dg.contains(1, 2));
        assertTrue(dg.contains(4, 5));
    }
}
