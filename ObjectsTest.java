import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhDTest {

	@Test
	void testConstructor1() {
		PhD jaime= new PhD("Jaime Luengo",12,2019);
		assert("Jaime Luengo"==jaime.name());
		assert(12==jaime.month());
		assert(2019==jaime.year());
		assert(null==jaime.advisor1());
		assert(null==jaime.advisor2());
		assert(0==jaime.numAdvisees());
	}
	
	@Test
	void testSetters() {
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
	void testAdvisors() {
		PhD David= new PhD("David Gries",1,1973);
		PhD Lebron= new PhD("Lebron James",11,1995,David);
		PhD jaime= new PhD("Jaime Luengo",12,2019,Lebron,David);
		assert(Lebron.advisor1()==David);
		assert(Lebron.numAdvisees()==1);
		assert(David.numAdvisees()==2);
		assert(jaime.advisor1()==Lebron);
		assert(jaime.advisor2()==David);
	}
	
	@Test
	void testSiblingsAndAfter(){
		PhD feb77= new PhD("feb77", 2, 1977);
        PhD jan77= new PhD("jan77", 1, 1977, feb77);
		PhD march77= new PhD("march77", 3, 1977);
		
		PhD feb78= new PhD("feb78", 2, 1978);
        PhD jan78= new PhD("jan78", 1, 1978);
		PhD march78= new PhD("march78", 3, 1978);
		
		PhD feb76= new PhD("feb76", 2, 1976);
        PhD jan76= new PhD("jan76", 1, 1976);
		PhD march76= new PhD("march76", 3, 1976);
		
        assertEquals(false,jan77.hasAdvisee());
        assertEquals(true,feb77.hasAdvisee());
        
        assertEquals(false,feb77.gotAfter(feb77));
        assertEquals(false,feb77.gotAfter(null));
        assertEquals(true,feb77.gotAfter(jan77));
        assertEquals(false,feb77.gotAfter(march77));
        
        assertEquals(false,feb77.gotAfter(feb78));
        assertEquals(false,feb77.gotAfter(jan78));
        assertEquals(false,feb77.gotAfter(march78));
        
        assertEquals(true,feb77.gotAfter(feb76));
        assertEquals(true,feb77.gotAfter(jan76));
        assertEquals(true,feb77.gotAfter(march76));        
        
        PhD Lebron = new PhD("Lebron James", 1, 1995, feb77);
        PhD Martin = new PhD("Martin Harris", 2, 1998, feb77);
        PhD john = new PhD("john", 2, 1998, jan76, feb77);
        PhD steve = new PhD("steve", 2, 1998, feb77,jan77);
        PhD mark = new PhD("john", 2, 1998, jan76, feb77);
        PhD flynn = new PhD("steve", 2, 1998, feb77,jan77);
        PhD ross = new PhD("ross", 2, 1998, jan76, feb77);
        PhD baldick = new PhD("baldick", 2, 1998, jan77, feb77); 
        
        
        
        assertEquals(false, Lebron.areSiblings(Lebron));
        assertEquals(false, feb77.areSiblings(feb77));
        assertEquals(true, Lebron.areSiblings(Martin));

        assertEquals(true, steve.areSiblings(john));

        assertEquals(true, mark.areSiblings(flynn));

        assertEquals(true, ross.areSiblings(baldick));
        
        
        
	}
	

}
