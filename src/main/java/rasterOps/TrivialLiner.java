package rasterOps;

import rasterData.Raster;

public class TrivialLiner implements Liner
{

    @Override
    public void draw(Raster raster, int c1, int r1, int c2, int r2, int color)
    {
        int dc = c2 - c1;
        int dr = r2 - r1;

        // Handle the case where the line is vertical
        if (dc == 0)
        {
            for (int r = Math.min(r1, r2); r <= Math.max(r1, r2); r++)
            {
                raster.setColor(c1, r, color);
            }
            return;
        }

        // Handle horizontal or diagonal lines
        double k = (double) dr / dc;
        double q = r1 - k * c1;

        // If the line is more horizontal (step along x-axis)
        if (Math.abs(dc) > Math.abs(dr))
        {
            if (c1 > c2)
            {
                int temp = c1;
                c1 = c2;
                c2 = temp; //switch c1, c2
                temp = r1;
                r1 = r2;
                r2 = temp; //switch r1, r2
            }
            for (int c = c1; c <= c2; c++)
            {
                int r = (int) (k * c + q);
                raster.setColor(c, r, color);
            }
            return;
        }

        // Handle more vertical lines (step along y-axis)
        if (r1 > r2)
        {
            int temp = c1;
            c1 = c2;
            c2 = temp; //switch c1, c2
            temp = r1;
            r1 = r2;
            r2 = temp; //switch r1, r2
        }
        for (int r = r1; r <= r2; r++)
        {
            int c = (int) ((r - q) / k);
            raster.setColor(c, r, color);
        }
    }
}
