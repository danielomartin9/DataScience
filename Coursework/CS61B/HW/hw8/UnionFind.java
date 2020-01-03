import java.util.Arrays;

/** A partition of a set of contiguous integers that allows (a) finding whether
 *  two integers are in the same partition set and (b) replacing two partitions
 *  with their union.  At any given time, for a structure partitioning
 *  the integers 1-N, each partition is represented by a unique member of that
 *  partition, called its representative.
 *  @author Daniel Martin
 */
public class UnionFind {
    /** Array that stores the partitions. */
    private int[] array;

    private int counter;
    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        int[] array = new int[N];

        for (int i = 0; i < N; i += 1) {
            array[i] = i;
        }

        counter = N;
        // FIXME
    }

    /** Return the representative of the partition currently containing V.
     *  Assumes V is contained in one of the partitions.  */
    public int find(int v) {
        return array[v];  // FIXME
    }

    /** Return true iff U and V are in the same partition. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single partition, returning its representative. */
    public int union(int u, int v) {
        int uNum = find(u);
        int vNum = find(v);

        for (int i = 0; i < array.length; i += 1) {
            if (array[i] == uNum) {
                array[i] = vNum;
            }
        }

        counter -= 1;
        return uNum;
    }

    // FIXME
}
