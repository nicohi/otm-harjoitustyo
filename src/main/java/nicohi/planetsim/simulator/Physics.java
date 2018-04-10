package nicohi.planetsim.simulator;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Physics {
	final double bigG = (6.67408 / 1000000) / 100000;

	public Vector nGravF(Planet p1, Planet p2) {
		double p1X = p1.getPos().getX();
		double p1Y = p1.getPos().getY();
		double p1Z = p1.getPos().getZ();
		double p2X = p2.getPos().getX();
		double p2Y = p2.getPos().getY();
		double p2Z = p2.getPos().getZ();

		double d = Math.pow(Math.pow((p1X - p2X), 2) + Math.pow((p1Y - p2Y), 2) + Math.pow((p1Z - p2Z), 2), 1/2);
		
		double fieldStr = -1 * (bigG * p1.getM() * p2.getM())/(d*d);
		
		return vectorScalarProduct(fieldStr, new Vector(p1X - p2X, p1Y - p2Y, p1Z - p2Z));
	}

	public Vector vectorScalarProduct(double s, Vector v) {
		return new Vector(s * v.getX(), s * v.getY(), s * v.getZ());
	}

	public Vector unitVector(Vector v1) {
		double p1X = v1.getX();
		double p1Y = v1.getY();
		double p1Z = v1.getZ();

		double mag = Math.pow(Math.pow(p1X, 2) + Math.pow(p1Y, 2) + Math.pow(p1Z, 2), 1/2);
		return new Vector(p1X/mag, p1Y/mag, p1Z/mag);
	}

	public Vector unitVectorNOT(Vector v1, Vector v2) {
		double p1X = v1.getX();
		double p1Y = v1.getY();
		double p1Z = v1.getZ();
		double p2X = v2.getX();
		double p2Y = v2.getY();
		double p2Z = v2.getZ();

		double mag = Math.pow(Math.pow((p1X - p2X), 2) + Math.pow((p1Y - p2Y), 2) + Math.pow((p1Z - p2Z), 2), 1/2);
		return new Vector((p1X + p2X)/mag, (p1Y + p2Y)/mag, (p1Z + p2Z)/mag);
	}

	public Vector vectorSum(Vector v1, Vector v2) {
		return new Vector(v1.getX() + v2.getX(), 
				v1.getY() + v2.getY(), 
				v1.getZ() + v2.getZ());
	}

	public Vector vectorSum(ArrayList<Vector> vs) {
		return vs.stream().reduce(new Vector(), new VectorSum());
	}

}

class VectorSum implements BinaryOperator {

		@Override
		public Vector apply(Object t, Object u) {
			if (t instanceof Vector && u instanceof Vector) {
				Vector one = (Vector) t;
				Vector two = (Vector) u;
				return new Vector(one.getX() + two.getX(), one.getY() + two.getY(), one.getZ() + one.getZ());
			} else { 
				return new Vector();
			} 
		}

}