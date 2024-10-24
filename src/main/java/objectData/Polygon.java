package objectData;

import rasterData.Raster;
import rasterData.RasterBI;
import rasterOps.Liner;
import rasterOps.Polygoner;

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

	public Polygon()
	{
		this.points = new ArrayList<>();
		this.color = 0x000000;
		this.thickness = 1;
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

	public List<Line> toLines() {
		List<Line> lines = new ArrayList<>();
		if (points.size() < 2) {
			return lines;
		}

		for (int i = 0; i < points.size() - 1; i++) {
			lines.add(new Line(points.get(i), points.get(i + 1), color, thickness));
		}

		lines.add(new Line(points.getLast(), points.getFirst(), color, thickness));

		return lines;
	}

	public Polygon translate(int dx, int dy)
	{
		Polygon result = new Polygon(color, thickness);

		for (int i = 0; i < this.size(); i++)
		{
			result.addPoint(this.getPoint(i).translate(dx, dy));
		}

		return result;
	}

	public Polygon scale(int k)
	{
		Polygon result = new Polygon(color, thickness);

		for (int i = 0; i < this.size(); i++)
		{
			result.addPoint(this.getPoint(i).scale(k));
		}

		return result;
	}

	public Polygon rotate(int alpha)
	{
		Polygon result = new Polygon(color, thickness);

		for (int i = 0; i < this.size(); i++)
		{
			result.addPoint(this.getPoint(i).rotate(alpha));
		}

		return result;
	}

	@Override
	public void draw(Raster img, Liner liner)
	{
		new Polygoner().draw(img, this, liner);
	}
}
