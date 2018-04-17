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
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import nicohi.planetsim.simulator.Planet;
import nicohi.planetsim.simulator.Simulator;
import nicohi.planetsim.simulator.Vector;

public class UserInterface extends Application {
	Simulator sim;
	AnimationTimer simLoop;
	double prev = 0;
	Scene scene;
	int width = 900;
	int height = 900;
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
					UIPlanet mMax = canvas.getChildren().stream()
							.filter(c -> c instanceof UIPlanet)
							.map(p -> (UIPlanet) p)
							.max((p1, p2) -> p1.getP().heavier(p2.getP()))
							.orElse(new UIPlanet(new Planet(1)));

					double xCenter = (width / 2) - mMax.getP().getPos().getX();
					double yCenter = (height / 2) - mMax.getP().getPos().getY();

					//test
					//double xCenter = 250;
					//double yCenter = 250;
					//System.out.println(mMax.getP().getM());
					canvas.getChildren().stream().filter(c -> c instanceof UIPlanet)
							.map(p -> (UIPlanet) p)
							.forEach(p -> {
								p.resetPos(xCenter, yCenter, 10000);
							});
				}	
				prev = now;
            }
        };

        simLoop.start();

    }

	private void stopSim() {
		simLoop.stop();
	}

    @Override
    public void start(Stage primaryStage) {
		this.sim = new Simulator();
		sim.getPlanets().add(new Planet(10000000000.0));
		sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, -0.5), 100));
		sim.getPlanets().add(new Planet(new Vector(50, 0), new Vector(0.2, 1.6), 100));

		// create containers
        BorderPane root = new BorderPane();

        // playfield for our Sprites

        // entire game as layers
        canvas = new Pane();

		//test circle
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(200, 200);

		this.sim.getPlanets().forEach(p -> {
			UIPlanet pN = new UIPlanet(p);
			canvas.getChildren().add(pN);
			//canvas.getChildren().addAll(pN.getA(), pN.getF(), pN.getV());
			//canvas.getChildren().addAll(pN.getV());
			//canvas.getChildren().addAll(pN.getA());
			canvas.getChildren().addAll(pN.getF());
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

class UIVector extends Line {
	Vector v;

	public UIVector(Vector v) {
		super();
		this.v = v;
	}

	public UIVector(Vector v, double startX, double startY) {
		super(startX, startY, startX + v.getX(), startY + v.getY());
		this.v = v;
	}

	public void resetPos(double x, double y, double scale) {
		this.setStartX(x);
		this.setStartY(y);
		this.setEndX(x + v.getX() * scale);
		this.setEndY(y + v.getY() * scale);
	}

	@Override
	public String toString() {
		return v.toString(); //To change body of generated methods, choose Tools | Templates.
	}

	public void setV(Vector v) {
		this.v = v;
	}
	
}

class UIPlanet extends Circle {
	Planet p;
	UIVector v;
	UIVector a;
	UIVector f;
	//int center = 250;
	//Circle c;

	public UIPlanet(Planet p) {
		super(p.radius(), Color.CRIMSON);
		this.setCenterX(p.getPos().getX());
		this.setCenterY(p.getPos().getY());
		this.p = p;
		this.v = new UIVector(p.getVel());
		this.a = new UIVector(p.getAcc());
		this.f = new UIVector(p.getNetF());
	}

	public UIPlanet(Planet p, double radius) {
		super(radius, Color.AQUAMARINE);
		this.setCenterX(p.getPos().getX());
		this.setCenterY(p.getPos().getY());
		this.p = p;
	}

	public void resetPos(double centerX, double centerY, double scale) {
		double x = p.getPos().getX() + centerX;
		double y = p.getPos().getY() + centerY;
		this.setCenterX(x);
		this.setCenterY(y);

		this.v.setV(this.p.getVel());
		this.v.resetPos(x, y, 10);

		this.a.setV(this.p.getAcc());
		this.a.resetPos(x, y, 100);

		this.f.setV(this.p.getNetF());
		this.f.resetPos(x, y, 0.001);
	}

	public UIVector getA() {
		return a;
	}

	public UIVector getF() {
		return f;
	}

	public UIVector getV() {
		return v;
	}
	
//	public UIPlanet(Planet p) {
//		this.p = p;
//		this.c = new Circle(p.getPos().getX(), p.getPos().getY(), radius());
//	}
	
//	public UIPlanet(Planet p, Circle c) {
//		this.p = p;
//		this.c = new Circle(p.getPos().getX(), p.getPos().getY(), radius());
//	}

	public Planet getP() {
		return p;
	}

		
}
