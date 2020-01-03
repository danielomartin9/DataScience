import java.util.LinkedList;
import java.util.Observable;
/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
//    public int[] distTo;
//    public int[] edgeTo;
//    public boolean[] marked;
    private int sourceID;
    private int targetID;
    private boolean found = false;
    private Maze _m;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        _m = m;
        sourceID = _m.xyTo1D(sourceX, sourceY);
        targetID = _m.xyTo1D(targetX, targetY);
        distTo[sourceID] = 0;
        edgeTo[sourceID] = sourceID;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(sourceID);

        while (q.size() != 0) {
            int val = q.remove();
            marked[val] = true;
            announce();

            for(int next: _m.adj(val)) {
                if (!marked[next]) {
                    distTo[next] = distTo[val] + 1;
                    edgeTo[next] = val;
                    marked[next] = true;
                    announce();

                    if (next == targetID) { found = true; }
                    if (found == true) { return; }
                    q.add(next);
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs();
    }
}

