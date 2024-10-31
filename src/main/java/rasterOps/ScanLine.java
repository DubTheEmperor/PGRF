package rasterOps;

import objectData.Line;
import objectData.Point2D;
import objectData.Polygon;
import rasterData.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ScanLine
{
    void draw(Raster raster, Polygon polygon, Liner liner, Polygoner polygoner, int fillColor, int borderColor)
    {
        List<Integer> yList = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++)
        {
            yList.add(polygon.getPoint(i).getY());
        }

        int yMin = Collections.min(yList);
        int yMax = Collections.max(yList);

        for (int y = yMin; y <= yMax; y++) {
            List<Integer> xIntersections = new ArrayList<>();

            // Find intersections of the scanline with the edges of the polygon
            for (int i = 0; i < polygon.size(); i++) {
                Point2D p1 = polygon.getPoint(i);
                Point2D p2 = polygon.getPoint((i + 1) % polygon.size());
                Line edge = new Line(p1, p2, borderColor);

                Optional<Float> xOpt = edge.yIntercept(y);
                xOpt.ifPresent(x -> xIntersections.add(Math.round(x)));
            }

            // Sort the intersection points
            Collections.sort(xIntersections);

            // Fill between pairs of intersections
            for (int i = 0; i < xIntersections.size(); i += 2) {
                if (i + 1 < xIntersections.size()) {
                    int xStart = xIntersections.get(i);
                    int xEnd = xIntersections.get(i + 1);

                    // Fill pixels between xStart and xEnd with the fill color
                    for (int x = xStart; x <= xEnd; x++) {
                        raster.setColor(x, y, fillColor);
                    }
                }
            }
        }
    }
}
