// REPLACE THIS STUB WITH THE CORRECT SOLUTION.
// The current contents of this file are merely to allow things to compile
// out of the box.
/** A set of String values.
 *  @author Daniel Martin
 */

import java.util.LinkedList;

class ECHashStringSet implements StringSet {
    private static double minLoad = 0.2;
    private static double maxLoad = 5;
    private int size;
    private LinkedList<String>[] container;

    private ECHashStringSet() {
        size = 0;
        container = new LinkedList[(int)1 / minLoad];
    }

    @Override
    public void put(String val) {
        double containerLoad = (double) size / (double) container.length;
        int index = hash(val.hashCode());

        if (val != null) {
            if (containerLoad > maxLoad) {
                resize();
            } else if (container[index] == null) {
                container[index] = new LinkedList<String>();
            } else {
                container[index].add(val);
                size += 1;
            }
        } else {
            throw error("Invalid input");
        }
    }

    @Override
    public boolean contains(String val) {
        int index = hash(val.hashCode());

        if (val != null) {
            if (container[index] == null) {
                return false;
            } else {
                return container[index].contains(val);
            }
        } else {
            return false;
        }
    }

    private void resize() {
        LinkedList<String>[] prev = container;
        container = new LinkedList[2 * prev.length];
        size = 0;

        for (LinkedList<String> lst : prev) {
            if (lst != null) {
                for (String val: lst) {
                    this.put(val);
                }
            }
        }
    }

    private int hash(int hashCode) {
        int last = hashCode & 1;
        int unsigned = (hashCode >>> 1) | last;
        return (int) (unsigned % container.length);
    }
}
