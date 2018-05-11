package nicohi.planetsim.simulator;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Nicolas Hiillos
 */
public class Planet {
	private String name;
    private double m;
    private Vector pos;
    private Vector vel;
	private Vector netF;
	private Vector acc;
	private double r;
	private boolean delete = false;
	private Planet collided;

	/**
	 * Sets velocity and position to parameterless vectors (0,0). Planet name is random
	 * @param m mass
	 */
	public Planet(double m) {
        this.pos = new Vector();
        this.vel = new Vector();
        this.netF = new Vector();
        this.acc = new Vector();
		this.m = m;
		this.r = Math.log10(m);
		this.name = new RandomStringGenerator().generateString();
    }

	public Planet(String n, double m, double pX, double pY, double pZ, double vX, double vY, double vZ) {
        this.pos = new Vector(pX, pY, pZ);
        this.vel = new Vector(vX, vY, vZ);
        this.netF = new Vector();
        this.acc = new Vector();
		this.m = m;
		this.r = Math.log10(m);
		this.name = n;
    }

	/**
	 * Planet name is random 
	 * @param pos position
	 * @param m mass
	 * @param vel velocity
	 */
	public Planet(Vector pos, Vector vel, double m) {
        this.pos = pos;
        this.vel = vel;
        this.netF = new Vector();
        this.acc = new Vector();
		this.m = m;
		this.r = Math.log10(m);
		this.name = new RandomStringGenerator().generateString();
    }

	/**
	 * Calculates a radius for the planet (double) based on mass
	 * @return radius
	 */
	public double radius() {
		return Math.log10(m) / 1.5;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getR() {
		return r;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 *
	 * @param acc acceleration
	 */
	public void setAcc(Vector acc) {
		this.acc = acc;
	}

	/**
	 *
	 * @return acceleration
	 */
	public Vector getAcc() {
		return acc;
	}

	/**
	 *
	 * @param netF net force
	 */
	public void setNetF(Vector netF) {
		this.netF = netF;
	}

	public Planet getCollided() {
		return collided;
	}

	public void setCollided(Planet collided) {
		this.collided = collided;
	}

	/**
	 *
	 * @return net force
	 */
	public Vector getNetF() {
		return netF;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + (int) (Double.doubleToLongBits(this.m) ^ (Double.doubleToLongBits(this.m) >>> 32));
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Planet other = (Planet) obj;
		if (Double.doubleToLongBits(this.m) != Double.doubleToLongBits(other.m)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Planet{" + "name=" + name + ", m=" + m + ", pos=" + pos + ", vel=" + vel + '}';
	}

	/**
	 *
	 * @return position
	 */
	public Vector getPos() {
		return pos;
	}

	/**
	 *
	 * @param pos position
	 */
	public void setPos(Vector pos) {
		this.pos = pos;
	}

	/**
	 *
	 * @return velocity
	 */
	public Vector getVel() {
		return vel;
	}

	/**
	 *
	 * @param vel velocity
	 */
	public void setVel(Vector vel) {
		this.vel = vel;
	}

	/**
	 *
	 * @return mass
	 */
	public double getM() {
		return m;
	}

	/**
	 *
	 * @param m mass
	 */
	public void setM(double m) {
		this.m = m;
	}

	/**
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Compares this planets mass to another planet
	 * @param p2 Planet 2
	 * @return 0 if masses are equal. 1 if this planet is heavier. -1 if Planet 2 is heavier
	 */
	public int heavier(Planet p2) {
		return Double.compare(this.m, p2.getM());
	}
}


class RandomStringGenerator {
	/**
	 * Random string using UUID
	 * @return random alphanumeric string of length 20
	 */
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
        return uuid.substring(20);
    }
}
