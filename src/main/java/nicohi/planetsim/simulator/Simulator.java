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
		this.tickTime = 10;
		this.phys = new Physics();
	}

	/**
	 * Progresses the simulation by the amount of time set as tickTime. Sets vectors of planets to new values.
	 */
	public void tick() {
		planets.stream().forEach(p -> collisions(p));
		planets = planets.stream().filter(p -> !p.isDelete()).collect(Collectors.toCollection(ArrayList<Planet>::new));
		planets.stream().forEach(p -> p.setNetF(newNetF(p, planets)));
		planets.stream().forEach(p -> p.setAcc(newAcc(p)));
		planets.stream().forEach(p -> p.setVel(newVel(p)));
		planets.stream().forEach(p -> p.setPos(newPos(p)));
		//planets.stream().forEach(p -> System.out.println(p));
	}

	/**
	 * Calculates net force experienced by a planet from all planets.
	 * @param p planet
	 * @param ps list of planets
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
	 * @param p planet
	 * @return New acceleration for planet based on current net force
	 */
	public Vector newAcc(Planet p) {
		return phys.vectorScalarProduct(1.0 / p.getM(), p.getNetF());
	}
	
	/**
	 * Calculates new velocity vector for planet based on its mass and current net force.
	 * 
	 * @param p planet
	 * @return New velocity vector for planet
	 */
	public Vector newVel(Planet p) {
		return phys.vectorSum(p.getVel(), phys.vectorScalarProduct(tickTime / p.getM(), p.getNetF()));
	}

	/**
	 * Calculates new position vector for planet based on its current position, velocity and the ticktime of the simulator
	 * 
	 * @param p planet
	 * @return New position vector (after tickTime amount of time)
	 */
	public Vector newPos(Planet p) {
		double nX = p.getPos().getX() + p.getVel().getX() * tickTime;
		double nY = p.getPos().getY() + p.getVel().getY() * tickTime;
		double nZ = p.getPos().getZ() + p.getVel().getZ() * tickTime;
		return new Vector(nX, nY, nZ);
	}
	
	/**
	 * Calculates collisions of a planet. Merges and deletes any planets that are touching p1.
	 * Should be done before calculating position.
	 * @param p1  planet
	 */
	public void collisions(Planet p1) {
		planets.stream().filter(p2 -> !(p2.equals(p1))).forEach(p2 -> {
			if (phys.distance(p1, p2) < p1.getR() + p2.getR() && !p1.isDelete() && !p2.isDelete()) {
				p1.setVel(phys.collisionVelocity(p1, p2));
				p1.setR(p1.radius());
				p1.setM(p1.getM() + p2.getM());
				p1.setNetF(phys.vectorSum(p1.getNetF(), phys.vectorSum(p1.getNetF(), phys.nGravF(p2, p1))));
				p2.setDelete(true);
			}
		});
	}

	/**
	 *
	 * @return planets
	 */
	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	/**
	 *
	 * @param planets list of planets
	 */
	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}

	/**
	 *
	 * @return ticktime
	 */
	public double getTickTime() {
		return tickTime;
	}

	/**
	 *
	 * @param tickTime time for of one simulation tick
	 */
	public void setTickTime(double tickTime) {
		this.tickTime = tickTime;
	}
	
}