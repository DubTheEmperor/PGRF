package org.example;

import fillPatterns.CheckerboardPattern;
import fillPatterns.SolidPattern;
import objectData3D.Cube;
import objectData3D.Curve;
import objectData3D.Scene;
import objectOps.SutherlandHodgman;
import rasterOps3D.Renderer3DLine;
import toolBarData.ToolBar;
import objectData.*;
import rasterData.RasterBI;
import rasterOps.Polygoner;
import rasterOps.SeedFill4BG;
import rasterOps.TrivialLiner;
import transfroms.Camera;
import transfroms.Mat4PerspRH;
import transfroms.Vec2D;
import transfroms.Vec3D;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.*;

public class Canvas extends JPanel
{
	private RasterBI img;

	private int currentX;
	private int currentY;

	private TrivialLiner liner;
	private Polygoner polygoner;

	private int y1;
	private int x1;
	private int r2;
	private int c2;

	private Polygon polygon;
	private Line line;
	private RegularPentagon regularPentagon;
	private Polygon cuttingPolygon;

	private Scene scene;
	private Renderer3DLine renderer3D;
	private Camera camera;

	private List<Shape> shapes;

	private Shape currentShape;

	public Canvas(int width, int height, ToolBar toolBar)
	{
		img = new RasterBI(width, height);
		liner = new TrivialLiner();
		polygoner = new Polygoner();
		shapes = new ArrayList<>();

		scene = new Scene();
		scene.add(new Cube());
		scene.add(new Curve());
		renderer3D = new Renderer3DLine();
		camera = new Camera();

		currentX = img.getWidth() / 2;
		currentY = img.getHeight() / 2;

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

				if (selectedButton != ToolBar.POLYGON_BUTTON)
					addPolygon();

				if(selectedButton != ToolBar.CUT_BUTTON)
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
					img.clear();

					if (polygon == null)
						polygon = new Polygon(0x000000, toolBar.getThickness(), true, new SolidPattern(0xff00ff));

					polygon.addPoint(new Point2D(x1, y1));
					currentShape = polygon;
					polygoner.draw(img, polygon, liner, polygon.isFilled());
				}
				else if (selectedButton == ToolBar.CUT_BUTTON)
				{
					img.clear();

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
					if(regularPentagon != null)
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
				else if(selectedButton == ToolBar.CAMERA_BUTTON)
				{
					double dx = x1 - x;
					double dy = y1 - y;
					camera.addAzimuth(dx / 100).addZenith(dy / 100);
				}
				repaint();
			}
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
		setFocusable(true);

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "cutPolygons");
		getActionMap().put("cutPolygons", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < shapes.size(); i++) {
					Shape shape = shapes.get(i);
					if (shape instanceof Polygon) {
						Polygon clipped = SutherlandHodgman.clipPolygon((Polygon) shape, cuttingPolygon);
						if (clipped.size() > 2) {
							System.out.println("Clipping succeeded for shape " + i);
							shapes.set(i, clipped);
						} else {
							System.out.println("Clipping failed or resulted in invalid polygon for shape " + i);
						}
					}
				}
				cuttingPolygon = null;
				img.clear();
				repaint();
			}
		});
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

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

	private double azimuthToOrigin(Vec3D observerPosition)
	{
		return observerPosition
				.opposite()
				.ignoreZ()
				.normalized()
				.map(viewNormalized -> {
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
				.flatMap(view -> view.withZ(0).normalized().map(projection -> {
					double angle = Math.acos(view.dot(projection));
					return (view.getZ() > 0) ? angle : -angle;
				}))
				.orElse(0.0);
	}

	private void start()
	{
		img.clear();
//		Vec3D observerPosition = new Vec3D(2, 2, 2);
//		camera = new Camera()
//				.withPosition(observerPosition)
//				.withAzimuth(azimuthToOrigin(observerPosition))
//				.withZenith(zenithToOrigin(observerPosition));
//		renderer3D.renderScene(img, scene, camera.getViewMatrix(), new Mat4PerspRH(Math.PI / 2, (double)img.getHeight() / img.getWidth(), 0.01, 100), liner, 0xff0000);
		renderScene();
		repaint();
	}

	private void renderScene()
	{
		Vec3D observerPosition = new Vec3D(2, 2, 2);
		camera = new Camera()
				.withPosition(observerPosition)
				.withAzimuth(azimuthToOrigin(observerPosition))
				.withZenith(zenithToOrigin(observerPosition));
		renderer3D.renderScene(img, scene, camera.getViewMatrix(), new Mat4PerspRH(Math.PI / 2, (double)img.getHeight() / img.getWidth(), 0.01, 100), liner, 0xff0000);
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