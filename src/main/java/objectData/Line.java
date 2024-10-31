package objectData;

import rasterData.RasterBI;
import rasterOps.Liner;

import java.util.Optional;

public class Line implements Shape
{
	private Point2D point1, point2;
	private int color, thickness;

	public Line(Point2D point1, Point2D point2, int color)
	{
		this.point1 = point1;
		this.point2 = point2;
		this.color = color;
		this.thickness = 1;
	}

	public Line(Point2D point1, Point2D point2, int color, int thickness)
	{
		this.point1 = point1;
		this.point2 = point2;
		this.color = color;
		this.thickness = thickness;
	}

	public Point2D getPoint1()
	{
		return point1;
	}

	public Point2D getPoint2()
	{
		return point2;
	}

	public void setPoint1(Point2D point1)
	{
		this.point1 = point1;
	}

	public void setPoint2(Point2D point2)
	{
		this.point2 = point2;
	}

	public int getColor()
	{
		return color;
	}

	public int getThickness()
	{
		return thickness;
	}

	public boolean isHorizontal()
	{
        return point1.getY() == point2.getY();
	}

	public Optional<Float> yIntercept(int y)
	{
		if(!hasIntercept(y))
			return Optional.empty();

		float x1 = point1.getX();
		float y1 = point1.getY();
		float x2 = point2.getX();
		float y2 = point2.getY();

		if(x2 - x1 == 0)
			return Optional.of(x1);

		float m = (y2 - y1) / (x2 - x1);

		float b = y1 - m * x1;

		return Optional.of(b);
	}

	private boolean hasIntercept(int y)
	{
		return y < Math.max(point1.getY(), point2.getY()) && y > Math.min(point1.getY(), point2.getY());
	}

	public void reverse()
	{
		Point2D tmp = point1;
		point1 = point2;
		point2 = tmp;
	}

	@Override
	public void draw(RasterBI img, Liner liner)
	{
		liner.draw(img, this);
	}
}
