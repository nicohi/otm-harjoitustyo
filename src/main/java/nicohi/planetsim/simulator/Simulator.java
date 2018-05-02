package nicohi.planetsim.simulator;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Nicolas Hiillos
 */
public class Simulator {
	ArrayList<Planet> planets;
	//seconds per tick
	double tickTime;
	Physics phys;

	/**
	 * Constructor
	 */
	public Simulator() {
		this.planets = new ArrayList<>();
		this.tickTime = 20;
		this.phys = new Physics();
	}

	/**
	 * Progresses the simulation by the amount of time set as tickTime. Sets vectors of planets to new values.
	 */
	public void tick() {
		planets.stream().forEach(p -> p.setNetF(newNetF(p, planets)));
		planets.stream().forEach(p -> p.setAcc(newAcc(p)));
		planets.stream().forEach(p -> p.setVel(newVel(p)));
		planets.stream().forEach(p -> p.setPos(newPos(p)));
		//planets.stream().forEach(p -> System.out.println(p));
	}

	/**
	 * Calculates net force experienced by a planet from all planets.
	 * @param p
	 * @param ps
	 * @return Net force for planet.
	 */
	public Vector newNetF(Planet p, ArrayList<Planet> ps) {
		ArrayList<Vector> fs = ps.stream()
						.filter(p2 -> !(p2.equals(p)))
						.map(p2 -> phys.nGravF(p, p2))
						.collect(Collectors.toCollection(ArrayList<Vector>::new));
		return phys.vectorSum(fs);
	}

	/**
	 * Calculates acceleration vector for planet
	 * @param p
	 * @return New acceleration for planet based on current net force
	 */
	public Vector newAcc(Planet p) {
		return phys.vectorScalarProduct(1.0 / p.getM(), p.getNetF());
	}
	
	/**
	 * Calculates new velocity vector for planet based on its mass and current net force.
	 * 
	 * @param p
	 * @return New velocity vector for planet
	 */
	public Vector newVel(Planet p) {
		return phys.vectorSum(p.getVel(), phys.vectorScalarProduct(tickTime / p.getM(), p.getNetF()));
	}

	/**
	 * Calculates new position vector for planet based on its current position, velocity and the ticktime of the simulator
	 * 
	 * @param p
	 * @return New position vector (after tickTime amount of time)
	 */
	public Vector newPos(Planet p) {
		double nX = p.getPos().getX() + p.getVel().getX() * tickTime;
		double nY = p.getPos().getY() + p.getVel().getY() * tickTime;
		double nZ = p.getPos().getZ() + p.getVel().getZ() * tickTime;
		return new Vector(nX, nY, nZ);
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	/**
	 *
	 * @param planets
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}

	/**
	 *
	 * @return
	 */
	public double getTickTime() {
		return tickTime;
	}

	/**
	 *
	 * @param tickTime
	 */
	public void setTickTime(double tickTime) {
		this.tickTime = tickTime;
	}
	
}