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
	 * @param p1 planet 1. Force calculated from this planets perspective.
	 * @param p2 planet 2
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

		double d = distance(p1, p2);
		double fieldStr = (bigG * p1.getM() * p2.getM()) / (d * d);
		//double fieldStr = (bigG * p1.getM() * p2.getM())/(d*d);
		
		return vectorScalarProduct(fieldStr, unitVector(new Vector(p2X - p1X, p2Y - p1Y, p2Z - p1Z)));
	}

	/**
	 * Computes distance between 2 planets
	 * @param p1
	 * @param p2
	 * @return distance as a double
	 */
	public double distance(Planet p1, Planet p2) {
		//planet1 coords
		double p1X = p1.getPos().getX();
		double p1Y = p1.getPos().getY();
		double p1Z = p1.getPos().getZ();
		//planet2 coords
		double p2X = p2.getPos().getX();
		double p2Y = p2.getPos().getY();
		double p2Z = p2.getPos().getZ();

		//3D pythagoras 
		return Math.pow(Math.pow((p1X - p2X), 2) + Math.pow((p1Y - p2Y), 2) + Math.pow((p1Z - p2Z), 2), 1.0 / 2);
	}

	/**
	 * Multiplies all components of a vector by a double
	 * @param s scalar
	 * @param v vector
	 * @return A vector with (x,y,z) multiplied by s.
	 */
	public Vector vectorScalarProduct(double s, Vector v) {
		return new Vector(s * v.getX(),
				s * v.getY(),
				s * v.getZ());
	}

	public double magnitude(Vector v1) {
		double p1X = v1.getX();
		double p1Y = v1.getY();
		double p1Z = v1.getZ();
		return Math.abs(Math.pow(Math.pow(p1X, 2) + Math.pow(p1Y, 2) + Math.pow(p1Z, 2), 1.0 / 2));
	}

	/**
	 *
	 * @param v1 vector
	 * @return The unit vector of v1
	 */
	public Vector unitVector(Vector v1) {
		double p1X = v1.getX();
		double p1Y = v1.getY();
		double p1Z = v1.getZ();
		double mag = magnitude(v1);
		return new Vector(p1X / mag, p1Y / mag, p1Z / mag);
	}

	public Vector collisionVelocity(Planet p1, Planet p2) {
		//planet1 velocity comps
		double v1X = p1.getVel().getX();
		double v1Y = p1.getVel().getY();
		double v1Z = p1.getVel().getZ();
		//planet2 velocity comps
		double v2X = p2.getVel().getX();
		double v2Y = p2.getVel().getY();
		double v2Z = p2.getVel().getZ();

		//p = mv
		double p1X = p1.getM() * v1X + p2.getM() * v2X;
		double p1Y = p1.getM() * v1Y + p2.getM() * v2Y;
		double p1Z = p1.getM() * v1Z + p2.getM() * v2Z;

		double mTot = p1.getM() + p2.getM();

		return new Vector(p1X / mTot, p1Y / mTot, p1Z / mTot);
	}

	/**
	 *
	 * @param v1 vector 1
	 * @param v2 vector 2
	 * @return The sum of the two vectors
	 */
	public Vector vectorSum(Vector v1, Vector v2) {
		return new Vector(
				v1.getX() + v2.getX(), 
				v1.getY() + v2.getY(), 
				v1.getZ() + v2.getZ());
	}

	/**
	 *
	 * @param vs list of vectors
	 * @return The sum of the list of vectors.
	 */
	public Vector vectorSum(ArrayList<Vector> vs) {
		return vs.stream().reduce(new Vector(), (v1, v2) -> new Vector(
				v1.getX() + v2.getX(), 
				v1.getY() + v2.getY(),
				v1.getZ() + v2.getZ()));
	}

}