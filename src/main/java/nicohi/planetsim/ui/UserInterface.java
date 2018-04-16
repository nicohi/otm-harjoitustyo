package nicohi.planetsim.ui;

import com.sun.scenario.Settings;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import nicohi.planetsim.simulator.Planet;
import nicohi.planetsim.simulator.Simulator;
import nicohi.planetsim.simulator.Vector;

public class UserInterface extends Application {
	Simulator sim;
	AnimationTimer simLoop;
	long prev = 0;
	Scene scene;
	int width = 500;
	int height = 500;
	

	public static void main(String[] args) {
        launch(args);
    }

	private void startSim() {

        // start sim
        simLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
				if (now - prev >= 1000000000) {
					sim.tick();
				}	
				prev = now;
            }
        };

        simLoop.start();

    }

    @Override
    public void start(Stage primaryStage) {
		this.sim = new Simulator();
		sim.getPlanets().add(new Planet(100000));
		sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, 0.01), 10));
		for (int i=0; i<100; i++) sim.tick();

		// create containers
        BorderPane root = new BorderPane();

        // playfield for our Sprites

        // entire game as layers
        Pane layerPane = new Pane();

        //layerPane.getChildren().addAll(playfield);

        root.setCenter(layerPane);

        scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();

        // add content
        //prepareGame();

        // add mouse location listener
        //addListeners();

        // run animation loop
        startSim();
    }		
}

class UIPlanet {
	Planet p;
	Circle c;
	public UIPlanet(Planet p) {
		this.p = p;
		this.c = new Circle(p.getPos().getX(), p.getPos().getY(), radius());
	}
	
	public double radius() {
		return Math.log(p.getM());
	}
		
}
