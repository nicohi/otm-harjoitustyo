package nicohi.planetsim.simulator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulatorTest {
	Simulator sim;

	public SimulatorTest() {
	}
	
	@Before
	public void setUp() {
		sim = new Simulator();
	}

	@Test
	public void planetAddedCorrectly() {
		sim.getPlanets().add(new Planet(10));
		assertEquals(10, sim.getPlanets().get(0).getM(), 1);
	}
	
}
