import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** An instance of RepostTree represents the spreading of a Post through
 * a (social) Network of people.
 * The root of the RepostTree is the original poster. From there, each
 * person in the RepostTree is the child of the person from whom they saw the post.
 * For example, for the tree:
 * <p>
 *      A
 *     / \
 *    B   C
 *       / \
 *      D   E
 * <p>
 * Person A originally created the post, B and C saw A's post, 
 * C reshared the post, and D and E saw the post from C.
 * <p>
 * Important note: The name of each person in the RepostTree is unique.
 *
 * @author Mshnik and ebirrell
 */
public class RepostTree {

    /** The String to be used as a separator in toString() */
    public static final String SEPARATOR = " - ";

    /** The String that marks the start of children in toString() */
    public static final String START_CHILDREN_DELIMITER = "[";

    /**  The String that divides children in toString() */
    public static final String DELIMITER = ", ";

    /** The String that marks the end of children in toString() */
    public static final String END_CHILDREN_DELIMITER = "]";

    /** The String that is the space increment in toStringVerbose() */
    public static final String VERBOSE_SPACE_INCREMENT = "\t";

    /** The person at the root of this RepostTree.
     * This is the original poster, who first create the content. 
     * root is non-null.
     * All Person's in a RepostTree have different names. There are no duplicates
     */
    private Person root;

    /** The immediate children of this RepostTree node.
     * Each element of children saw the post from the person at this node.
     * root is non-null but will be an empty set if this is a leaf. */
    private Set<RepostTree> children;

    /** Constructor: a new RepostTree with root p and no children.
     * Throw an IllegalArgumentException if p is null. */
    public RepostTree(Person p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct RepostTree with null root");
        root= p;
        children= new HashSet<>();
    }

    /** Constructor: a new RepostTree that is a copy of tree p.
     * Tree p and its copy have no node in common (but nodes can share a Person).
     * Throw an IllegalArgumentException if p is null. */
    public RepostTree(RepostTree p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct RepostTree as copy of null");
        root= p.root;
        children= new HashSet<>();

        for (RepostTree rt : p.children) {
            children.add(new RepostTree(rt));
        }
    }

    /** Return the person that is at the root of this RepostTree */
    public Person getRoot() {
        return root;
    }

    /** Return the number of direct children of this RepostTree */
    public int getChildrenCount() {
        return children.size();
    }

    /** Return a COPY of the set of children of this RepostTree. */
    public Set<RepostTree> getChildren() {
        return new HashSet<>(children);
    }

    /** Insert c in this RepostTree as a child of p and
     * return the RepostTree whose root is the new child.
     * Throw an IllegalArgumentException if:<br>
     * -- p or c is null,<br>
     * -- c is already in this RepostTree, or<br>
     * -- p is not in this RepostTree */
    public RepostTree insert(Person c, Person p) throws IllegalArgumentException {
        //TODO 1. This function has a simple, non-recursive implementation
        if (p == null || c == null || contains(c) || !contains(p)) 
            throw new IllegalArgumentException("Can't insert child as parent or"
            		+ " child are null");
        RepostTree parent = getTree(p);
        RepostTree child = new RepostTree(c);
        parent.children.add(child);	     

        return child;
    }

    /** Return the number of people in this RepostTree.
     * Note: If this is a leaf, the size is 1 (just the root) */
    public int size() {
    	if(getChildrenCount()==0)return 1;
    	int count=0;
        for (RepostTree rt : children) {
            count+=rt.size();
        }
        return count+1;
    }

    /**Return the depth at which p occurs in this RepostTree, or -1
     * if p is not in the RepostTree.
     * Note: depth(root) is 0.
     * If p is a child of this RepostTree, then depth(p) is 1. etc. */
    public int depth(Person p) {
    	if(p == null) return -1;
        if(root==p) return 0;
        for (RepostTree rt : children) {
        	int d = rt.depth(p);
        	if(d!=-1) return 1+d;
        }
        return -1;
    }

    /** If p is in this tree, return the RepostTree object in this tree 
     * that contains p. If p is not in this tree, return null.
     * <p>
     * Example: Calling getTree(root) should return this.
     */
    public RepostTree getTree(Person p) {
        if (root == p) return this; //Base case - look here

        // Recursive case - ask children to look
        for (RepostTree rt : children) {
            RepostTree search= rt.getTree(p);
            if (search != null) return search;
        }

        return null; // Not found
    }

    /** Return true iff this RepostTree contains p. */
    public boolean contains(Person p) {
        /* Note: This RepostTree contains p iff the root of this
         * RepostTree is p or if one of p's children contains p. */
        if (root == p) return true;
        for (RepostTree rt : children) {
            if (rt.contains(p)) return true;
        }
        return false;
    }


    /** Return the maximum depth of this RepostTree, i.e. the longest path from
     * the root to a leaf. Example. If this RepostTree is a leaf, return 0.
     */
    public int maxDepth() {
        int maxDepth= 0;
        for (RepostTree rt : children) {
            maxDepth= Math.max(maxDepth, rt.maxDepth() + 1);
        }
        return maxDepth;
    }

    /** Return the width of this tree at depth d (i.e. the number of repost 
     * trees that occur at depth d, where the depth of the root is 0.
     * Throw an IllegalArgumentException if depth < 0.
     * Thus, for the following tree :
     * Depth level:
     * 0       A
     *        / \
     * 1     B   C
     *      /   / \
     * 2   D   E   F
     *              \
     * 3             G
     * <p>
     * A.widthAtDepth(0) = 1,  A.widthAtDepth(1) = 2,
     * A.widthAtDepth(2) = 3,  A.widthAtDepth(3) = 1,
     * A.widthAtDepth(4) = 0.
     * C.widthAtDepth(0) = 1,  C.widthAtDepth(1) = 2
     */
    public int widthAtDepth(int d) throws IllegalArgumentException {
        //TODO 4
        // For d > 0, the width at depth d is the sum of the widths
        //            of the children at depth d-1.
    	if(d<0) {
    		throw new IllegalArgumentException("The depth cannot be negative");
    	}
    	if(d==0) return 1;
    	if(d==1) return getChildrenCount();
    	int width = 0;
    	for(RepostTree rt : children) {
    		width+=rt.widthAtDepth(d-1);
    	}
    	return width;
    }

    /** Return the maximum width of all the widths in this tree, i.e. the
     * maximum value that could be returned from widthAtDepth for this tree.
     */
    public int maxWidth() {
        return maxWidthImplementationOne(this);
    }

    // Simple implementation of maxWith. Relies on widthAtDepth.
    // Takes time proportional the the square of the size of the t.
    static int maxWidthImplementationOne(RepostTree t) {
        int width = 0;
        int depth = t.maxDepth();
        for (int i = 0; i <= depth; i++) {
            width = Math.max(width, t.widthAtDepth(i));
        }
        return width;
    }

    /* Better implementation of maxWidth. Caches results in an array.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationTwo(RepostTree t) {
        // For each integer d, 0 <= d <= maximum depth of t, store in
        // widths[d] the number of nodes at depth d in t.
        // The calculation is done by calling recursive procedure addToWidths.
        int[] widths = new int[t.maxDepth() + 1];   // initially, contains 0's
        t.addToWidths(0, widths);

        int max = 0;
        for (int width : widths) {
            max = Math.max(max, width);
        }
        return max;
    }

    /* For each node of this RepostTree that is at some depth d in this
     * RepostTree add 1 to widths[depth + d]. */
    private void addToWidths(int depth, int[] widths) {
        widths[depth]++;        //the root of this SharintTree is at depth d = 0
        for (RepostTree dt : children) {
            dt.addToWidths(depth + 1, widths);
        }
    }

    /* Better implementation of maxWidth. Caches results in a HashMap.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationThree(RepostTree t) {
        // For each possible depth d >= 0 in tree t, widthMap will contain the
        // entry (d, number of nodes at depth d in t). The calculation is
        // done using recursive procedure addToWidthMap.

        // For each integer d, 0 <= d <= maximum depth of t, add to
        // widthMap an entry <d, 0>.
        HashMap<Integer, Integer> widthMap = new HashMap<>();
        for (int d= 0; d <= t.maxDepth() + 1; d= d+1) {
            widthMap.put(d, 0);
        }

        t.addToWidthMap(0, widthMap);

        int max= 0;
        for (Integer w : widthMap.values()) {
            max= Math.max(max, w);
        }
        return max;
    }

    /* For each node of this RepostTree that is at some depth d in this RepostTree,
     * add 1 to the value part of entry <depth + d, ...> of widthMap. */
    private void addToWidthMap(int depth, HashMap<Integer, Integer> widthMap) {
        widthMap.put(depth, widthMap.get(depth) + 1);  //the root is at depth d = 0
        for (RepostTree dt : children) {
            dt.addToWidthMap(depth + 1, widthMap);
        }
    }

    /** Return the route the Post took to get from "here" (the root of
     * this RepostTree) to child c.
     * Return null if no such route.
     * For example, for this tree:
     * <p>
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *             \
     * 3            G
     * <p>
     * A.getRepostRoute(E) should return a list of [A, C, E].
     * A.getRepostRoute(A) should return [A].
     * A.getRepostRoute(X) should return null.
     * <p>
     * B.getRepostRoute(C) should return null.
     * B.getRepostRoute(D) should return [B, D]
     */
    public List<Person> getRepostRoute(Person c) {
    	if(c==null) return null;
    	if(c==root) {
    		List<Person> route = new LinkedList<Person>();
    		((LinkedList<Person>)route).addFirst(c);
    		return route;
    	}
    	for(RepostTree rt : children) {
    		List<Person> r = rt.getRepostRoute(c);
    		if(r!=null) {
    			((LinkedList<Person>)r).addFirst(root);
    			return r;
    		}
    	}
    	return null;
    }

    /** Return the immediate parent of c (null if c is not in this
     * RepostTree).
     * <p>
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.getParent(E) returns C.
     * C.getParent(E) returns C.
     * A.getParent(B) returns A.
     * E.getParent(F) returns null.
     */
    public Person getParent(Person c) {
        // Base case
        for (RepostTree rt : children) {
            if (rt.root == c) return root;
        }

        // Recursive case - ask children to look
        for (RepostTree rt : children) {
            Person parent = rt.getParent(c);
            if (parent != null) return parent;
        }

        return null; //Not found
    }

    /** If either child1 or child2 is null or is not in this RepostTree, return null.
     * Otherwise, return the person at the root of the smallest subtree of this
     * RepostTree that contains child1 and child2.
     * <p>
     * Examples. For the following tree (which does not contain H):
     * <p>
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.getSharedAncestor(B, A) is A
     * A.getSharedAncestor(B, B) is B
     * A.getSharedAncestor(B, C) is A
     * A.getSharedAncestor(A, C) is A
     * A.getSharedAncestor(E, F) is C
     * A.getSharedAncestor(G, F) is A
     * B.getSharedAncestor(B, E) is B
     * B.getSharedAncestor(B, A) is null
     * B.getSharedAncestor(D, F) is null
     * B.getSharedAncestor(D, H) is null
     * A.getSharedAncestor(null, C) is null
     */
    public Person getSharedAncestor(Person child1, Person child2) {
    	if(child1==null || child2==null) return null;
    	List<Person> l1= getRepostRoute(child1);
    	List<Person> l2= getRepostRoute(child1);
    	if(l1==null || l2==null)return null;
    	if(l1.get(0)!=l2.get(0)) return null;
//    	Iterator<Person> it1 = l1.iterator();
//    	Iterator<Person> it2 = l2.iterator();
//    	Person prev1 = l1.get(0);
//    	Person p1=prev1,p2=l2.get(0);
//    	while(it1.hasNext() && it2.hasNext()) {
//    		p1 = it1.next();
//    		p2 = it2.next();
//    		if(p1!=p2) return prev1;
//    		prev1=p1;
//    	}
//    	if(it1.hasNext() || it2.hasNext()) {
//    		if(p1==p2)return p1;
//    	}
    	int i=1;
    	for(i=1; i<Math.min(l2.size(), l1.size()); i++) {
    		if(l1.get(i)!=l2.get(i)) return l1.get(i-1);
    	}
    	if(l1.get(i-1)==l2.get(i-1)) return l1.get(i-1);
        return null;
    }

    /** Return a (single line) String representation of this RepostTree.
     * If this RepostTree has no children (it is a leaf), return the root's substring.
     * Otherwise, return
     * root's substring + SEPARATOR + START_CHILDREN_DELIMITER + each child's
     * toString, separated by DELIMITER, followed by END_CHILD_DELIMITER.
     * Make sure there is not an extra DELIMITER following the last child.
     * <p>
     * Finally, make sure to use the static final fields declared at the top of
     * RepostTree.java.
     * <p>
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *         \
     * 3        G
     * A.toString() should print:
     * (A) - HEALTHY - [(C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]], (B) - HEALTHY - [(D) - HEALTHY]]
     * <p>
     * C.toString() should print:
     * (C) - HEALTHY - [(F) - HEALTHY, (E) - HEALTHY - [(G) - HEALTHY]]
     */
    public String toString() {
        if (children.isEmpty()) return root.toString();
        String s = root.toString() + SEPARATOR + START_CHILDREN_DELIMITER;
        for (RepostTree dt : children) {
            s = s + dt.toString() + DELIMITER;
        }
        return s.substring(0, s.length() - 2) + END_CHILDREN_DELIMITER;
    }


    /** Return a verbose (multi-line) string representing this RepostTree. */
    public String toStringVerbose() {
        return toStringVerbose(0);
    }

    /** Return a verbose (multi-line) string representing this RepostTree.
     * Each person in the tree is on its own line, with indentation representing
     * what each person is a child of.
     * indent is the the amount of indentation to put before this line.
     * Should increase on recursive calls to children to create the above pattern.
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *         \
     * 3        G
     * <p>
     * A.toStringVerbose(0) should return:
     * (A) - HEALTHY
     *    (C) - HEALTHY
     *       (F) - HEALTHY
     *       (E) - HEALTHY
     *         (G) - HEALTHY
     *    (B) - HEALTHY
     *        (D) - HEALTHY
     * <p>
     * Make sure to use VERBOSE_SPACE_INCREMENT for indentation.
     */
    private String toStringVerbose(int indent) {
        String s = "";
        for (int i = 0; i < indent; i++) {
            s = s + VERBOSE_SPACE_INCREMENT;
        }
        s = s + root.toString();

        if (children.isEmpty()) return s;

        for (RepostTree dt : children) {
            s = s + "\n" + dt.toStringVerbose(indent + 1);
        }
        return s;
    }
    
    /** Return true iff st equals some member of s2 */
    private boolean help(RepostTree st, Set<RepostTree> s2) {
    	for(RepostTree rt:s2) {
    		if(rt.equals(st)) return true;
    	}
    	return false;
    }

    /** Return true iff this is equal to ob.
     * If ob is not a RepostTree, it cannot be equal to this RepostTree, return false.
     * Two RepostTrees are equal if they are the same object (==) or:
     * <br> - they have the same root Person object (==)
     * <br> - their children sets are the same size:
     * <br> --their children sets are equal, which, since their sizes
     * <br> --are equal, requires:
     * <br> --- for every RepostTree rt in one set there is a RepostTree rt2
     *            in the other set for which rt.equals(rt2) is true.
     * <p>
     * Otherwise the two RepostTrees are not equal.
     * Do not use any of the toString functions to write equals(). */
    public boolean equals(Object ob) {
    	if(ob.getClass()!= RepostTree.class) return false;
    	RepostTree obj = (RepostTree)ob;
    	if(root!= obj.root) return false;
    	if(getChildrenCount()!=obj.getChildrenCount()) return false;
    	for(RepostTree rt: children) {
    		if(!help(rt,obj.children)) return false;
    	}
        return true;

    }



}
