package objectData;

import rasterData.RasterBI;
import rasterOps.Liner;

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

	@Override
	public void draw(RasterBI img, Liner liner)
	{
		liner.draw(img, this);
	}
}
