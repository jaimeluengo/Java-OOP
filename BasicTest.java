import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhDTest {

	@Test
	void test1() {
		PhD john= new PhD("Jaime Luengo",4,2019);
		assert("Jaime Luengo",john.name());
		assert(4,john.month());
		assert(2019,john.year());
		assert(null,john.advisor1());
		assert(null,john.advisor2());
		assert(0,john.numAdvisees());
	}

}
