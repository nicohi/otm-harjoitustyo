package nicohi.planetsim.simulator;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlanetDBTest {
	ArrayList<Planet> ps;
	
	public PlanetDBTest() {
	}
	
	@Before
	public void setUp() {
		PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/testdb/");
		db.clear();
		ps = new ArrayList<>();
		ps.add(new Planet(100));
		db.stop();
	}
	
	@Test
	public void testSave() {
		PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/testdb/");
		db.save(ps);
		db.stop();
	}

	@Test
	public void testClear() {
		PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/testdb/");
		db.save(ps);
		db.clear();
		ArrayList<Planet> ps2 = db.load();
		db.stop();
		assertTrue(ps2.isEmpty());
	}

	@Test
	public void testLoad() {
		PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/testdb/");
		db.clear();
		db.save(ps);
		ArrayList<Planet> ps2 = db.load();
		db.stop();
		assertEquals(ps.get(0), ps2.get(0));
	}
}
