package objectData;

import rasterData.Raster;
import rasterOps.Liner;

public class Point2D
{
	private int c, y;

	public Point2D(int x, int y)
	{
		this.c = x;
		this.y = y;
	}

	public int getX()
	{
		return c;
	}

	public int getY()
	{
		return y;
	}

	public Point2D translate(int dx, int dy)
	{
		return new Point2D(c + dx, y + dy);
	}

	public Point2D scale(int k)
	{
		return new Point2D(c * k, y * k);
	}

	public Point2D rotate(int alpha)
	{
		return new Point2D((int) (Math.cos(alpha) * c - Math.sin(alpha) * y), (int) (Math.sin(alpha) * c + Math.cos(alpha) * y));
	}
}
