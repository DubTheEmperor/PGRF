package objectData;

import rasterData.Raster;
import rasterOps.Liner;
import java.util.ArrayList;

public class RegularPentagon extends Polygon
{

	private Point2D center;
	private int radius;

	public RegularPentagon(Point2D center, int radius, int color, int thickness, boolean isFilled)
	{
		super(color, thickness, isFilled);
		this.center = center;
		this.radius = radius;
		generateVertices();
	}

	private void generateVertices()
	{
		points = new ArrayList<>();
		double angleStep = 2 * Math.PI / 5;
		for (int i = 0; i < 5; i++)
		{
			double angle = i * angleStep - Math.PI / 2;
			int x = center.getX() + (int) (radius * Math.cos(angle));
			int y = center.getY() + (int) (radius * Math.sin(angle));
			points.add(new Point2D(x, y));
		}
	}

	public Point2D getCenter()
	{
		return center;
	}

	public void setCenter(Point2D center)
	{
		this.center = center;
		generateVertices();
	}

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
		generateVertices();
	}

	public static double distanceBetween(Point2D center, Point2D other)
	{
		int dx = other.getX() - center.getX();
		int dy = other.getY() - center.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public void draw(Raster img, Liner liner)
	{
		super.draw(img, liner);
	}
}
