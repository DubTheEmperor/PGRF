package rasterOps;

import rasterData.Raster;

public class TrivialLiner implements Liner
{

    @Override
    public void draw(Raster raster, int c1, int r1, int c2, int r2, int color)
    {
        int dc = c2 - c1;
        int dr = r2 - r1;

        if(Math.abs(dc) > Math.abs(dr))
        {
            if(c1 > c2)
            {
                int tmp = c1;
                c1 = c2;
                c2 = tmp;
            }
            double k = (double) (r2 - r1) / (c2 - c1);
            double q = r1 - k * c1;
            for (int c = c1; c < c2; c++)
            {
                int r = (int) (k * c + q);
                raster.setColor(c, r , color);
            }
        }
        else
        {
            if(r1 > r2)
            {
                int tmp = r1;
                r1 = r2;
                r2 = tmp;
            }
            double k = (double) (r2 - r1) / (c2 - c1);
            double q = r1 - k * c1;
            for (int r = r1; r < r2; r++)
            {
                int c = (int) (k * r + q);
                raster.setColor(c, r , color);
            }
        }
    }
}
