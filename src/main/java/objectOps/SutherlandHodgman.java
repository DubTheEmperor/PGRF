package objectOps;

import objectData.Point2D;
import objectData.Polygon;

import java.util.ArrayList;
import java.util.List;

public class SutherlandHodgman
{
	public static Polygon clipPolygon(Polygon subjectPolygon, Polygon clipPolygon)
	{
		List<Point2D> subjectPoints = subjectPolygon.getPoints();
		List<Point2D> clipPoints = clipPolygon.getPoints();

		for (int i = 0; i < clipPoints.size(); i++)
		{
			Point2D clipStart = clipPoints.get(i);
			Point2D clipEnd = clipPoints.get((i + 1) % clipPoints.size());
			subjectPoints = clipAgainstEdge(subjectPoints, clipStart, clipEnd);
		}

		Polygon result = new Polygon(subjectPolygon.getColor(), subjectPolygon.getThickness(), subjectPolygon.isFilled(), subjectPolygon.getFillPattern());
		for (Point2D point : subjectPoints)
		{
			result.addPoint(point);
		}

		return result;
	}

	private static List<Point2D> clipAgainstEdge(List<Point2D> subjectPoints, Point2D clipStart, Point2D clipEnd)
	{
		List<Point2D> newPoints = new ArrayList<>();

		for (int i = 0; i < subjectPoints.size(); i++)
		{
			Point2D current = subjectPoints.get(i);
			Point2D prev = subjectPoints.get((i + subjectPoints.size() - 1) % subjectPoints.size());

			boolean currentInside = isInside(current, clipStart, clipEnd);
			boolean prevInside = isInside(prev, clipStart, clipEnd);

			if (currentInside)
			{
				if (!prevInside)
				{
					newPoints.add(intersection(prev, current, clipStart, clipEnd));
				}
				newPoints.add(current);
			}
			else if (prevInside)
			{
				newPoints.add(intersection(prev, current, clipStart, clipEnd));
			}
		}

		return newPoints;
	}

	private static boolean isInside(Point2D point, Point2D edgeStart, Point2D edgeEnd)
	{
		double dx = edgeEnd.getX() - edgeStart.getX();
		double dy = edgeEnd.getY() - edgeStart.getY();

		// Calculate cross product
		double crossProduct = dx * (point.getY() - edgeStart.getY()) - dy * (point.getX() - edgeStart.getX());

		// Use a small tolerance to handle edge cases
		double epsilon = 1e-9;
		return crossProduct >= -epsilon;
	}

	private static Point2D intersection(Point2D start1, Point2D end1, Point2D start2, Point2D end2)
	{
		int a1 = end1.getY() - start1.getY();
		int b1 = start1.getX() - end1.getX();
		int c1 = a1 * start1.getX() + b1 * start1.getY();

		int a2 = end2.getY() - start2.getY();
		int b2 = start2.getX() - end2.getX();
		int c2 = a2 * start2.getX() + b2 * start2.getY();

		int determinant = a1 * b2 - a2 * b1;

		if (determinant == 0)
		{
			return null;
		}

		int x = (b2 * c1 - b1 * c2) / determinant;
		int y = (a1 * c2 - a2 * c1) / determinant;

		return new Point2D(x, y);
	}
}
