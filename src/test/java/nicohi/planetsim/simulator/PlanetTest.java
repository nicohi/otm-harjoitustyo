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
		assertEquals(p.getM(), 1.0, 0.0);
	}
	
	@Test
	public void posX() {
		assertEquals(p.getPos().getX(), 0.0, 0.0);
	}

	@Test
	public void posY() {
		assertEquals(p.getPos().getY(), 0.0, 0.0);
	}

	@Test
	public void posZ() {
		assertEquals(p.getPos().getZ(), 0.0, 0.0);
	}
}
