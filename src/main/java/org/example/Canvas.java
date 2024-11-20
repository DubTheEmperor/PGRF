package org.example;

import ToolBarData.ToolBar;
import objectData.*;
import rasterData.RasterBI;
import rasterOps.Polygoner;
import rasterOps.ScanLine;
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

	private boolean shiftPressed;

	private TrivialLiner liner;
	private Polygoner polygoner;

	private int r1;
	private int c1;
	private int r2;
	private int c2;

	private Polygon polygon;
	private Line line;

	private List<Shape> shapes;

	public Canvas(int width, int height, ToolBar toolBar)
	{
		shiftPressed = false;
		img = new RasterBI(width, height);
		liner = new TrivialLiner();
		polygoner = new Polygoner();
		shapes = new ArrayList<>();

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
				{
					addPolygon();
				}

				if (selectedButton == ToolBar.FILL_BUTTON)
				{
					Optional<Integer> maybeBackgroundColor = img.getColor(c1, r1);

					if (maybeBackgroundColor.isPresent())
					{
						int backgroundColor = maybeBackgroundColor.get();
						int fillColor = 0x000000;
						List<Point2D> filledPoints = SeedFill4BG.fill(img, c1, r1, backgroundColor, fillColor);
						shapes.add(new FilledArea(filledPoints, fillColor));
					}
				}
				else if (selectedButton == ToolBar.POLYGON_BUTTON)
				{
					img.clear();

					if (polygon == null)
						polygon = new Polygon(0x000000, toolBar.getThickness());

					polygon.addPoint(new Point2D(c1, r1));
					polygoner.draw(img, polygon, liner);
				}
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				int selectedButton = toolBar.getSelectedButton();

				if (selectedButton == ToolBar.LINE_BUTTON)
				{
					shapes.add(line);
				}
			}
		});

		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				int selectedButton = toolBar.getSelectedButton();

				if (selectedButton == ToolBar.LINE_BUTTON)
				{
					img.clear();

					int x = e.getX();
					int y = e.getY();

					line = new Line(new Point2D(c1, r1), new Point2D(x, y), 0x000000, toolBar.getThickness());

					if ((e.getModifiersEx() & MouseEvent.SHIFT_DOWN_MASK) != 0)
					{
						alignLine(line);
					}

					liner.draw(img, line);
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
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for (Shape shape : shapes)
		{
			shape.draw(img, liner);
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

	private void alignLine(Line line)
	{
		Point2D point1 = line.getPoint1();
		Point2D point2 = line.getPoint2();

		int c1 = point1.getX();
		int r1 = point1.getY();
		int c2 = point2.getX();
		int r2 = point2.getY();

		// Calculate the differences
		int dc = c2 - c1;
		int dr = r2 - r1;

		// Threshold for determining diagonal vs horizontal/vertical
		int threshold = 100;

		if (Math.abs(dr) <= threshold)
		{
			//align to horizontal
			r2 = r1;
		} else if (Math.abs(dc) <= threshold)
		{
			//align to vertical
			c2 = c1;
		} else
		{
			//align to diagonal
			int signX = Integer.signum(dc);
			int signY = Integer.signum(dr);
			int delta = Math.min(Math.abs(dc), Math.abs(dr));
			c2 = c1 + signX * delta;
			r2 = r1 + signY * delta;
		}

		line.setPoint2(new Point2D(c2, r2));
	}

	private void start()
	{
		img.clear();
		repaint();
	}

	private void clearCanvas()
	{
		polygon = null;
		img.clear();
		shapes.clear();
		repaint();
	}
}