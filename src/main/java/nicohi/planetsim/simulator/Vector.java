package nicohi.planetsim.simulator;

/**
 *
 * @author Nicolas Hiillos
 */
public class Vector {
    double x;
    double y;
    double z;

	/**
	 * Creates new vector (0,0,0)
	 */
	public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

	/**
	 * Creates new vector (x,y,0)
	 * @param x x
	 * @param y y
	 */
	public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

	/**
	 * Creates new vector (x,y,z)
	 * @param x x
	 * @param y y
	 * @param z z
	 */
	public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public String toString() {
		return "Vector{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}

	/**
	 *
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 *
	 * @param x x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 *
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 *
	 * @param y y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 *
	 * @return z
	 */
	public double getZ() {
		return z;
	}

	/**
	 *
	 * @param z z
	 */
	public void setZ(double z) {
		this.z = z;
	}

}
