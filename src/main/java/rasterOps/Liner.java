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

    /**
     * Draws a line onto the given raster using the provided color and thickness
     * @param raster raster to be drawn onto
     * @param point1 address of first point
     * @param point2 address of second point
     * @param color color of the resulting line segment
     * @param thickness thickness of the resulting line segment
     */
    void draw(Raster raster, Point2D point1, Point2D point2, int color, int thickness);

    /**
     * Draws already existing line onto the given raster
     * @param raster raster to be drawn onto
     * @param line desired line to be drawn
     */
    void draw(Raster raster, Line line);
}
