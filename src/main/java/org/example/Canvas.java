package org.example;

import fillPatterns.CheckerboardPattern;
import fillPatterns.SolidPattern;
import objectData.Point2D;
import objectData3D.*;
import objectOps.SutherlandHodgman;
import rasterOps3D.Renderer3DLine;
import toolBarData.ToolBar;
import objectData.*;
import rasterData.RasterBI;
import rasterOps.Polygoner;
import rasterOps.SeedFill4BG;
import rasterOps.TrivialLiner;
import transfroms.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.*;

public class Canvas extends JPanel
{
	private JLabel chosenObjectLabel;
	private RasterBI img;
	ToolBar toolBar;
	private Timer animationTimer;

	private int currentX;
	private int currentY;

	private TrivialLiner liner;
	private Polygoner polygoner;

	private int x1;
	private int y1;

	private Polygon polygon;
	private Line line;
	private RegularPentagon regularPentagon;
	private Polygon cuttingPolygon;

	private Scene scene;
	private Renderer3DLine renderer3D;
	private Camera camera;
	private double cameraSpeed;
	private Object3D chosenObject;
	private Mat4 projectionMatrix;

	private List<Shape> shapes;
	private Shape currentShape;

	public Canvas(int width, int height, ToolBar toolBar)
	{
		this.toolBar = toolBar;
		chosenObjectLabel = new JLabel("Chosen object: ");
		this.add(chosenObjectLabel);

		img = new RasterBI(width, height);
		liner = new TrivialLiner();
		polygoner = new Polygoner();
		shapes = new ArrayList<>();

		camera = new Camera();
		scene = new Scene();
		scene.add(new XAxis(0xff0000));
		scene.add(new YAxis(0x00ff00));
		scene.add(new ZAxis(0x0000ff));
		Cube cube = new Cube(0x000000);
		addAnimation(cube);
		scene.add(cube);
		scene.add(new Pyramid(0x000000));
		scene.add(new Prism(0x000000));
		scene.add(new Curve(
				0x800800,
				Cubic.BEZIER,
				new Point3D(-1, -1, -1),
				new Point3D(1, -1, -1),
				new Point3D(-1, 1, 1),
				new Point3D(1, 1, 1)
		));
		scene.add(new Curve(
				0xff00ff,
				Cubic.FERGUSON,
				new Point3D(-1, -1, -1),
				new Point3D(1, -1, -1),
				new Point3D(-1, 1, 1),
				new Point3D(1, -1, 1)
		));
		scene.add(new Curve(
				0x00ffff,
				Cubic.COONS,
				new Point3D(-2, -7, 5),
				new Point3D(4, 1, -1),
				new Point3D(4, -3, -1),
				new Point3D(10, 5, 5)
		));
		renderer3D = new Renderer3DLine();
		projectionMatrix = new Mat4PerspRH(
				Math.PI / 2,
				(double) img.getHeight() / img.getWidth(),
				0.01,
				100
		);

		currentX = img.getWidth() / 2;
		currentY = img.getHeight() / 2;

		cameraSpeed = 0.5;

		setPreferredSize(new Dimension(width, height));

		start();

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int selectedButton = toolBar.getSelectedButton();
				x1 = e.getX();
				y1 = e.getY();

				if (SwingUtilities.isRightMouseButton(e))
				{
					// Handle right-click to select the closest Object3D
					selectClosestObject(e.getX(), e.getY());
					return;
				}

				if (selectedButton != ToolBar.POLYGON_BUTTON)
					addPolygon();

				if (selectedButton != ToolBar.CUT_BUTTON)
				{
					cuttingPolygon = null;
				}

				if (selectedButton == ToolBar.FILL_BUTTON)
				{
					Optional<Integer> maybeBackgroundColor = img.getColor(x1, y1);

					if (maybeBackgroundColor.isPresent())
					{
						int backgroundColor = maybeBackgroundColor.get();
						int fillColor = 0x008000;
						List<Point2D> filledPoints = SeedFill4BG.fill(img, x1, y1, backgroundColor, fillColor);
						FilledArea filledArea = new FilledArea(filledPoints, fillColor);
						currentShape = filledArea;
						shapes.add(filledArea);
					}
				}
				else if (selectedButton == ToolBar.POLYGON_BUTTON)
				{
					if (polygon == null)
						polygon = new Polygon(0x000000, toolBar.getThickness(), true, new SolidPattern(0xff00ff));

					polygon.addPoint(new Point2D(x1, y1));
					currentShape = polygon;
					polygoner.draw(img, polygon, liner, polygon.isFilled());
				}
				else if (selectedButton == ToolBar.CUT_BUTTON)
				{
					if (cuttingPolygon == null)
						cuttingPolygon = new Polygon(0xff0000, 1, false, new SolidPattern(0xff00ff));

					cuttingPolygon.addPoint(new Point2D(x1, y1));
					currentShape = cuttingPolygon;
					polygoner.draw(img, cuttingPolygon, liner, cuttingPolygon.isFilled());
				}
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{

				int selectedButton = toolBar.getSelectedButton();

				if (selectedButton != ToolBar.NONE)
					currentShape = null;

				if (selectedButton == ToolBar.LINE_BUTTON)
				{
					shapes.add(line);
				}
				else if (selectedButton == ToolBar.REGULAR_PENTAGON_BUTTON)
				{
					if (regularPentagon != null)
						shapes.add(regularPentagon);
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				int selectedButton = toolBar.getSelectedButton();
				int x = e.getX();
				int y = e.getY();
				img.clear();

				if (selectedButton == ToolBar.LINE_BUTTON)
				{
					line = new Line(new Point2D(x1, y1), new Point2D(x, y), 0x000000, toolBar.getThickness());
					currentShape = line;

					if ((e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0)
					{
						liner.alignLine(line);
					}

					liner.draw(img, line);
				}
				else if (selectedButton == ToolBar.REGULAR_PENTAGON_BUTTON)
				{
					Point2D center = new Point2D(x1, y1);
					Point2D secondPoint = new Point2D(x, y);

					regularPentagon = new RegularPentagon(center, (int) RegularPentagon.distanceBetween(center, secondPoint), 0x000000, toolBar.getThickness(), true, new CheckerboardPattern(0xff00ff, 10));
					currentShape = regularPentagon;

					polygoner.draw(img, regularPentagon, liner, regularPentagon.isFilled());
				}
				else if (selectedButton == ToolBar.NONE)
				{
					double dx = x - x1;
					double dy = y - y1;

					// Adjust camera azimuth and zenith based on mouse movement
					camera = camera.addAzimuth(-dx / 100).addZenith(-dy / 100);

					// Update x1 and y1 to the new mouse position
					x1 = x;
					y1 = y;
				}
				repaint();
			}
		});

		addMouseWheelListener(e ->
		{
			int notches = e.getWheelRotation();
			if (notches < 0)
			{
				// Scrolling up (zoom in)
				camera = camera.forward(cameraSpeed);
			}
			else
			{
				// Scrolling down (zoom out)
				camera = camera.backward(cameraSpeed);
			}
			repaint();
		});


		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("C"), "clearCanvas");
		getActionMap().put("clearCanvas", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				clearCanvas();
			}
		});

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "cutPolygons");
		getActionMap().put("cutPolygons", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < shapes.size(); i++)
				{
					Shape shape = shapes.get(i);
					if (shape instanceof Polygon)
					{
						Polygon clipped = SutherlandHodgman.clipPolygon((Polygon) shape, cuttingPolygon);
						if (clipped.size() > 2)
						{
							System.out.println("Clipping succeeded for shape " + i);
							shapes.set(i, clipped);
						}
						else
						{
							System.out.println("Clipping failed or resulted in invalid polygon for shape " + i);
						}
					}
				}
				cuttingPolygon = null;
				repaint();
			}
		});

		// Bind keys for camera movement
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "cameraForward");
		getActionMap().put("cameraForward", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Move the camera forward
				camera = camera.up(cameraSpeed);
				repaint();
			}
		});

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "cameraBackward");
		getActionMap().put("cameraBackward", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Move the camera backward
				camera = camera.down(cameraSpeed);
				repaint();
			}
		});

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "cameraLeft");
		getActionMap().put("cameraLeft", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Move the camera left
				camera = camera.left(cameraSpeed);
				repaint();
			}
		});

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "cameraRight");
		getActionMap().put("cameraRight", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// Move the camera right
				camera = camera.right(cameraSpeed);
				repaint();
			}
		});
		setFocusable(true);
		requestFocusInWindow();
	}

	public void addAnimation(Object3D object)
	{
		if (object == null) return;

		if (animationTimer != null)
		{
			animationTimer.stop();
		}

		animationTimer = new Timer(30, e ->
		{
			object.rotateX(Math.toRadians(2));
			repaint();
		});

		animationTimer.start();
	}

	public void stopAnimation()
	{
		if (animationTimer != null)
		{
			animationTimer.stop();
		}
	}

	// Method to set perspective projection
	public void setPerspectiveProjection()
	{
		this.projectionMatrix = new Mat4PerspRH(
				Math.PI / 2,
				(double) img.getHeight() / img.getWidth(),
				0.01,
				100
		);
		repaint(); // Trigger a repaint to apply the new projection immediately
	}

	// Method to set orthogonal projection
	public void setOrthogonalProjection()
	{
		double width = 20;  // Total width of the visible cuboid (right - left)
		double height = 20; // Total height of the visible cuboid (top - bottom)
		double zn = 0.01;   // Near plane distance
		double zf = 100;    // Far plane distance

		this.projectionMatrix = new Mat4OrthoRH(width, height, zn, zf);
		repaint(); // Trigger a repaint to apply the new projection immediately
	}


	@Override
	public void paintComponent(Graphics g)
	{
		img.clear();

		super.paintComponent(g);

		renderer3D.renderScene(img, scene, camera.getViewMatrix(), projectionMatrix, liner);

		for (Shape shape : shapes)
		{
			shape.draw(img, liner);
		}

		if (currentShape != null)
		{
			currentShape.draw(img, liner);
		}

		img.present(g);
	}

	public void addPolygon()
	{
		if (polygon != null)
		{
			shapes.add(polygon);
			polygon = null;
		}
	}

	public void rotateX()
	{
		if(chosenObject != null)
		{
			chosenObject.rotateX(Math.toRadians(20));
			repaint();
		}
	}

	public void rotateY()
	{
		if(chosenObject != null)
		{
			chosenObject.rotateY(Math.toRadians(20));
			repaint();
		}
	}

	public void rotateZ()
	{
		if(chosenObject != null)
		{
			chosenObject.rotateZ(Math.toRadians(20));
			repaint();
		}
	}

	public void scale(double factor)
	{
		if(chosenObject != null)
		{
			Point3D center = chosenObject.getCenter();
			chosenObject.scale(factor);
			chosenObject.moveTo(center.getX(), center.getY(), center.getZ());
			repaint();
		}
	}

	public void moveTo(double x, double y, double z)
	{
		if(chosenObject != null)
		{
			chosenObject.moveTo(x, y, z);
			repaint();
		}
	}

	private double azimuthToOrigin(Vec3D observerPosition)
	{
		return observerPosition
				.opposite()
				.ignoreZ()
				.normalized()
				.map(viewNormalized ->
				{
					double angle = Math.acos(viewNormalized.dot(new Vec2D(1, 0)));
					return (viewNormalized.getY() > 0 ? angle : 2 * Math.PI - angle);
				})
				.orElse(0.0);
	}

	private double zenithToOrigin(Vec3D observerPosition)
	{
		return observerPosition
				.opposite()
				.normalized()
				.flatMap(view -> view.withZ(0).normalized().map(projection ->
				{
					double angle = Math.acos(view.dot(projection));
					return (view.getZ() > 0) ? angle : -angle;
				}))
				.orElse(0.0);
	}

	private void selectClosestObject(int mouseX, int mouseY)
	{
		double minDistance = Double.MAX_VALUE;
		double minDepth = Double.MAX_VALUE; // Track the smallest depth
		Object3D closestObject = null;

		Mat4 projectionMatrix;

		// Use the current projection matrix
		if (this.projectionMatrix instanceof Mat4PerspRH)
			projectionMatrix = new Mat4PerspRH(Math.PI / 2, (double) img.getHeight() / img.getWidth(), 0.01, 100);
		else
			projectionMatrix = new Mat4OrthoRH(20, 20, 0.01, 100);

		for (Object3D object : scene.getObjects())
		{
			if (object instanceof XAxis || object instanceof YAxis || object instanceof ZAxis)
				continue;

			// Calculate the screen projection of the Object's center
			Point3D center = object.getCenter();
			Point3D projectedCenter = center
					.mul(camera.getViewMatrix())
					.mul(projectionMatrix);

			// Extract screen coordinates (normalized)
			int screenX = (int) ((projectedCenter.getX() / projectedCenter.getW() + 1) * img.getWidth() / 2);
			int screenY = (int) ((1 - projectedCenter.getY() / projectedCenter.getW()) * img.getHeight() / 2);
			double depth = projectedCenter.getZ() / projectedCenter.getW(); // Depth after perspective division

			// Calculate the Euclidean distance
			double distance = Math.sqrt(Math.pow(screenX - mouseX, 2) + Math.pow(screenY - mouseY, 2));

			// Select the closest object based on depth and distance
			if (distance < minDistance || (distance == minDistance && depth < minDepth))
			{
				minDistance = distance;
				minDepth = depth;
				closestObject = object;
			}
		}

		if (closestObject != null)
		{
			chosenObject = closestObject;
			chosenObjectLabel.setText("Chosen object: " + chosenObject.getClass().getSimpleName());
			toolBar.setXPosition(chosenObject.getCenter().getX());
			toolBar.setYPosition(chosenObject.getCenter().getY());
			toolBar.setZPosition(chosenObject.getCenter().getZ());
		}
	}


	private void start()
	{
		img.clear();
		Vec3D observerPosition = new Vec3D(6, 6, 6);
		camera = new Camera()
				.withPosition(observerPosition)
				.withAzimuth(azimuthToOrigin(observerPosition))
				.withZenith(zenithToOrigin(observerPosition));
		renderer3D.renderScene(img, scene, camera.getViewMatrix(), new Mat4PerspRH(Math.PI / 2, (double) img.getHeight() / img.getWidth(), 0.01, 100), liner);
		repaint();
	}

	private void clearCanvas()
	{
		cuttingPolygon = null;
		polygon = null;
		currentShape = null;
		img.clear();
		shapes.clear();
		repaint();
	}
}