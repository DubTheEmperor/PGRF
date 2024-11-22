package org.example;

import fillPatterns.CheckerboardPattern;
import fillPatterns.SolidPattern;
import objectOps.SutherlandHodgman;
import toolBarData.ToolBar;
import objectData.*;
import rasterData.RasterBI;
import rasterOps.Polygoner;
import rasterOps.SeedFill4BG;
import rasterOps.TrivialLiner;

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

	private int r1;
	private int c1;
	private int r2;
	private int c2;

	private Polygon polygon;
	private Line line;
	private RegularPentagon regularPentagon;
	private Polygon cuttingPolygon;

	private List<Shape> shapes;

	private Shape currentShape;

	private SutherlandHodgman clipPolygon;

	public Canvas(int width, int height, ToolBar toolBar)
	{
		img = new RasterBI(width, height);
		liner = new TrivialLiner();
		polygoner = new Polygoner();
		shapes = new ArrayList<>();
		clipPolygon = new SutherlandHodgman();

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
				c1 = e.getX();
				r1 = e.getY();

				if (selectedButton != ToolBar.POLYGON_BUTTON)
					addPolygon();

				if(selectedButton != ToolBar.CUT_BUTTON)
				{
					cuttingPolygon = null;
				}

				if (selectedButton == ToolBar.FILL_BUTTON)
				{
					Optional<Integer> maybeBackgroundColor = img.getColor(c1, r1);

					if (maybeBackgroundColor.isPresent())
					{
						int backgroundColor = maybeBackgroundColor.get();
						int fillColor = 0x008000;
						List<Point2D> filledPoints = SeedFill4BG.fill(img, c1, r1, backgroundColor, fillColor);
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

					polygon.addPoint(new Point2D(c1, r1));
					currentShape = polygon;
					polygoner.draw(img, polygon, liner, polygon.isFilled());
				}
				else if (selectedButton == ToolBar.CUT_BUTTON)
				{
					img.clear();

					if (cuttingPolygon == null)
						cuttingPolygon = new Polygon(0xff0000, 1, false, new SolidPattern(0xff00ff));

					cuttingPolygon.addPoint(new Point2D(c1, r1));
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
					line = new Line(new Point2D(c1, r1), new Point2D(x, y), 0x000000, toolBar.getThickness());
					currentShape = line;

					if ((e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0)
					{
						liner.alignLine(line);
					}

					liner.draw(img, line);
				}
				else if (selectedButton == ToolBar.REGULAR_PENTAGON_BUTTON)
				{
					Point2D center = new Point2D(c1, r1);
					Point2D secondPoint = new Point2D(x, y);

					regularPentagon = new RegularPentagon(center, (int) RegularPentagon.distanceBetween(center, secondPoint), 0x000000, toolBar.getThickness(), true, new CheckerboardPattern(0xff00ff, 10));
					currentShape = regularPentagon;

					polygoner.draw(img, regularPentagon, liner, regularPentagon.isFilled());
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

		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("B"), "cutPolygons");
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

	private void start()
	{
		img.clear();
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