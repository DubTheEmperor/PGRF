package objectData;

import rasterData.RasterBI;
import rasterOps.Liner;
import rasterOps.Polygoner;
import rasterOps.Shape;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Shape
{
	private List<Point2D> points;
	private int color, thickness;

	public Polygon(int color, int thickness)
	{
		this.points = new ArrayList<>();
		this.color = color;
		this.thickness = thickness;
	}

	public void addPoint(Point2D point)
	{
		points.add(point);
	}

	public Point2D getPoint(int index)
	{
		return points.get(index);
	}

	public int getColor()
	{
		return color;
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

	@Override
	public void draw(RasterBI img, Liner liner)
	{
		new Polygoner().draw(img, this, liner);
	}
}
