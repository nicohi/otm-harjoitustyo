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
		assertEquals("Vector{x=1.0, y=2.0, z=0.0}", v1.toString());
	}
	
	@Test
	public void testToString2() {
		assertEquals("Vector{x=1.0, y=2.0, z=3.0}", v2.toString());
	}

	@Test
	public void testSetXYZ() {
		Vector v3 = new Vector(1, 2, 3);
		v3.setX(0);
		v3.setY(0);
		v3.setZ(0);
		assertEquals("Vector{x=0.0, y=0.0, z=0.0}", v3.toString());
	}
}
