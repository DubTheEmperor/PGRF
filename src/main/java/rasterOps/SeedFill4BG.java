package rasterOps;

import objectData.Point2D;
import rasterData.Raster;

import java.util.*;

public class SeedFill4BG
{
	public static List<Point2D> fill(Raster raster, int c, int r, int bgColor, int fillColor)
	{
		List<Point2D> filledPoints = new ArrayList<>();

		if ((bgColor & 0xffffff) == (fillColor & 0xffffff))
		{
			return filledPoints;
		}

		Stack<Point2D> stack = new Stack<>();
		stack.push(new Point2D(c, r));

		while (!stack.isEmpty())
		{
			Point2D point = stack.pop();
			int x = point.getX();
			int y = point.getY();

			Optional<Integer> maybeColor = raster.getColor(x, y);
			if (maybeColor.isPresent() && (maybeColor.get() & 0xffffff) == (bgColor & 0xffffff))
			{
				raster.setColor(x, y, fillColor);
				filledPoints.add(point);

				stack.push(new Point2D(x + 1, y));
				stack.push(new Point2D(x - 1, y));
				stack.push(new Point2D(x, y + 1));
				stack.push(new Point2D(x, y - 1));
			}
		}

		return filledPoints;
	}
}
