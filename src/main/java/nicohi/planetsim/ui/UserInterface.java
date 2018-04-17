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
import javafx.scene.paint.Color;
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
	Pane canvas;
	

	public static void main(String[] args) {
        launch(args);
    }

	private void startSim() {

        // start sim
        simLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
				if (now - prev >= 10000000) {
					sim.tick();
					canvas.getChildren().stream().filter(c -> c instanceof UIPlanet)
							.map(p -> (UIPlanet) p)
							.forEach(p -> p.resetPos());
				}	
				prev = now;
            }
        };

        simLoop.start();

    }

    @Override
    public void start(Stage primaryStage) {
		this.sim = new Simulator();
		sim.getPlanets().add(new Planet(1000000));
		sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, 0.1), 10));
		sim.getPlanets().add(new Planet(new Vector(10, 0), new Vector(0.1, 0.6), 10));
		//for (int i=0; i<100; i++) sim.tick();

		// create containers
        BorderPane root = new BorderPane();

        // playfield for our Sprites

        // entire game as layers
        canvas = new Pane();

		//test circle
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(200, 200);

		this.sim.getPlanets().forEach(p -> {
			canvas.getChildren().add(new UIPlanet(p));
		});

        //layerPane.getChildren().addAll(playfield);
		//canvas.getChildren().addAll(circle);
        root.setCenter(canvas);

        scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();

        // run animation loop
        startSim();
    }		
}

class UIPlanet extends Circle {
	Planet p;
	int center = 250;
	//Circle c;

	public UIPlanet(Planet p) {
		super(p.radius());
		this.setCenterX(p.getPos().getX() + center);
		this.setCenterY(p.getPos().getY() + center);
		this.p = p;
	}

	public UIPlanet(Planet p, double radius) {
		super(radius);
		this.setCenterX(p.getPos().getX() + center);
		this.setCenterY(p.getPos().getY() + center);
		this.p = p;
	}

	public void resetPos() {
		this.setCenterX(p.getPos().getX() + center);
		this.setCenterY(p.getPos().getY() + center);
	}
//	public UIPlanet(Planet p) {
//		this.p = p;
//		this.c = new Circle(p.getPos().getX(), p.getPos().getY(), radius());
//	}
	
//	public UIPlanet(Planet p, Circle c) {
//		this.p = p;
//		this.c = new Circle(p.getPos().getX(), p.getPos().getY(), radius());
//	}

		
}
