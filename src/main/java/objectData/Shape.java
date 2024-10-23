package objectData;

import rasterData.RasterBI;
import rasterOps.Liner;

public interface Shape
{
	public  void draw(RasterBI img, Liner liner);
}