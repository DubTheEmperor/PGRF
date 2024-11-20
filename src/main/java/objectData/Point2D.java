package objectData;

public class Point2D
{
	private int x, y;

	public Point2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Point2D translate(int dx, int dy)
	{
		return new Point2D(x + dx, y + dy);
	}

	public Point2D scale(int k)
	{
		return new Point2D(x * k, y * k);
	}

	public Point2D rotate(int alpha)
	{
		return new Point2D((int) (Math.cos(alpha) * x - Math.sin(alpha) * y), (int) (Math.sin(alpha) * x + Math.cos(alpha) * y));
	}
}
