package nicohi.planetsim;

import nicohi.planetsim.simulator.Planet;
import nicohi.planetsim.simulator.Simulator;
import nicohi.planetsim.simulator.Vector;

public class Main {
	public static void main(String [] args)	{
		Simulator sim = new Simulator();
		sim.getPlanets().add(new Planet(100000));
		sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, 2), 10));
		for (int i=0; i<10; i++) sim.tick();
	}
}
