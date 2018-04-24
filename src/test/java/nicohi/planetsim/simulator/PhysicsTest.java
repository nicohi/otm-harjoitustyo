package nicohi.planetsim.simulator;

import java.util.ArrayList;
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
		assertEquals(1.0, phys.unitVector(v1).getX(), 0.1);
	}
	
	@Test
	public void vectorSumTest() {
		assertEquals(1.0, phys.vectorSum(v1, v2).getX(), 0.1);
	}

	@Test
	public void vectorXScalarTest1() {
		Vector vRes = phys.vectorScalarProduct(2, v1);
		assertEquals(2.0, vRes.getX(), 0.1);
		assertEquals(0.0, vRes.getY(), 0.1);
		assertEquals(0.0, vRes.getZ(), 0.1);
	}

	@Test
	public void vectorSumTest1() {
		Vector vRes = phys.vectorSum(v2, v1);
		assertEquals(1.0, vRes.getX(), 0.1);
		assertEquals(0.0, vRes.getY(), 0.1);
		assertEquals(0.0, vRes.getZ(), 0.1);
	}

	@Test
	public void vectorSumTest2() {
		ArrayList<Vector> vs = new ArrayList<>();
		vs.add(v1);
		vs.add(v2);
		Vector vRes = phys.vectorSum(vs);
		assertEquals(1.0, vRes.getX(), 0.1);
		assertEquals(0.0, vRes.getY(), 0.1);
		assertEquals(0.0, vRes.getZ(), 0.1);
	}
}
