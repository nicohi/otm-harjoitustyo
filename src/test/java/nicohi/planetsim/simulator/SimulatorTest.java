package nicohi.planetsim.simulator;

import java.util.ArrayList;
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

	@Test
	public void testTicktime() {
		sim.setTickTime(1);
		assertEquals(1.0, sim.getTickTime(), 0.2);
	}

	@Test
	public void testMotion() {
		sim.setTickTime(1);
		sim.getPlanets().add(new Planet(new Vector(), new Vector(0,0,1), 1));
		sim.tick();
		assertEquals(1.0, sim.getPlanets().get(0).getPos().getZ(), 0.2);
	}

	@Test
	public void testCollision() {
		sim.setTickTime(1);
		sim.getPlanets().add(new Planet(new Vector(), new Vector(0,0,1), 1000));
		sim.getPlanets().add(new Planet(new Vector(), new Vector(0,0,1), 1000));
		sim.tick();
		sim.tick();
		sim.tick();
		assertEquals(2000.0, sim.getPlanets().get(0).getM(), 0.2);
		assertEquals(1.0, sim.getPlanets().get(0).getVel().getZ(), 0.2);
	}
	
}
