package nicohi.planetsim.ui;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.scenario.Settings;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import nicohi.planetsim.simulator.Planet;
import nicohi.planetsim.simulator.Simulator;
import nicohi.planetsim.simulator.Vector;

/**
 *
 * @author Nicolas Hiillos
 */
public class UserInterface extends Application {
	Simulator sim;
	StatusTimer simLoop;
	long prev = 0;
	Scene scene;
	int width = 1000;
	int height = 900;
	Pane canvas;
	VBox rMenu;
	double scale = 0.4;
	double zDist = 300;

	/**
	 * main
	 * @param args arguments
	 */
	public static void main(String[] args) {
        launch(args);
    }

	/**
	 * A button which calls this.toggleTimer() to pause and unpause the simulation
	 * @return A Pause button
	 */
	public Button pauseBtn() {
		Button btn = new Button("pause/unpause");
		btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				toggleTimer();
            }
        });
		return btn;
	}
	
	/**
	 * A box for setting x and y
	 * @param t label
	 * @return A VBox with x and y fields and labels
	 */
	public VBox vectorSetBox(String t) {
		VBox b = new VBox();
		b.getChildren().add(new Label(t));
		b.getChildren().add(numericFieldAndLabel("x: "));
		b.getChildren().add(numericFieldAndLabel("y: "));
		return b;
	}

	/**
	 * A field with a label to the left of it
	 * @param l label
	 * @return A HBox with a label and field
	 */
	public HBox numericFieldAndLabel(String l) {
		TextField txt = new TextField();
		// force the field to be numeric only
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				//if (!newValue.matches("\\d*") && !newValue.matches("\\[-]d*")) {
					//txt.setText(newValue.replaceAll("[^\\d]", ""));
				//}
			}
		});
		return new HBox(new Label(l), txt);
	}

	private void startSim() {

        // start sim
        simLoop = new StatusTimer() {

            @Override
            public void handle(long now) {
				if (now - prev >= 10000000) {
					sim.tick();

					//remove nonexisting planets (resulting from collisions)
					updateWithSim();
					sortBasedOnZ();

					UIPlanet mMax = canvas.getChildren().stream()
							.filter(c -> c instanceof UIPlanet)
							.map(p -> (UIPlanet) p)
							.max((p1, p2) -> p1.getP().heavier(p2.getP()))
							.orElse(new UIPlanet(new Planet(1)));
					//System.out.println(mMax.p.getM());

					//set colour of central planet
					mMax.setFill(Paint.valueOf(Color.DARKORANGE.toString()));
					//System.out.println(mMax.p);

					double xCenter = mMax.getP().getPos().getX() * scale + (width / 2);
					double yCenter = mMax.getP().getPos().getY() * scale + (height / 2);
					double zCenter = zDist + mMax.getP().getPos().getZ();

					//System.out.println("x: " + xCenter + " y: " + yCenter);
					canvas.getChildren().stream().filter(c -> c instanceof UIPlanet)
							.map(p -> (UIPlanet) p)
							.forEach(p -> {
								p.resetPos(xCenter, yCenter, zCenter, scale);
							});

					prev = now;
				}	
            }
        };

        simLoop.start();

    }

	private void updateWithSim() {
		//ArrayList<UIPlanet> delete = canvas.getChildren().stream().filter(c -> c instanceof UIPlanet)
				//.map(p -> (UIPlanet) p)
				//.filter(p -> !sim.getPlanets().contains(p.p))
				//.collect(Collectors.toCollection(ArrayList<UIPlanet>::new));
		//delete.stream().forEach(p -> canvas.getChildren().remove(p));
		canvas.getChildren().clear();
		sim.getPlanets().stream().forEach(p -> addPlanetToUI(p));
	}

	private void stopTimer() {
		simLoop.stop();
	}

	private void toggleTimer() {
		if(simLoop.isRunning()) simLoop.stop();
		else simLoop.start();
	}
	
	/**
	 * A button to add a planet
	 * @return A button
	 */
	public Button addPlanetButton() {
		Button b = new Button("add planet");
		return b;
	}
	
	public void sortBasedOnZ() {
		Comparator<Node> comparator = (p1, p2) -> {
			if (p1 instanceof UIPlanet && p2 instanceof UIPlanet) {
				System.out.println("sus");
				UIPlanet up1 = (UIPlanet) p1;
				UIPlanet up2 = (UIPlanet) p2;
				return Double.compare(up1.p.getPos().getZ(), up2.p.getPos().getZ());
			} else {
				return -1;
			}
		};
		FXCollections.sort(canvas.getChildren(), comparator);
	}
	
	public void addPlanetToUI(Planet p) {
		UIPlanet pN = new UIPlanet(p);
		canvas.getChildren().add(pN);
		//canvas.getChildren().addAll(pN.getA(), pN.getF(), pN.getV());
		canvas.getChildren().addAll(pN.getV());
		//canvas.getChildren().addAll(pN.getA());
	}
	/**
	 * Adds a planet to the UI and sim
	 * @param p planet
	 */
	public void addPlanet(Planet p) {
		sim.getPlanets().add(p);
		addPlanetToUI(p);
	}

    @Override
    public void start(Stage primaryStage) {
        // layers
        canvas = new Pane();

		this.sim = new Simulator();
		addPlanet(new Planet(100000000000.0));
		//addPlanet(new Planet(new Vector(100, 0), new Vector(0, 0.3, 0), 10000000000.0));
		addPlanet(new Planet(new Vector(-300,100), new Vector(0, 0, 0.154), 1000000000.0));
		addPlanet(new Planet(new Vector(400,0), new Vector(0, 0.134), 1000000000.0));
		addPlanet(new Planet(new Vector(-400,0), new Vector(0, -0.134), 10000000000.0));
		//addPlanet(new Planet(new Vector(400,40), new Vector(0.033, 0.134), 1000.0));
		//sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, -0.5), 1000000));
		//addPlanet(new Planet(new Vector(100, 0), new Vector(0, -0.5), 100000));
		//sim.getPlanets().add(new Planet(new Vector(150, 30), new Vector(0, -0.8), 1000000000));
		//addPlanet(new Planet(new Vector(150, 30), new Vector(0, -0.8), 1000000));
		//sim.getPlanets().add(new Planet(new Vector(55, 0), new Vector(0.2, 1.6), 100000000));

		// create containers
        BorderPane root = new BorderPane();

		//right menu
		rMenu = new PlanetAddMenu(this);

		//test circle
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(500, 500);
		//canvas.getChildren().add(circle);

		//this.sim.getPlanets().forEach(p -> {
			//UIPlanet pN = new UIPlanet(p);
			//canvas.getChildren().add(pN);
			//canvas.getChildren().addAll(pN.getA(), pN.getF(), pN.getV());
			//canvas.getChildren().addAll(pN.getV());
			//canvas.getChildren().addAll(pN.getA());
			//canvas.getChildren().addAll(pN.getF());
		//});

        //layerPane.getChildren().addAll(playfield);
		//canvas.getChildren().addAll(circle);
        root.setLeft(canvas);
		root.setBottom(pauseBtn());

		root.setRight(rMenu);
		
        scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();

        startSim();
    }		
}

class PlanetAddMenu extends VBox {
	UserInterface ui;

	public PlanetAddMenu(UserInterface ui) {
		super();
		this.ui = ui;
		this.getChildren().add(ui.vectorSetBox("position"));
		this.getChildren().add(ui.vectorSetBox("velocity"));
		this.getChildren().add(ui.numericFieldAndLabel("mass: "));
		this.getChildren().add(ui.addPlanetButton());
	}
		
}

abstract class StatusTimer extends AnimationTimer {

    private volatile boolean running;

    @Override
    public void start() {
         super.start();
         running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
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
		return v.toString();
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
	double scale;

	public UIPlanet(Planet p) {
		super(p.radius(), Color.CRIMSON);
		this.setCenterX(p.getPos().getX());
		this.setCenterY(p.getPos().getY());
		this.p = p;
		this.v = new UIVector(p.getVel());
		this.a = new UIVector(p.getAcc());
		this.f = new UIVector(p.getNetF());
		this.scale = 1;
	}

	public UIPlanet(Planet p, double radius) {
		super(radius, Color.AQUAMARINE);
		this.setCenterX(p.getPos().getX());
		this.setCenterY(p.getPos().getY());
		this.p = p;
	}

	public void resetPos(double centerX, double centerY, double centerZ, double scale) {
		this.scale = scale;
		this.radiusProperty().set(p.radius());
		double x = -1 * (p.getPos().getX() * scale) + centerX;
		double y = -1 * (p.getPos().getY() * scale) + centerY;

		double zScale = ((p.getPos().getZ() + centerZ) / centerZ);
		//if too close
		if (zScale * p.getPos().getZ() > centerZ) this.opacityProperty().set(0.5);

		this.setCenterX(x);
		this.setCenterY(y);

		//scale based on z
		this.radiusProperty().set(this.radiusProperty().multiply(zScale).doubleValue());

		this.v.setV(this.p.getVel());
		this.v.resetPos(x, y, 100);

		this.a.setV(this.p.getAcc());
		this.a.resetPos(x, y, 10000);

		this.f.setV(this.p.getNetF());
		this.f.resetPos(x, y, 10000.0);
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
