package nicohi.planetsim.simulator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlanetTest {
	Planet p;
	
	public PlanetTest() {
	}
	
	@Before
	public void setUp() {
		p = new Planet(1);
	}

	@Test
	public void mass() {
		assertEquals(1.0, p.getM(), 0.0);
	}
	
	@Test
	public void posX() {
		assertEquals(0.0, p.getPos().getX(), 0.0);
	}

	@Test
	public void posY() {
		assertEquals(0.0, p.getPos().getY(), 0.0);
	}

	@Test
	public void posZ() {
		assertEquals(0.0, p.getPos().getZ(), 0.0);
	}

	@Test
	public void testName() {
		Planet pN = new Planet(1);
		pN.setName("test");
		assertEquals("test", pN.getName());
	}

	@Test
	public void testRadius() {
		Planet pN = new Planet(10);
		assertEquals(1, pN.radius(), 0.2);
	}

	@Test
	public void planetEqualityTest1() {
		Planet pCopy = new Planet(p.getM());
		pCopy.setName(p.getName());
		assertTrue(p.equals(pCopy));
		assertTrue(p.equals(p));
		assertFalse(p.equals(null));
		assertFalse(p.equals(new Vector()));

		//change mass of pCopy
		pCopy.setM(110);
		assertFalse(p.equals(pCopy));
		//change name of pCopy
		pCopy.setM(p.getM());
		pCopy.setName("test");
		assertFalse(p.equals(pCopy));
	}
}
