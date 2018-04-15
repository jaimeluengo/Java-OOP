/** NetId(s): jl3752. Time spent: 3 hours, 20 minutes.
 *
 * Please be careful in replacing nnnn by netids and hh by hours and
 * mm by minutes. Any mistakes cause us to have to fix this manually
 * before extracting times, in order to give you max and median and mean
 * Thanks
 * 
 * Name(s):
 * What I thought about this assignment:
 * Really interesting
 *
 */

import java.lang.reflect.Array;
import java.util.*;

/** Please keep up with the pinned Piazza note Assignment A5.
 *  Study the slides of the Lecture on Priority Queues and Heaps
 *  and the A5 handout before starting on this assignment.
 *  It does no good to start programming without fully understanding
 *  the Class Invariant. */

/** An instance is a min-heap or a max-heap of distinct values of type E
 *  with priorities of type double. */
public class Heap<E> {

    /** Class Invariant:
     *   1. d[0..size-1] represents a complete binary tree. d[0] is the root;
     *      For each k, d[2k+1] and d[2k+2] are the left and right children of d[k].
     *      If k != 0, d[(k-1)/2] (using integer division) is the parent of d[k].
     *   
     *   2. For k in 0..size-1, d[k] contains the value and its priority.
     *   
     *   3. The values in d[0..size-1] are all different.
     *   
     *   4. For k in 1..size-1,
     *      if isMinHeap, (d[k]'s priority) >= (d[k]'s parent's priority),
     *      otherwise,    (d[k]'s priority) <= (d[k]'s parent's priority).
     *   
     *   map and the tree are in sync, meaning:
     *   
     *   5. The keys of map are the values in d[0..size-1].
     *      This implies that this.size = map.size().
     *   
     *   6. if value v is in d[k], then map.get(v) returns k.
     */
    protected final boolean isMinHeap;
    protected VP[] d;
    protected int size;
    protected HashMap<E, Integer> map;

    /** Constructor: an empty heap with capacity 10.
     *  It is a min-heap if isMin is true, a max-heap if isMin is false. */
    public Heap(boolean isMin) {
        isMinHeap= isMin;
        d= createVPArray(10);
        map= new HashMap<E, Integer>();
    }

    /** A VP object houses a value and a priority. */
    class VP {
        E val;             // The value
        double priority;   // The priority

        /** An instance with value v and priority p. */
        VP(E v, double p) {
            val= v;
            priority= p;
        }

        /** Return a representation of this VP object. */
        @Override public String toString() {
            return "(" + val + ", " + priority + ")";
        }
    }

    /** Add v with priority p to the heap.
     *  Throw an illegalArgumentException if v is already in the heap.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap. */
    public void add(E v, double p) throws IllegalArgumentException {
        // TODO #1: Write this whole method. Note that bubbleUp is not implemented,
        // so calling it has no effect (yet). The first tests of add, using
        // test00Add, ensure that this method maintains fields d and map properly,
        // without worrying about bubbling up.
        if (map.containsKey(v)) { 
            throw new IllegalArgumentException("Value already in the heap");
        }
        ensureSpace();
        size++;
        d[size-1]=new VP(v,p);
        map.put(v, size-1);
        bubbleUp(size-1);
        // Testing procedure test00Add should work. Look at its specification.
        
        // Do NOT call bubbleUp until the class invariant is true except
        // for the need to bubble up.
        // Calling bubbleUp is the last thing to be done.
        
    }

    /** If size = length of d, double the length of array d.
     *  The worst-case time is proportional to the length of d. */
    protected void ensureSpace() {
        //TODO #2. Any method that increases the size of the heap must call
        // this method first. 
        //
        // Use method copyOf in class java.util.Arrays
        // to create the larger array and do the copying.
        // Look at the specification of java.util.Arrays.
        //
        // If you write this method correctly AND method
        // add calls this method appropriately, testing procedure
        // test10ensureSpace will not find errors.
    	if(size==d.length) d= Arrays.copyOf(d, 2*size);
    }

    /** Return the size of this heap.
     *  This operation takes constant time. */
    public int size() { // Do not change this method
        return size;
    }

    /** Swap d[h] and d[k].
     *  Precondition: 0 <= h < heap-size, 0 <= k < heap-size. */
    void swap(int h, int k) {
        assert 0 <= h  &&  h < size  &&  0 <= k  &&  k < size;
        //TODO 3: When bubbling values up and (later on) down, two values,
        // say d[h] and d[k], will have to be swapped. At the same time,
        // the definition of map has to be maintained.
        // In order to always get this right, use method swap for this.
        // Method swap is tested by testing procedure test13Swap --it will
        // find no errors if you write this method properly.
        // 
        // Read the Assignment A5 note about map.put(...).
    	VP n = d[h];
    	map.put(d[k].val,h);//update child address to parent's
    	d[h] = d[k];
    	map.put(n.val,k);//update parent address to child's
    	d[k] = n;

    }
    
    /** If a value with priority p1 should be above a value with priority
     *       p2 in the heap, return 1.
     *  If priority p1 and priority p2 are the same, return 0.
     *  If a value with priority p1 should be below a value with priority
     *       p2 in the heap, return -1.
     *  This is based on what kind of a heap this is,
     *  E.g. a min-heap, the value with the smallest priority is in the root.
     *  E.g. a max-heap, the value with the largest priority is in the root.
     */
    public int compareTo(double p1, double p2) {
        if (p1 == p2) return 0;
        if (isMinHeap) {
            return p1 < p2 ? 1 : -1;
        }
        return p1 > p2 ? 1 : -1;
    }

    /** If d[m] should be above d[n] in the heap, return 1.
     *  If d[m]'s priority and d[n]'s priority are the same, return 0.
     *  If d[m] should be below d[n] in the heap, return -1.
     *  This is based on what kind of a heap this is,
     *  E.g. a min-heap, the value with the smallest priority is in the root.
     *  E.g. a max-heap, the value with the largest priority is in the root.
     */
    public int compareTo(int m, int n) {
        return compareTo(d[m].priority, d[n].priority);
    }

    /** Bubble d[k] up the heap to its right place.
     *  Precondition: 0 <= k < size and
     *       The class invariant is true, except perhaps
     *       that d[k] belongs above its parent (if k > 0)
     *       in the heap, not below it. */
    void bubbleUp(int k) {
        // TODO #4 This method should be called within add in order
        // to bubble a value up to its proper place, based on its priority.
        // Do not use recursion. Use iteration.
        // Use method compareTo to test whether value k is in its right place.
        // If this method is written properly, testing procedure
        // test15Add_BubbleUp() will not find any errors.
        assert 0 <= k  &&  k < size;
        int p =(k-1)/2;
        while(k>0 && compareTo(d[k].priority,d[p].priority)>0) {
        	swap(k,p);
        	k=p;
        	p =(k-1)/2;
        }

    }

    /** If this is a min-heap, return the heap value with lowest priority.
     *  If this is a max-heap, return the heap value with highest priority
     *  Do not change the heap. This operation takes constant time.
     *  Throw a NoSuchElementException if the heap is empty. */
    public E peek() {
        // TODO 5: Do peek. This is an easy one. If it is correct,
        //         test25MinPeek() and test25MaxPeek will show no errors.
    	if(size==0) throw new NoSuchElementException("Heap empty.");
    	return d[0].val;
    }

    /** Bubble d[k] down in heap until it finds the right place.
     *  If there is a choice to bubble down to both the left and
     *  right children (because their priorities are equal), choose
     *  the right child.
     *  Precondition: 0 <= k < size   and
     *           Class invariant is true except that perhaps
     *           d[k] belongs below one or both of its children. */
    void bubbleDown(int k) {
        // TODO 6: We suggest implementing and using upperChildOf, though
        //         you don't have to. DO NOT USE RECURSION. Use iteration.
        //         When this method is correct, testing procedures
        //         test30MinBubbledown and test31MinBubbledown and
        //         test31MaxBubbledown will not find errors.
        assert 0 <= k  &&  k < size;
        int c = upperChild(k);
        while(c!=k && compareTo(d[c].priority,d[k].priority)>0) {
        	swap(c,k);
        	k = c;
        	c = upperChild(k);
        }
    }

    /** If d[n] doesn't exist or has no child, return n.
     *  If d[n] has one child, return its index.
     *  If d[n] has two children with the same priority, return the
     *      index of the right one.
     *  If d[n] has two children with different priorities return the
     *      index of the one that must appear above the other in a heap. */
    protected int upperChild(int n) {
        if(n>size-1 || 2*n+1>size-1) return n;
        if(2*n+2>size-1) return 2*n+1;
        if(d[2*n+1].val==d[2*n+2].val) return 2*n+2;
        if(compareTo(d[2*n+1].priority,d[2*n+2].priority)>0) return 2*n+1;
        return 2*n+2;
    }

    /** If this is a min-heap, remove and return heap value with lowest priority.
     * If this is a max-heap, remove and return heap value with highest priority.
     * The expected time is logarithmic and the worst-case time is linear
     * in the size of the heap.
     * Throw a NoSuchElementException if the heap is empty. */
    public E poll() {
        // TODO 7: When this method is written correctly, testing procedure
        //         test30Poll_BubbleDown_NoDups will not find errors.
        // 
        //         Note also testing procedure test40DuplicatePriorities
        //         This method tests to make sure that when bubbling up or down,
        //         two values with the same priority are not swapped.
    	if(size==0) throw new NoSuchElementException("Heap is empty.");
    	E n = d[0].val;
    	map.put(d[size-1].val, 0);
    	d[0]=d[size-1];
    	map.remove(n);
    	size--;
    	if(size>0) bubbleDown(0);
        return n;
    }

    /** Change the priority of value v to p.
     *  The expected time is logarithmic and the worst-case time is linear
     *  in the size of the heap.
     *  Throw an IllegalArgumentException if v is not in the heap. */
    public void updatePriority(E v, double p) {
        // TODO  8: When this method is correctly implemented, testing procedure
        //          test50updatePriority() won't find errors.
    	if(!map.containsKey(v))throw new IllegalArgumentException("Not found");
    	int k = map.get(v);
        d[k].priority = p;
        if(k>0) bubbleUp(k);
        if(map.get(v)==k) bubbleDown(k);

    }

    /** Create and return an array of size n.
     *  This is necessary because generics and arrays don't interoperate nicely.
     *  A student in CS2110 would not be expected to know about the need
     *  for this method and how to write it. We had to search the web to
     *  find out how to do it. */
    VP[] createVPArray(int n) {
        return (VP[]) Array.newInstance(VP.class, n);
    }
}
