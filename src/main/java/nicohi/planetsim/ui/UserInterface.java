package nicohi.planetsim.ui;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.scenario.Settings;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import nicohi.planetsim.simulator.Planet;
import nicohi.planetsim.simulator.Simulator;
import nicohi.planetsim.simulator.Vector;

public class UserInterface extends Application {
	Simulator sim;
	StatusTimer simLoop;
	long prev = 0;
	Scene scene;
	int width = 900;
	int height = 900;
	Pane canvas;
	VBox rMenu;
	double scale = 0.5;

	public static void main(String[] args) {
        launch(args);
    }

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
	
	public VBox vectorSetBox(String t) {
		VBox b = new VBox();
		b.getChildren().add(new Label(t));
		b.getChildren().add(numericFieldAndLabel("x: "));
		b.getChildren().add(numericFieldAndLabel("y: "));
		return b;
	}
	
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
								p.resetPos(xCenter, yCenter, scale);
							});
					prev = now;
				}	
            }
        };

        simLoop.start();

    }

	private void stopTimer() {
		simLoop.stop();
	}

	private void toggleTimer() {
		if(simLoop.isRunning()) simLoop.stop();
		else simLoop.start();
	}
	
	public Button addPlanetButton() {
		Button b = new Button("add planet");
		return b;
	}
	
	public void addPlanet(Planet p) {
		sim.getPlanets().add(p);
		UIPlanet pN = new UIPlanet(p);
		canvas.getChildren().add(pN);
		//canvas.getChildren().addAll(pN.getA(), pN.getF(), pN.getV());
		canvas.getChildren().addAll(pN.getV());
		canvas.getChildren().addAll(pN.getA());
	}

    @Override
    public void start(Stage primaryStage) {
        // layers
        canvas = new Pane();

		this.sim = new Simulator();
		addPlanet(new Planet(100000000000.0));
		addPlanet(new Planet(new Vector(400,0), new Vector(0, 0.134), 1000000000.0));
		addPlanet(new Planet(new Vector(400,40), new Vector(0.033, 0.134), 1000.0));
		//sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, -0.5), 1000000));
		//addPlanet(new Planet(new Vector(100, 0), new Vector(0, -0.5), 100000));
		//sim.getPlanets().add(new Planet(new Vector(150, 30), new Vector(0, -0.8), 1000000000));
		//addPlanet(new Planet(new Vector(150, 30), new Vector(0, -0.8), 1000000));
		//sim.getPlanets().add(new Planet(new Vector(55, 0), new Vector(0.2, 1.6), 100000000));

		// create containers
        BorderPane root = new BorderPane();

		//right menu
		rMenu = new VBox();
		rMenu.getChildren().add(vectorSetBox("position"));
		rMenu.getChildren().add(vectorSetBox("velocity"));
		rMenu.getChildren().add(numericFieldAndLabel("mass: "));
		rMenu.getChildren().add(addPlanetButton());

		//test circle
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(200, 200);

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

	public void resetPos(double centerX, double centerY, double scale) {
		this.scale = scale;
		double x = p.getPos().getX() * scale + centerX;
		double y = p.getPos().getY() * scale + centerY;
		this.setCenterX(x);
		this.setCenterY(y);

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
