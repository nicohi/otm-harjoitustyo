package nicohi.planetsim.simulator;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

/**
 *
 * @author Nicolas Hiillos
 */
public class Physics {
	//final double bigG = (6.67408 / Math.pow(10, 11));
	final double bigG = (6.67408 / Math.pow(10, 11));

	/**
	 * Calculates the gravitational force between 2 planets as a vector.
	 * 
	 * @param Planet 1
	 * @param Planet 2
	 * @return Vector of gravitational force experienced by p1
	 */
	public Vector nGravF(Planet p1, Planet p2) {
		//planet1 coords
		double p1X = p1.getPos().getX();
		double p1Y = p1.getPos().getY();
		double p1Z = p1.getPos().getZ();
		//planet2 coords
		double p2X = p2.getPos().getX();
		double p2Y = p2.getPos().getY();
		double p2Z = p2.getPos().getZ();

		//3D pythagoras 
		double d = Math.pow(Math.pow((p1X - p2X), 2) + Math.pow((p1Y - p2Y), 2) + Math.pow((p1Z - p2Z), 2), 1.0 / 2);
		//-1 for 
		double fieldStr = (bigG * p1.getM() * p2.getM()) / (d * d);
		//double fieldStr = (bigG * p1.getM() * p2.getM())/(d*d);
		
		return vectorScalarProduct(fieldStr, unitVector(new Vector(p2X - p1X, p2Y - p1Y, p2Z - p1Z)));
	}

	/**
	 * Multiplies all components of a vector by a double
	 * @param s
	 * @param v
	 * @return
	 */
	public Vector vectorScalarProduct(double s, Vector v) {
		return new Vector(s * v.getX(),
				s * v.getY(),
				s * v.getZ());
	}

	/**
	 *
	 * @param v1
	 * @return
	 */
	public Vector unitVector(Vector v1) {
		double p1X = v1.getX();
		double p1Y = v1.getY();
		double p1Z = v1.getZ();

		double mag = Math.abs(Math.pow(Math.pow(p1X, 2) + Math.pow(p1Y, 2) + Math.pow(p1Z, 2), 1.0 / 2));
		return new Vector(p1X / mag, p1Y / mag, p1Z / mag);
	}

	/**
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Vector vectorSum(Vector v1, Vector v2) {
		return new Vector(
				v1.getX() + v2.getX(), 
				v1.getY() + v2.getY(), 
				v1.getZ() + v2.getZ());
	}

	/**
	 *
	 * @param vs
	 * @return
	 */
	public Vector vectorSum(ArrayList<Vector> vs) {
		return vs.stream().reduce(new Vector(), (v1, v2) -> new Vector(
				v1.getX() + v2.getX(), 
				v1.getY() + v2.getY(),
				v1.getZ() + v2.getZ()));
	}

}