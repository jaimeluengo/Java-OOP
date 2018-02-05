import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhDTest {

	@Test
	void testGroupA() {
		PhD jaime= new PhD("Jaime Luengo",12,2019);
		assert("Jaime Luengo"==jaime.name());
		assert(12==jaime.month());
		assert(2019==jaime.year());
		assert(null==jaime.advisor1());
		assert(null==jaime.advisor2());
		assert(0==jaime.numAdvisees());
	}
	
	@Test
	void testGroupB() {
		PhD Lebron= new PhD("Lebron James",11,1995);
		PhD David= new PhD("David Gries",1,1973);
		PhD jaime= new PhD("Jaime Luengo",12,2019);
		jaime.setAdvisor1(Lebron);
		jaime.setAdvisor2(David);
		assertEquals(jaime.advisor1(),Lebron);
		assertEquals(jaime.advisor2(),David);
		assert(1==Lebron.numAdvisees());
		assert(1==David.numAdvisees());
	}
	
	@Test
	void testGroupC() {
		PhD David= new PhD("David Gries",1,1973);
		PhD Lebron= new PhD("Lebron James",11,1995,David);
		PhD jaime= new PhD("Jaime Luengo",12,2019,Lebron,David);
		assert(Lebron.advisor1()==David);
		assert(Lebron.numAdvisees()==1);
		assert(David.numAdvisees()==2);
		assert(jaime.advisor1()==Lebron);
		assert(jaime.advisor2()==David);
	}

}
