package rasterOps;

import objectData.Line;
import objectData.Point2D;
import rasterData.Raster;

/**
 * Represents an algorithm for drawing line segments
 */
public interface Liner
{
    /**
     * Draws a line onto the given raster using the provided color
     * @param raster raster to be drawn onto
     * @param point1 address of first point
     * @param point2 address of second point
     * @param color color of the resulting line segment
     */
    void draw(Raster raster, Point2D point1, Point2D point2, int color);

    void draw(Raster raster, Point2D point1, Point2D point2, int color, int thickness);

    void draw(Raster raster, Line line);
}
