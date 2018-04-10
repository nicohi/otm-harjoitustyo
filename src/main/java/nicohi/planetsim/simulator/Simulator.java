package nicohi.planetsim.simulator;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Simulator {
	ArrayList<Planet> planets;
	//seconds per tick
	double tickTime;
	Physics phys;

	public Simulator() {
		this.planets = new ArrayList<>();
		this.tickTime = 10;
		this.phys = new Physics();
	}

	public void tick() {
		planets.stream().forEach(p -> p.setVel(newVel(p, planets)));
		planets.stream().forEach(p -> p.setPos(newPos(p)));
		planets.stream().forEach(p -> System.out.println(p));
	}

	public Vector newVel(Planet p, ArrayList<Planet> ps) {
		ArrayList<Vector> fs = ps.stream()
				.filter(p2 -> !(p2.equals(p)))
				.map(p2 -> phys.nGravF(p, p2))
				.collect(Collectors.toCollection(ArrayList<Vector>::new));
		return phys.vectorSum(p.getVel(), phys.vectorScalarProduct(tickTime, phys.vectorScalarProduct(1/p.getM(), phys.vectorSum(fs))));
	}

	public Vector newPos(Planet p) {
		double nX = p.getPos().getX() + p.getVel().getX() * tickTime;
		double nY = p.getPos().getY() + p.getVel().getY() * tickTime;
		double nZ = p.getPos().getZ() + p.getVel().getZ() * tickTime;
		return new Vector(nX, nY, nZ);
	}

	public ArrayList<Planet> getPlanets() {
		return planets;
	}

	public void setPlanets(ArrayList<Planet> planets) {
		this.planets = planets;
	}

	public double getTickTime() {
		return tickTime;
	}

	public void setTickTime(double tickTime) {
		this.tickTime = tickTime;
	}

	
}