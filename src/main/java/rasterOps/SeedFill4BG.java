package rasterOps;

import rasterData.Raster;

import java.util.Optional;

public class SeedFill4BG
{
    public void fill(Raster raster, int c, int r, int bgColor, int fillColor)
    {
        Optional<Integer> maybeColor = raster.getColor(c, r);

        if(maybeColor.isEmpty())
            return;

        int color = maybeColor.get();
        if((color & 0xffffff) == (bgColor & 0xffffff))
        {
            raster.setColor(c, r, fillColor);
            fill(raster, c + 1, r, bgColor, fillColor);
            fill(raster, c - 1, r, bgColor, fillColor);
            fill(raster, c, r + 1, bgColor, fillColor);
            fill(raster, c, r - 1, bgColor, fillColor);
        }
    }
}
