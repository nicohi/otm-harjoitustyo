package nicohi.planetsim.simulator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VectorTest {
	Vector v1;	
	Vector v2;	
	
	public VectorTest() {
	}
	
	@Before
	public void setUp() {
		v1 = new Vector(1, 2);
		v2 = new Vector(1, 2, 3);
	}

	@Test
	public void testToString1() {
		assertEquals(v1.toString(), "Vector{x=1.0, y=2.0, z=0.0}");
	}
	
	@Test
	public void testToString2() {
		assertEquals(v2.toString(), "Vector{x=1.0, y=2.0, z=3.0}");
	}
}
