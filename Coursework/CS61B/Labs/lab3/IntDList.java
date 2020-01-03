
public class IntDList {

    protected DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     *
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     *
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() { return _back._val; }

    /**
     *
     * @return The number of elements in this list.
     */
    public int size() {
        DNode temp = _front;
        int count = 0;

        while(temp != null){
                count += 1;
                temp = temp._next;
        }
        return count;
    }

    /**
     *
     * @param i index of element to return, where i = 0 returns the first element,
     *          i = 1 returns the second element, i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size
     *          for positive indices and -size <= i < 0 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        DNode temp = _front;
        if(i > 0){
            temp = _front;
            while(i > 0){
                temp = temp._next;
                i--;
            }
        }else if(i < 0){
            temp = _back;
            while(i < -1){
                temp = temp._prev;
                i++;
            }
        }
        return temp._val;
    }

    /**
     *
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        if(_front == null){
            _front = new DNode(null, d, null);
            _back = _front;
        }else {
            _front = new DNode(null, d, _front);
            _front._next._prev = _front;
        }
    }

    /**
     *
      * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        if(_front == null){
            _back = new DNode(null, d, null);
            _front = _back;
        }else{
            _back = new DNode(_back, d, null);
            _back._prev._next = _back;
        }
    }

    /**
     * Removes the last item in the IntDList and returns it
     * @return the item that was deleted
     */
    public int deleteBack() {
        if(size() == 1){
            int tempVal = _front._val;
            _front = null;
            _back = null;
            return tempVal;
        }else{
            DNode temp = _back;
            int tempVal = temp._val; //Last item in the list to return

            _back = _back._prev;
            _back._next = null;
            return tempVal;
        }
    }

    /**
     *
     * @return a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc.
     *  Hint:
     *  String a = "a";
     *  a += "b";
     *  System.out.println(a); //prints ab
     */
    public String toString() {
        String stringlst = "[";
        for (int i  = 0; i < size(); i++){
            stringlst = stringlst + get(i);
            if(i != size() - 1){
                stringlst = stringlst + ", ";
            }
        }
        return stringlst + "]";
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information! */
    protected static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        protected DNode(int val) {
            this(null, val, null);
        }

        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
