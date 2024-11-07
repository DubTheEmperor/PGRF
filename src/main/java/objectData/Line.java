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

	public Point2D toVec()
	{
		int dx = point2.getX() - point1.getX();
		int dy = point2.getY() - point1.getY();

		return new Point2D(dx, dy);
	}

	public boolean isInside(Point2D point)
	{
		// Get the vector AB (from point1 to point2) using toVec()
		Point2D vecAB = toVec();

		// Calculate the normal vector to vecAB
		int normalX = vecAB.getY();
		int normalY = -vecAB.getX();

		// Vector AP (from point1 to the given point)
		int vx = point.getX() - point1.getX();
		int vy = point.getY() - point1.getY();
		Point2D vecAP = new Point2D(vx, vy);

		// Compute the dot product between the normal vector and vecAP
		int dotProduct = normalX * vecAP.getX() + normalY * vecAP.getY();

		// If the dot product is zero, the point is on the line
		if (dotProduct != 0) {
			return false; // The point is not collinear with vecAB
		}

		// Perform the bounds check (same as before) to ensure point is within the line segment
		int dotProductABAP = vecAB.getX() * vecAP.getX() + vecAB.getY() * vecAP.getY();
		if (dotProductABAP < 0) {
			return false; // Point is outside the segment on the "point1" side
		}

		// Check if the squared length of AB is at least as large as that of AP
		int lengthSquaredAB = vecAB.getX() * vecAB.getX() + vecAB.getY() * vecAB.getY();
		int lengthSquaredAP = vecAP.getX() * vecAP.getX() + vecAP.getY() * vecAP.getY();
		return lengthSquaredAP <= lengthSquaredAB;
	}


	public Optional<Point2D> interception(Line other)
	{
		float x1 = point1.getX();
		float y1 = point1.getY();
		float x2 = point2.getX();
		float y2 = point2.getY();

		float x3 = other.getPoint1().getX();
		float y3 = other.getPoint1().getY();
		float x4 = other.getPoint2().getX();
		float y4 = other.getPoint2().getY();

		// Calculate the denominator to check if lines are parallel
		float denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (denominator == 0)
		{
			return Optional.empty(); // Lines are parallel
		}

		// Calculate intersection point
		float px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
		float py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;

		// Check if intersection is within both line segments
		Point2D intersection = new Point2D(Math.round(px), Math.round(py));
		if (this.isInside(intersection) && other.isInside(intersection))
		{
			return Optional.of(intersection);
		}

		return Optional.empty();
	}

	@Override
	public void draw(RasterBI img, Liner liner)
	{
		liner.draw(img, this);
	}
}
