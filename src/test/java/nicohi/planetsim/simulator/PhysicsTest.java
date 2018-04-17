package nicohi.planetsim.simulator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PhysicsTest {
	Physics phys;
	Vector v1;
	Vector v2;
	
	public PhysicsTest() {
	}
	
	@Before
	public void setUp() {
		phys = new Physics();
		v1 = new Vector(1, 0);
		v2 = new Vector();
	}

	@Test
	public void unitVectorTest() {
		assertEquals(phys.unitVector(v1).getX(), 1.0, 0.1);
	}
	
	@Test
	public void vectorSumTest() {
		assertEquals(phys.vectorSum(v1, v2).getX(), 1.0, 0.1);
	}
}
