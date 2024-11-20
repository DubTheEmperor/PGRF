package rasterOps;

import objectData.Point2D;
import rasterData.Raster;

import java.util.*;

public class SeedFill4BG
{
    public static List<Point2D> fill(Raster raster, int c, int r, int bgColor, int fillColor)
    {
        List<Point2D> filledPoints = new ArrayList<>();
        fillRecursive(raster, c, r, bgColor, fillColor, filledPoints);
        return filledPoints;
    }

    private static void fillRecursive(Raster raster, int c, int r, int bgColor, int fillColor, List<Point2D> filledPoints)
    {
        Optional<Integer> maybeColor = raster.getColor(c, r);

        if (maybeColor.isEmpty())
            return;

        int color = maybeColor.get();
        if ((color & 0xffffff) == (bgColor & 0xffffff))
        {
            raster.setColor(c, r, fillColor);
            filledPoints.add(new Point2D(c, r));
            fillRecursive(raster, c + 1, r, bgColor, fillColor, filledPoints);
            fillRecursive(raster, c - 1, r, bgColor, fillColor, filledPoints);
            fillRecursive(raster, c, r + 1, bgColor, fillColor, filledPoints);
            fillRecursive(raster, c, r - 1, bgColor, fillColor, filledPoints);
        }
    }
}
