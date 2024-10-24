package rasterOps;

import objectData.Line;
import objectData.Point2D;
import rasterData.Raster;

public class TrivialLiner implements Liner
{
	@Override
	public void draw(Raster raster, Point2D point1, Point2D point2, int color)
	{
		draw(raster, point1, point2, color, 1);
	}

	@Override
	public void draw(Raster raster, Point2D point1, Point2D point2, int color, int thickness)
	{
		int c1 = point1.getX();
		int r1 = point1.getY();
		int c2 = point2.getX();
		int r2 = point2.getY();

		int dc = c2 - c1;
		int dr = r2 - r1;

		// Handle the case where the line is vertical
		if (dc == 0)
		{
			for (int r = Math.min(r1, r2); r <= Math.max(r1, r2); r++)
			{
				for (int i = -thickness / 2; i <= thickness / 2; i++)
				{
					raster.setColor(c1 + i, r, color);
				}
			}
			return;
		}

		// Handle horizontal or diagonal lines
		double k = (double) dr / dc;
		double q = r1 - k * c1;

		// If the line is more horizontal (step along x-axis)
		if (Math.abs(dc) > Math.abs(dr))
		{
			for (int c = Math.min(c1, c2); c <= Math.max(c1, c2); c++)
			{
				int r = (int) (k * c + q);
				for (int i = -thickness / 2; i <= thickness / 2; i++)
				{
					raster.setColor(c, r + i, color);
				}
			}
			return;
		}

		// Handle more vertical lines (step along y-axis)
		for (int r = Math.min(r1, r2); r <= Math.max(r1, r2); r++)
		{
			int c = (int) ((r - q) / k);
			for (int i = -thickness / 2; i <= thickness / 2; i++)
			{
				raster.setColor(c + i, r, color);
			}
		}
	}

	public void alignLine(Line line)
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
		}
		else if (Math.abs(dc) <= threshold)
		{
			//align to vertical
			c2 = c1;
		}
		else
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

	@Override
	public void draw(Raster raster, Line line)
	{
		Point2D point1 = line.getPoint1();
		Point2D point2 = line.getPoint2();

		draw(raster, point1, point2, line.getColor(), line.getThickness());
	}
}
