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
import javafx.scene.control.Slider;
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
import nicohi.planetsim.simulator.PlanetDB;
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
	int width = 1200;
	int height = 1000;
	Pane canvasP;
	Pane canvasV;
	PlanetAddMenu rMenu;
	double scale = 0.4;
	double zDist = 300;
	double tickTime = 20;
	UIPlanet mMax;
	boolean showVectors = true;

	/**
	 * main
	 * @param args arguments
	 */
	public static void main(String[] args) {
        launch(args);
    }
	
	public void saveToDB() {
		PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/planetdb/");
		db.clear();
		db.save(sim.getPlanets());
		db.stop();
	}

	public void loadFromDB() {
		PlanetDB db = new PlanetDB(System.getProperty("user.dir") + "/planetdb/");
		sim.setPlanets(db.load());
		db.stop();
	}

	public HBox dbButtons() {
		Button save = new Button("save");
		Button load = new Button("load");

		save.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				saveToDB();
            }
        });

		load.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				loadFromDB();
            }
        });

		return new HBox(save, load);
	}

	public Button toggleShowVectors() {
		Button btn = new Button("toggle vectors");
		btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				if (canvasV.isVisible()) {
					canvasV.setVisible(false);
				} else {
					canvasV.setVisible(true);
				}
            }
        });
		return btn;
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
	public VBox vectorSetBox(String t, String defX, String defY, String defZ) {
		VBox b = new VBox();
		b.getChildren().add(new Label(t));
		b.getChildren().add(numericFieldAndLabel("x: ", defX));
		b.getChildren().add(numericFieldAndLabel("y: ", defY));
		b.getChildren().add(numericFieldAndLabel("z: ", defZ));
		return b;
	}

	/**
	 * A field with a label to the left of it
	 * @param l label
	 * @return A HBox with a label and field
	 */
	public HBox numericFieldAndLabel(String l, String d) {
		TextField txt = new TextField();
		txt.setText(d);
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
				if (now - prev >= 8000000) {
					sim.setTickTime(tickTime);
					sim.tick();

					//remove nonexisting planets (resulting from collisions)
					updateWithSim();

					mMax = canvasP.getChildren().stream()
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
					double zCenter = zDist + mMax.getP().getPos().getZ() * scale;

					//System.out.println("x: " + xCenter + " y: " + yCenter);
					canvasP.getChildren().stream().filter(c -> c instanceof UIPlanet)
							.map(p -> (UIPlanet) p)
							.forEach(p -> {
								p.resetPos(xCenter, yCenter, zCenter, scale);
							});

					sortBasedOnZ();

					prev = now;
				}	
            }
        };

        simLoop.start();

    }

	private void updateWithSim() {
		//ArrayList<UIPlanet> delete = canvasP.getChildren().stream().filter(c -> c instanceof UIPlanet)
				//.map(p -> (UIPlanet) p)
				//.filter(p -> !sim.getPlanets().contains(p.p))
				//.collect(Collectors.toCollection(ArrayList<UIPlanet>::new));
		//delete.stream().forEach(p -> canvasP.getChildren().remove(p));
		//load defaults
		canvasP.getChildren().clear();
		canvasV.getChildren().clear();
		sim.getPlanets().stream().forEach(p -> addPlanetToUI(p));
	}

	private void stopTimer() {
		simLoop.stop();
	}

	private void toggleTimer() {
		if(simLoop.isRunning()) simLoop.stop();
		else simLoop.start();
	}
	
	public HBox bottomBar() {
		Slider time = new Slider();
		time.setMin(0.1);
		time.setMax(60);
		time.setValue(10);
		time.setShowTickLabels(true);
		time.setShowTickMarks(true);
		time.setMajorTickUnit(10);
		time.setMinorTickCount(1);
		time.setBlockIncrement(10);
		time.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
					tickTime = new_val.doubleValue();
            }
        });

		Slider zoom = new Slider();
		zoom.setMin(0.01);
		zoom.setMax(1.5);
		zoom.setValue(0.5);
		zoom.setShowTickLabels(true);
		zoom.setShowTickMarks(true);
		zoom.setMajorTickUnit(0.5);
		zoom.setMinorTickCount(10);
		zoom.setBlockIncrement(10);
		zoom.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
					scale = new_val.doubleValue();
            }
        });

		return new HBox(pauseBtn(), new Label(" time: "), time, new Label(" scale: "), zoom);
	}
	
	/**
	 * A button to add a planet
	 * @return A button
	 */
	public Button addPlanetButton() {
		Button b = new Button("add planet");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				//System.out.println(rMenu.getPos());
				//System.out.println(rMenu.getVel());
				//System.out.println(rMenu.getM());
				Vector pos = rMenu.getPos();
				Vector relPos = new Vector(-1 * pos.getX() + mMax.p.getPos().getX(),
						-1 * pos.getY() + mMax.p.getPos().getY(),
						pos.getZ() + mMax.p.getPos().getZ());
				double m = rMenu.getM();
				if (m == 0.0) m = 100;
				addPlanet(new Planet(relPos, rMenu.getVel(), m));
			}
		});
		return b;
	}
	
	public void sortBasedOnZ() {
		Comparator<Node> comparator = (p1, p2) -> {
			try {
				UIPlanet up1 = (UIPlanet) p1;
				UIPlanet up2 = (UIPlanet) p2;
				return Double.compare(up1.getScaleX(), up2.getScaleX());
			} catch (Exception e) {
				return Double.compare(p1.getScaleX(), p2.getScaleX());
			}
		};
		FXCollections.sort(canvasP.getChildren(), comparator);
	}
	
	public void addPlanetToUI(Planet p) {
		UIPlanet pN = new UIPlanet(p);
		canvasP.getChildren().add(pN);
		//canvas.getChildren().addAll(pN.getA(), pN.getF(), pN.getV());
		if (showVectors) {
			canvasV.getChildren().addAll(pN.getV());
			canvasV.getChildren().addAll(pN.getA());
		}
	}
	/**
	 * Adds a planet to the UI and sim
	 * @param p planet
	 */
	public void addPlanet(Planet p) {
		sim.getPlanets().add(p);
		addPlanetToUI(p);
	}

	public Button clearPlanets() {
		Button b =  new Button("clear");
		b.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				sim.getPlanets().clear();
            }
        });
		return b;

	}

    @Override
    public void start(Stage primaryStage) {
		this.sim = new Simulator();

        // layers
        canvasP = new Pane();
        canvasV = new Pane();
		Pane layers = new Pane(canvasP, canvasV);

		//load default
		if (sim.getPlanets().isEmpty()) {
			addPlanet(new Planet(100000000000.0));
			addPlanet(new Planet(new Vector(100, 0), new Vector(0, 0.3, 0), 1000000000.0));
			addPlanet(new Planet(new Vector(-300,0), new Vector(0, 0, 0.154), 1000000000.0));
			addPlanet(new Planet(new Vector(400,0), new Vector(0, 0.134), 1000000000.0));
			addPlanet(new Planet(new Vector(400,40), new Vector(0.033, 0.134), 1000.0));
			addPlanet(new Planet(new Vector(-400,0), new Vector(0, -0.134), 1000000000.0));
			//sim.getPlanets().add(new Planet(new Vector(100, 0), new Vector(0, -0.5), 1000000));
			//addPlanet(new Planet(new Vector(100, 0), new Vector(0, -0.5), 100000));
			//sim.getPlanets().add(new Planet(new Vector(150, 30), new Vector(0, -0.8), 1000000000));
			//addPlanet(new Planet(new Vector(150, 30), new Vector(0, -0.8), 1000000));
			//sim.getPlanets().add(new Planet(new Vector(55, 0), new Vector(0.2, 1.6), 100000000));
		}

		// create container
        BorderPane root = new BorderPane();

		//right menu
		rMenu = new PlanetAddMenu(this);
		rMenu.getChildren().add(new Label(""));
		rMenu.getChildren().add(new Label("general: "));
		rMenu.getChildren().add(clearPlanets());
		rMenu.getChildren().add(toggleShowVectors());
		rMenu.getChildren().add(new Label("database: "));
		rMenu.getChildren().add(dbButtons());

		//test circle
		Circle circle = new Circle(50, Color.BLUE);
		circle.relocate(500, 500);
		//canvas.getChildren().add(circle);

        root.setCenter(layers);
		root.setBottom(bottomBar());

		root.setRight(rMenu);
		
		root.setStyle("-fx-background-color: #A0A0A0");
        scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.show();
		
		//saveToDB();
		//loadFromDB();
        startSim();
    }		
}

class PlanetAddMenu extends VBox {
	UserInterface ui;
	VBox pos;
	VBox vel;
	HBox m;

	public PlanetAddMenu(UserInterface ui) {
		super();
		this.ui = ui;
		this.pos = ui.vectorSetBox("position", "300.0", "0.0", "0.0");
		this.vel = ui.vectorSetBox("velocity", "0.0", "0.15", "0.05");
		this.m = ui.numericFieldAndLabel("mass: ", "1000.0");
		this.getChildren().add(this.pos);
		this.getChildren().add(this.vel);
		this.getChildren().add(this.m);
		this.getChildren().add(ui.addPlanetButton());
	}

	public double getM() {
		try {
			TextField mT = (TextField) m.getChildren().get(1);
			return Double.valueOf(mT.getText());
		} catch (Exception e) {
			System.out.println(e);
			return 1;
		}
	}

	public Vector getPos() {
		try {
			HBox xH = (HBox) pos.getChildren().get(1);
			TextField xT = (TextField) xH.getChildren().get(1);
			double x = Double.valueOf(xT.getText());

			HBox yH = (HBox) pos.getChildren().get(2);
			TextField yT = (TextField) yH.getChildren().get(1);
			double y = Double.valueOf(yT.getText());

			HBox zH = (HBox) pos.getChildren().get(3);
			TextField zT = (TextField) zH.getChildren().get(1);
			double z = Double.valueOf(zT.getText());

			return new Vector(x, y, z);
		} catch (Exception e) {
			System.out.println(e);
			return new Vector();
		}
	}

	public Vector getVel() {
		try {
			HBox xH = (HBox) vel.getChildren().get(1);
			TextField xT = (TextField) xH.getChildren().get(1);
			double x = Double.valueOf(xT.getText());

			HBox yH = (HBox) vel.getChildren().get(2);
			TextField yT = (TextField) yH.getChildren().get(1);
			double y = Double.valueOf(yT.getText());

			HBox zH = (HBox) vel.getChildren().get(3);
			TextField zT = (TextField) zH.getChildren().get(1);
			double z = Double.valueOf(zT.getText());

			return new Vector(x, y, z);
		} catch (Exception e) {
			System.out.println(e);
			return new Vector();
		}
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
		this.setEndX(x - v.getX() * scale);
		this.setEndY(y - v.getY() * scale);
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
		v.setStroke(Color.FORESTGREEN);
		this.a = new UIVector(p.getAcc());
		a.setStroke(Color.MAROON);
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
		//if (zScale * p.getPos().getZ() > centerZ) this.opacityProperty().set(0.5);
		//System.out.println(zScale);
		this.setFill(Color.hsb(zScale / 2.0 * (this.p.getPos().getZ() + 270 - centerZ), 1.0, 0.8));

		this.setCenterX(x);
		this.setCenterY(y);

		//scale based on z
		//this.radiusProperty().set(this.radiusProperty().multiply(zScale).doubleValue());
		this.setScaleX(zScale);
		this.setScaleY(zScale);
		this.setScaleZ(zScale);

		this.v.setV(this.p.getVel());
		this.v.resetPos(x, y, 100);

		this.a.setV(this.p.getAcc());
		this.a.resetPos(x, y, 100000);

		this.f.setV(this.p.getNetF());
		this.f.resetPos(x, y, 0.00007);
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
	
	public Planet getP() {
		return p;
	}

		
}
