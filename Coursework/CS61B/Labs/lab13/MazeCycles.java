import java.util.Observable;
import java.util.Stack;
/**
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        Stack<Integer> stack =  new Stack<>();
        int[] p = new int[maze.V()];

        int sourceID = 0;
        edgeTo[0] = 0;
        distTo[0] = 0;

        stack.push(sourceID);
        p[sourceID] = sourceID;

        while (!stack.isEmpty()) {
            int c = stack.pop();

            for (int next : maze.adj(c)) {
                if (!marked[next]) {
                    stack.push(next);
                    p[next] = c;
                    distTo[next] = distTo[c] + 1;
                    announce();
                }else {
                    if (p[c] != next) {
                        distTo[c] = distTo[p[c]] + 1;
                        edgeTo[next] = c;
                        marked[c] = true;
                        announce();
                        edgeTo[c] = p[c];

                        while (c != next) {
                            edgeTo[c] = p[c];
                            c = p[c];
                            announce();
                        }
                        return;
                    }
                }
            }
            marked[c] = true;
            announce();
        }
    }

    // Helper methods go here
}

