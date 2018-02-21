package LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DLListTest {

	@Test
	void PrependAppendStringTest() {
		DLList loco= new DLList<Integer>();
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
		DLList loco= new DLList<Integer>();
		int[] list1= {7,3,4,5,6};
		for(int i=0;i<5;i++) {
			loco.prepend(list1[i]);
			loco.append(list1[i]);
		}
		DLList<Integer>.Node node_c= loco.last().prev().prev();
		assertEquals(node_c,loco.getNode(7));
		DLList<Integer>.Node node_b= loco.first().next();
		assertEquals(node_b,loco.getNode(1));
	}

}
