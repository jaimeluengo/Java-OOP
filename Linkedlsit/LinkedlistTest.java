package LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DLListTest {

	@Test
	void PrependAppendStringTest() {
		DLList<Integer> loco= new DLList<Integer>();
		int[] list1= {7,3,4,5,6};
		for(int i=0;i<5;i++) {
			loco.prepend(list1[i]);
			loco.append(list1[i]);
		}
		assertEquals(10,loco.size());
		assertEquals("[6, 5, 4, 3, 7, 7, 3, 4, 5, 6]",loco.toString());
		assertEquals("[6, 5, 4, 3, 7, 7, 3, 4, 5, 6]",loco.gnirtSot());
	}
	
	@Test
	void getNodeTest() {
		DLList<Integer> loco= new DLList<Integer>();
		int[] list1= {7,3,4,5,6};
		for(int i=0;i<5;i++) {
			loco.prepend(list1[i]);
			loco.append(list1[i]);
		}
		//create node for testing that actually points to the node to be tested
		DLList<Integer>.Node node_c= loco.last().prev().prev();
		assertEquals(node_c,loco.getNode(7));
		DLList<Integer>.Node node_b= loco.first().next();
		assertEquals(node_b,loco.getNode(1));
	}
	
	@Test
	void deleteTest(){
		DLList<Integer> loco= new DLList<Integer>();
		int[] list1= {7,3,4,5,6};
		for(int i=0;i<5;i++) {
			loco.prepend(list1[i]);
		}
		DLList<Integer>.Node test =loco.getNode(2);
		loco.delete(test);
		assertEquals("[6, 5, 3, 7]",loco.toString());
		loco.delete(loco.last());
		assertEquals("[6, 5, 3]",loco.toString());
		loco.delete(loco.first());
		assertEquals("[5, 3]",loco.toString());
		assertEquals(2,loco.size());
		DLList<Integer> one= new DLList<Integer>();
		one.append(list1[0]);
		DLList<Integer>.Node oneTest=one.first();
		one.delete(oneTest);
		assertEquals("[]",one.toString()); 
		assertEquals(0,one.size());
	}
	
	@Test
	void insertAfterTest() {
		DLList<Integer> loco= new DLList<Integer>();
		int[] list1= {7,3,4,5,6};
		for(int i=0;i<5;i++) {
			loco.prepend(list1[i]);
		}
		assertEquals(5,loco.size());
		DLList<Integer>.Node test =loco.getNode(3);
		loco.insertAfter(11, test);
		assertEquals("[6, 5, 4, 3, 11, 7]",loco.toString());
		assertEquals(6,loco.size());
		test=loco.last();
		loco.insertAfter(11, test);
		assertEquals("[6, 5, 4, 3, 11, 7, 11]",loco.toString());
		assertEquals(7,loco.size());
	}

}
