package nicohi.planetsim.simulator;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;

/**
 *
 * @author Nicolas Hiillos
 */
public class PlanetDB {
	Connection con = null;
	private HsqlProperties properties;
	private Server server;
	private boolean running = false;

	/**
	 * Constructor for planetdb
	 * @param path
	 */
	public PlanetDB(String path) {
		properties = new HsqlProperties();
		properties.setProperty("index", 0);
		properties.setProperty("id", 0);
		properties.setProperty("server.database.0", path + "planetdb");
		properties.setProperty("server.dbname.0", "planetdb");
		startServer();
		connect();
		initDB();
	}

	/**
	 * initializes database with table "Planet"
	 */
	public void initDB() {
		try {
			PreparedStatement stmt = con.prepareStatement("CREATE TABLE Planet (name varchar(20), mass double, posX double, posY double, posZ double, velX double, velY double, velZ double);");
			stmt.execute();
			System.out.println("creating planets");
		} catch (SQLException ex) {
			//System.out.println(ex);
		}
	}

	/**
	 * Starts HSQL server
	 */
	public void startServer() {
		if (server == null) {
			System.out.println("Starting HSQL server...");
			server = new Server();
			try {
				server.setProperties(properties);
				server.start();
				running = true;
				//System.out.println("start");	
			} catch (Exception e) {
				System.out.println("error: " + e);	
			}
			//} catch (AclFormatException afe) {
				//System.out.println("Error starting HSQL server." + afe);
			//} catch (IOException e) {
				//System.out.println("Error starting HSQL server." + e);
			//}
		}
	}

	/**
	 * Stops HSQL server
	 */
	public void stop() {
		server.shutdownCatalogs(1);
	}

	/**
	 * Creates connection con to HSQL server for PlanetDB
	 */
	public void connect() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/planetdb", "SA", "");
			if (con != null) {
				System.out.println("Connection created successfully");
			} else {
				System.out.println("Problem with creating connection");
			}
		}  catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Save planets to DB
	 * @param ps ArrayList of planets to be saved
	 */
	public void save(ArrayList<Planet> ps) {
		ps.stream().forEach(p -> {
			try {
				PreparedStatement stmt = con.prepareStatement("INSERT INTO Planet (name, mass, posX, posY, posZ, velX, velY, velZ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, p.getName());
				stmt.setDouble(2, p.getM());

				stmt.setDouble(3, p.getPos().getX());
				stmt.setDouble(4, p.getPos().getY());
				stmt.setDouble(5, p.getPos().getZ());

				stmt.setDouble(6, p.getVel().getX());
				stmt.setDouble(7, p.getVel().getY());
				stmt.setDouble(8, p.getVel().getZ());

				stmt.execute();
				stmt.close();
			} catch (SQLException ex) {
				System.out.println(ex);
			}
		});
	}

	/**
	 * Deletes everything in table Planet
	 */
	public void clear() {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM Planet WHERE true");
			stmt.execute();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Fetches planets from DB
	 * @return ArrayList of planets from DB
	 */
	public ArrayList<Planet> load() {
		ArrayList<Planet> ps = new ArrayList<>();
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM Planet");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String n = rs.getString("name");
				double m = rs.getDouble("mass");
				double pX = rs.getDouble("posX");
				double pY = rs.getDouble("posY");
				double pZ = rs.getDouble("posZ");
				double vX = rs.getDouble("velX");
				double vY = rs.getDouble("velY");
				double vZ = rs.getDouble("velZ");
				ps.add(new Planet(n, m, pX, pY, pZ, vX, vY, vZ));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return ps;
	}
}
