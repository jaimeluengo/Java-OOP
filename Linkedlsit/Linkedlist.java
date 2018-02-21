package LinkedList;


/** An instance is a doubly linked list. */
public class DLList<E>  {
    private Node first; // first node of linked list (null if size is 0)
    private Node last;  // last node of linked list (null if size is 0)
    private int size;   // Number of values in the linked list.

    /** Constructor: an empty linked list. */
    public DLList() {
    }

    /** Return the number of values in this list.
     *  This function takes constant time. */
    public int size() {
        return size;
    }

    /** Return the first node of the list (null if the list is empty). */
    public Node first() {
        return first;
    }

    /** Return the last node of the list (null if the list is empty). */
    public Node last() {
        return last;
    }

    /** Return the value of node n of this list.
     * Precondition: n is a node of this list; it may not be null. */
    public E value(Node n) {
        assert n != null;
        return n.val;
    }

    /** Return a representation of this list: its values, with adjacent
     * ones separated by ", ", "[" at the beginning, and "]" at the end. <br>
     * Takes time proportional to the length of this list.<br>
     * E.g. for the list containing 4 7 8 in that order, the result it "[4, 7, 8]".
     * E.g. for the list containing two empty strings, the result is "[, ]" */
    public String toString() {
        String res= "[";
        Node n= first;
        // inv: res contains values of nodes before node n (all of them if n = null),
        //      with ", " after each (except for the last value)
        while (n != null) {
            res= res + n.val;
            n= n.next;
            if (n != null) {
                res= res + ", ";
            }
        }

        return res + "]";
    }

    /** Return a representation of this list: its values in reverse, with adjacent
     * ones separated by ", ", "[" at the beginning, and "]" at the end. <br>
     * Note that gnirtSot is the reverse of toString.
     * Takes time proportional to the length of this list.
     * E.g. for the list containing 4 7 8 in that order, the result is "[8, 7, 4]".
     * E.g. for the list containing two empty strings, the result is "[, ]". */
    public String gnirtSot() { // Note: 
        //TODO 1. Look at toString to see how that was written.
        //        Use the same scheme. Extreme case to watch out for:
        //        E is String and values are the empty string.
        //        You can't test this fully until #2, prepend, is written.
    	String res="[";
    	Node n=last;
    	while(n!=null) {
    		res+=n.val;
    		n=n.prev;
    		if(n!=null) {
    			res+= ", ";
    		}
    	}
        return res + "]";
    }
    

    /** Add value v in a new node at the front of the list.
     * This operation takes constant time. */
    public void prepend(E v) {
        //TODO 2. After writing this method, test this method and
        //        method gnirtSot thoroughly before starting on the next
        //        method. These two must be correct in order to be
        //        able to write and test all the others.
    	if(size==0) {
    		Node previous=new Node(null,v,null);
    		first=previous;
    		last=previous;
    		size=1;
    	}else {
        	Node previous=new Node(null,v,first);
        	first.prev=previous;
        	first=previous;
        	size++;
    	}

    }

    /** add value v in a new node at the end of the list.
     *  This operation takes constant time. */
    public void append(E v) {
        //TODO 3. This is the third method to write and test
    	if(size==0) {
    		Node following=new Node(null,v,null);
    		first=following;
    		last=following;
    		size=1;
    	}else {
        	Node following=new Node(last,v,null);
        	last.next=following;
        	last=following;
        	size++;
    	}
        
    }


    /** Return node number k. 
     *  Precondition: 0 <= k < size of the list.
     *  If k is 0, return first node; if k = 1, return second node, ... */
    public Node getNode(int k) {
        //TODO 4. This method should take time proportional to min(k, size-k).
        // For example, if k <= size/2, search from the beginning of the
        // list, otherwise search from the end of the list.
    	Node n;
        assert(k<size);
        if(size/2>k) {
        	n=first;
        	for(int i=0;i<k;i++) {
        		n=n.next;
        	}
        	return n;
        }else{
        	n=last;
        	for(int i=0;i<size-k-1;i++) {
        		n=n.prev;
        	}
        	return n;
        }
    }
    
    /** Remove node n from this list.
     * This operation must take constant time.
     * Precondition: n must be a node of this list; it may not be null. */
    public void delete(Node n) {
        //TODO 5. Make sure this method takes constant time. 
    	assert(n.val!=null && n.next.prev!=n);
    	if(n==first) {
    		n.next=first;
    		n.next.prev=null;
    	}else if(n==last) {
    		n.prev=last;
    		n.prev.next=null;
    	}else {
    		n.prev.next=n.next;
    		n.next.prev=n.prev;
    	}
    }
    /** Insert value v in a new node after node n.
     * This operation takes constant time.
     * Precondition: n must be a node of this list; it may not be null. */
    public void insertAfter(E v, Node n) {
        //TODO 6. Make sure this method takes constant time. 
    }

 

    /*********************/

    /** An instance is a node of this list. */
    public class Node {
        private Node prev; // Previous node on list (null if this is first node)
        private E val;     // The value of this element
        private Node next; // Next node on list. (null if this is last node)

        /** Constructor: an instance with previous node p (can be null),
         * value v, and next node n (can be null). */
        Node(Node p, E v, Node n) {
            prev= p;
            val= v;
            next= n;
        }

        /** Return the node previous to this one (null if this is the
         * first node of the list). */
        public Node prev() {
            return prev;
        }

        /** Return the value of this node. */
        public E value() {
            return val;
        }

        /** Return the next node in this list (null if this is the
         * last node of this list). */
        public Node next() {
            return next;
        }
    }

}
