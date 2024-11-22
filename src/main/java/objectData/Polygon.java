package objectData;

import rasterData.Raster;
import rasterOps.Liner;
import rasterOps.Polygoner;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Shape
{
	protected List<Point2D> points;
	private int color, thickness;
	private int backgroundColor = 0xff00ff;
	private boolean isFilled;

	public Polygon(int color, int thickness, boolean isFilled)
	{
		this.points = new ArrayList<>();
		this.color = color;
		this.thickness = thickness;
		this.isFilled = isFilled;
	}

	public Polygon(int color, int thickness, boolean isFilled, List<Point2D> points)
	{
		this.points = points;
		this.color = color;
		this.thickness = thickness;
		this.isFilled = isFilled;
	}

	public Polygon()
	{
		this.points = new ArrayList<>();
		this.color = 0x000000;
		this.thickness = 1;
		this.isFilled = false;
	}

	public boolean isFilled()
	{
		return isFilled;
	}

	public void addPoint(Point2D point)
	{
		points.add(point);
	}

	public Point2D getPoint(int index)
	{
		return points.get(index);
	}

	public List<Point2D> getPoints()
	{
		return points;
	}

	public int getColor()
	{
		return color;
	}

	public int getBackgroundColor()
	{
		return backgroundColor;
	}

	public int getThickness()
	{
		return thickness;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public int size()
	{
		return points.size();
	}

	public List<Line> toLines()
	{
		List<Line> lines = new ArrayList<>();
		if (points.size() < 2)
		{
			return lines;
		}

		for (int i = 0; i < points.size() - 1; i++)
		{
			lines.add(new Line(points.get(i), points.get(i + 1), color, thickness));
		}

		lines.add(new Line(points.getLast(), points.getFirst(), color, thickness));

		return lines;
	}

	public Polygon translate(int dx, int dy)
	{
		Polygon result = new Polygon(color, thickness, isFilled);

		for (int i = 0; i < this.size(); i++)
		{
			result.addPoint(this.getPoint(i).translate(dx, dy));
		}

		return result;
	}

	public Polygon scale(int k)
	{
		Polygon result = new Polygon(color, thickness, isFilled);

		for (int i = 0; i < this.size(); i++)
		{
			result.addPoint(this.getPoint(i).scale(k));
		}

		return result;
	}

	public Polygon rotate(int alpha)
	{
		Polygon result = new Polygon(color, thickness, isFilled);

		for (int i = 0; i < this.size(); i++)
		{
			result.addPoint(this.getPoint(i).rotate(alpha));
		}

		return result;
	}

	@Override
	public void draw(Raster img, Liner liner)
	{
		new Polygoner().draw(img, this, liner, isFilled);
	}
}
