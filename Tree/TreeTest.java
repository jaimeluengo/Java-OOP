import static org.junit.Assert.*;
import static common.JUnitUtil.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.junit.BeforeClass;
import org.junit.Test;

public class RepostTreeTest {

    private static Network n;
    private static Person[] people;

    @BeforeClass
    public static void setup(){
        n= new Network();
        people= new Person[]{new Person("A", n, 0), new Person("B", n, 0), new Person("C", n, 0),
                new Person("D", n, 0), new Person("E", n, 0), new Person("F", n, 0),
                new Person("G", n, 0), new Person("H", n, 0), new Person("I", n, 0),
                new Person("J", n, 0), new Person("K", n, 0), new Person("L", n, 0)
        };
    }

    @Test
    public void testBuiltInGetters() {
        RepostTree st= new RepostTree(people[1]);
        assertEquals("B", toStringBrief(st));
    }
    
    
    
    /** Create a RepostTree with structure A[B[D E F[G[H[I]]]] C]
     * Doesn't rely on the add(..) method of SharingTree. */ 
    private RepostTree makeTree1() {
        RepostTree dt = new RepostTree(people[0]); // A
        dt.insert(people[1], people[0]); // A, B
        dt.insert(people[2], people[0]); // A, C
        dt.insert(people[3], people[1]); // B, D
        dt.insert(people[4], people[1]); // B, E
        dt.insert(people[5], people[1]); // B, F
        dt.insert(people[6], people[5]); // F, G
        dt.insert(people[7], people[6]); // G, H
        dt.insert(people[8], people[7]); // H, I
        return new RepostTree(dt); //Clone over to BugTree
    }
    
    @Test
    public void testMakeTree1() {
        RepostTree dt= makeTree1();
        assertEquals("A[B[D E F[G[H[I]]]] C]", toStringBrief(dt)); 
    }

    @Test
    public void testInsert() {
        RepostTree st= new RepostTree(people[1]); 

        //Test add to root
        RepostTree dt2= st.insert(people[2], people[1]);
        assertEquals("B[C]", toStringBrief(st));
    }
    
    @Test
    public void testSize() {
        RepostTree st= new RepostTree(people[1]);
        st.insert(people[2], people[1]);
        st.insert(people[3], people[1]);
        st.insert(people[4], people[2]);
        st.insert(people[5], people[2]);
        st.insert(people[6], people[4]);
        st.insert(people[7], people[5]);
        st.insert(people[8], people[4]);
        st.insert(people[9], people[5]);
        assertEquals(9, st.size());
    }
    
    @Test
    public void testDepth() {
        RepostTree st= new RepostTree(people[1]);
        st.insert(people[2], people[1]);
        st.insert(people[3], people[1]);
        st.insert(people[4], people[2]);
        st.insert(people[5], people[4]);
        st.insert(people[6], people[4]);
        assertEquals(3, st.depth(people[6]));
        assertEquals(-1, st.depth(people[11]));
    }
    
    @Test
    public void testWidthAtDepth() {
        RepostTree st= new RepostTree(people[1]);
        st.insert(people[2], people[1]);
        st.insert(people[3], people[1]);
        st.insert(people[4], people[2]);
        st.insert(people[5], people[2]);
        st.insert(people[6], people[4]);
        st.insert(people[7], people[5]);
        st.insert(people[8], people[4]);
        st.insert(people[9], people[5]);
        assertEquals(4, st.widthAtDepth(3));
    }
    
    @Test
    public void testGetRepostRoute() {
        RepostTree st= new RepostTree(people[1]);
        st.insert(people[2], people[1]);
        st.insert(people[3], people[1]);
        st.insert(people[4], people[2]);
        st.insert(people[5], people[2]);
        st.insert(people[6], people[4]);
        st.insert(people[7], people[5]);
        st.insert(people[8], people[4]);
        st.insert(people[9], people[5]);
        List<Person> ll= new LinkedList<>();
        ll.add(people[1]);
        ll.add(people[2]);
        ll.add(people[4]);
        ll.add(people[8]);
        assertEquals(ll, st.getRepostRoute(people[8]));
        List<Person> ll2= new LinkedList<>();
        ll2.add(people[1]);
        ll2.add(people[3]);
        List<Person> ll3= new LinkedList<>();
        ll3.add(people[1]);
        assertEquals(ll3, st.getRepostRoute(people[1]));
        assertEquals(ll2, st.getRepostRoute(people[3]));
        assertEquals(null, st.getRepostRoute(people[11]));
        
    }
    
    @Test
    public void testGetSharedAncestor() {
        RepostTree st= makeTree1();
        // A.testSharedAncestorOf(A, A) is A
        assertEquals(people[0], st.getSharedAncestor(people[0], people[0]));
    }
    
    @Test
    public void testEquals() {
        RepostTree st= new RepostTree(people[1]);
        st.insert(people[2], people[1]);
        st.insert(people[3], people[1]);
        st.insert(people[4], people[2]);
        st.insert(people[5], people[2]);
        st.insert(people[6], people[4]);
        st.insert(people[7], people[5]);
        st.insert(people[8], people[4]);
        st.insert(people[9], people[5]);
        
        RepostTree st2= new RepostTree(people[1]);
        st2.insert(people[2], people[1]);
        st2.insert(people[3], people[1]);
        st2.insert(people[4], people[2]);
        st2.insert(people[5], people[2]);
        st2.insert(people[6], people[4]);
        st2.insert(people[7], people[5]);
        st2.insert(people[8], people[4]);
        //Missed last element
        assertEquals(false, st.equals(st2));
        assertEquals(true, st.equals(st));
        assertEquals(true, st2.equals(st2));
    }
    
    
    
    /** Return a representation of this tree. This representation is:
     * (1) the name of the Person at the root, followed by
     * (2) the representations of the children (in alphabetical
     *     order of the children's names).
     * There are two cases concerning the children.
     *
     * No children? Their representation is the empty string.
     * Children? Their representation is the representation of each child, with
     * a blank between adjacent ones and delimited by "[" and "]".
     * Examples:
     * One-node tree: "A"
     * root A with children B, C, D: "A[B C D]"
     * root A with children B, C, D and B has a child F: "A[B[F] C D]"
     */
    public static String toStringBrief(RepostTree t) {
        String res= t.getRoot().getName();

        Object[] childs= t.getChildren().toArray();
        if (childs.length == 0) return res;
        res= res + "[";
        selectionSort1(childs);

        for (int k= 0; k < childs.length; k= k+1) {
            if (k > 0) res= res + " ";
            res= res + toStringBrief(((RepostTree)childs[k]));
        }
        return res + "]";
    }

    /** Sort b --put its elements in ascending order.
     * Sort on the name of the Person at the root of each SharingTree
     * Throw a cast-class exception if b's elements are not SharingTrees */
    public static void selectionSort1(Object[] b) {
        int j= 0;
        // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
        // 0---------------j--------------- b.length
        // inv : b | sorted, <= | >= |
        // --------------------------------
        while (j != b.length) {
            // Put into p the index of smallest element in b[j..]
            int p= j;
            for (int i= j+1; i != b.length; i++) {
                String bi= ((RepostTree)b[i]).getRoot().getName();
                String bp= ((RepostTree)b[p]).getRoot().getName();
                if (bi.compareTo(bp) < 0) {
                    p= i;

                }
            }
            // Swap b[j] and b[p]
            Object t= b[j]; b[j]= b[p]; b[p]= t;
            j= j+1;
        }
    }

}
