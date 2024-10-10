package rasterData;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterBI implements Raster
{
    private final BufferedImage image;

    public RasterBI(int width, int height)
    {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int width()
    {
        return image.getWidth();
    }

    @Override
    public int height()
    {
        return image.getHeight();
    }

    @Override
    public void setColor(int c, int r, int color)
    {
        if(c < image.getWidth() && c >= 0 && r < image.getHeight() && r >= 0)
            image.setRGB(c, r, color);
    }

    @Override
    public Optional<Integer> getColor(int c, int r)
    {
        if(c < image.getWidth() && c >= 0 && r < image.getHeight() && r >= 0)
            return Optional.of(image.getRGB(c, r));
        else
            return Optional.empty();
    }
}
