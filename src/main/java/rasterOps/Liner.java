package rasterOps;

import rasterData.Raster;

/**
 * Represents an algorithm for drawing line segments
 */
public interface Liner
{
    /**
     * Draws a line onto the given raster using the provided color
     * @param raster raster to be drawn onto
     * @param c1 column address of the starting point
     * @param r1 row address of the starting point
     * @param c2 column address of the end point
     * @param r2 row address of the end point
     * @param color color of the resulting line segment
     */
    void draw(Raster raster, int c1, int r1, int c2, int r2, int color);
}
