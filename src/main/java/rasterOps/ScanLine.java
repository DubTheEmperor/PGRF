package rasterOps;

import objectData.Line;
import objectData.Point2D;
import objectData.Polygon;
import rasterData.Raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ScanLine
{
    public void draw(Raster raster, Polygon polygon, int fillColor)
    {
        List<Integer> yList = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++)
        {
            yList.add(polygon.getPoint(i).getY());
        }

        int yMin = Collections.min(yList);
        int yMax = Collections.max(yList);

        for (int y = yMin; y <= yMax; y++) {
            // Find intersections of the polygon edges with the current scanline
            List<Integer> intersections = findIntersections(polygon, y);

            // Sort the intersections to process pairs
            Collections.sort(intersections);

            // Fill pixels between pairs of intersections
            for (int i = 0; i < intersections.size(); i += 2) {
                int startX = intersections.get(i);
                int endX = intersections.get(i + 1);

                for (int x = startX; x < endX; x++) {
                    raster.setColor(x, y, fillColor);
                }
            }
        }
    }

    private List<Integer> findIntersections(Polygon polygon, int scanlineY) {
        List<Integer> intersections = new ArrayList<>();

        // Get all lines that form the edges of the polygon
        List<Line> lines = polygon.toLines();

        for (Line line : lines) {
            // Extract points from the line
            Point2D start = line.getPoint1();
            Point2D end = line.getPoint2();

            int x1 = start.getX();
            int y1 = start.getY();
            int x2 = end.getX();
            int y2 = end.getY();

            // Check if the line intersects the current scanline
            if ((y1 <= scanlineY && y2 > scanlineY) || (y2 <= scanlineY && y1 > scanlineY)) {
                // Calculate the X coordinate of the intersection point
                int intersectX = x1 + (scanlineY - y1) * (x2 - x1) / (y2 - y1);
                intersections.add(intersectX);
            }
        }

        return intersections;
    }
}
