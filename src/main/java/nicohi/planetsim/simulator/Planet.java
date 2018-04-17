package nicohi.planetsim.simulator;

import java.util.UUID;

public class Planet {
	String name;
    double m;
    Vector pos;
    Vector vel;

    public Planet(double m) {
        this.pos = new Vector();
        this.vel = new Vector();
		this.m = m;
		this.name = new RandomStringGenerator().generateString();
    }

    public Planet(Vector pos, Vector vel, double m) {
        this.pos = pos;
        this.vel = vel;
		this.m = m;
		this.name = new RandomStringGenerator().generateString();
    }

	public double radius() {
		return Math.log(m);
	}

	@Override
	public String toString() {
		return "Planet{" + "name=" + name + ", m=" + m + ", pos=" + pos + ", vel=" + vel + '}';
	}

	public Vector getPos() {
		return pos;
	}

	public void setPos(Vector pos) {
		this.pos = pos;
	}

	public Vector getVel() {
		return vel;
	}

	public void setVel(Vector vel) {
		this.vel = vel;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


class RandomStringGenerator {

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
        return uuid.substring(20);
    }
}
