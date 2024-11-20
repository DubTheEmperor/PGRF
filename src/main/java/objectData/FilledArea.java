package objectData;

import rasterData.Raster;
import rasterOps.Liner;
import rasterOps.SeedFill4BG;

import java.util.List;
import java.util.Optional;

public class FilledArea implements Shape {
	private List<Point2D> filledPoints;
	private int fillColor;

	public FilledArea(List<Point2D> filledPoints, int fillColor) {
		this.filledPoints = filledPoints;
		this.fillColor = fillColor;
	}

	@Override
	public void draw(Raster img, Liner liner) {
		for (Point2D point : filledPoints) {
			img.setColor(point.getX(), point.getY(), fillColor);
		}
	}
}