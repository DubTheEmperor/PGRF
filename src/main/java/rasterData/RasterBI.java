package rasterData;

import java.awt.*;
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
    public int getWidth()
    {
        return image.getWidth();
    }

    @Override
    public int getHeight()
    {
        return image.getHeight();
    }

    @Override
    public boolean setColor(int c, int r, int color)
    {
        if(c < image.getWidth() && c >= 0 && r < image.getHeight() && r >= 0)
        {
            image.setRGB(c, r, color);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Integer> getColor(int c, int r)
    {
        if(c < image.getWidth() && c >= 0 && r < image.getHeight() && r >= 0)
            return Optional.of(image.getRGB(c, r));
        else
            return Optional.empty();
    }

    public void clear(int color)
    {
        Graphics gr = image.getGraphics();
        gr.setColor(new Color(color));
        gr.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void clear()
    {
        Graphics gr = image.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    public void present(Graphics graphics)
    {
        if(graphics != null)
            graphics.drawImage(image, 0, 0, null);
    }
}
