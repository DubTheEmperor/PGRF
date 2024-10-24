package objectData;

import rasterData.Raster;
import rasterData.RasterBI;
import rasterOps.Liner;

public interface Shape
{
	/**
	 * Draws a shape onto given
	 * @param img raster to be drawn onto
	 * @param liner desired liner which should be used
	 */
	void draw(Raster img, Liner liner);
}