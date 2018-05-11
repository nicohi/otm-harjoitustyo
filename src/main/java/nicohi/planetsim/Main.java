package nicohi.planetsim;

import nicohi.planetsim.simulator.PlanetDB;
import nicohi.planetsim.ui.UserInterface;


public class Main {
	public static void main(String [] args)	{
		//PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/db/");
		UserInterface.main(args);
	}
}
