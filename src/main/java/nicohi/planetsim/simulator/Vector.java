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
	 * @param x
	 * @param y
	 */
	public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

	/**
	 * Creates new vector (x,y,z)
	 * @param x
	 * @param y
	 * @param z
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
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 *
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 *
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 *
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 *
	 * @return
	 */
	public double getZ() {
		return z;
	}

	/**
	 *
	 * @param z
	 */
	public void setZ(double z) {
		this.z = z;
	}

}
