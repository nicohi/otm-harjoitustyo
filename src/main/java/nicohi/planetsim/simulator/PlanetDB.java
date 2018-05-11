package nicohi.planetsim.simulator;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
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

	public PlanetDB (String path) {
		properties = new HsqlProperties();
		properties.setProperty("index", 0);
		properties.setProperty("id", 0);
		properties.setProperty("server.database.0", path + "planetbase");
		properties.setProperty("server.dbname.0", "planetbase");
		start();
	}

	public void start() {
		if (server == null) {
			System.out.println("Starting HSQL server...");
			server = new Server();
			try {
				server.setProperties(properties);
				server.start();
				running = true;
			} catch (AclFormatException afe) {
				System.out.println("Error starting HSQL server." + afe);
			} catch (IOException e) {
				System.out.println("Error starting HSQL server." + e);
			}
		}
	}

	public void stop() {
		server.shutdownCatalogs(1);
	}

	public void connect() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/planetdb", "SA", "");
			if (con!= null){
				System.out.println("Connection created successfully");
			} else {
				System.out.println("Problem with creating connection");
			}
		}  catch (Exception e) {
			e.printStackTrace(System.out);
		}
   }
}
