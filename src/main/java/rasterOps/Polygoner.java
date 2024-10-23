package rasterOps;

import objectData.Point2D;
import objectData.Polygon;
import rasterData.Raster;

public class Polygoner
{
	public void draw(Raster raster, Polygon polygon, Liner liner)
	{
		for (int i = 0; i < polygon.size(); i++) {
			Point2D p1 = polygon.getPoint(i);
			Point2D p2 = polygon.getPoint((i + 1) % polygon.size());

			liner.draw(raster, p1, p2, polygon.getColor(), polygon.getThickness());
		}
	}
}
