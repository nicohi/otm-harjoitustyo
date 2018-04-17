package nicohi.planetsim.simulator;

public class Vector {
    double x;
    double y;
    double z;

    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

	@Override
	public String toString() {
		return "Vector{" + "x=" + x + ", y=" + y + '}';
	}


//	public double getMag() {
//		return Math.abs(Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), 1.0 / 2));
//	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
