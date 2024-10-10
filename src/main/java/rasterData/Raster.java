package rasterData;

import java.util.Optional;

public interface Raster
{
    /**
     * Retruns the number of columns in this Raster
     * @return number of columns
     */
    int width();

    /**
     * Returns the number of rows in this Raster
     * @return number of rows
     */
    int height();

    /**
     * Sets the provided color on the pixel specified by the given pixel address
     * If the given address is invalid, doesn't change any color values
     * @param c column pixel address
     * @param r row pixel address
     * @param color new color
     */
    void setColor(int c, int r, int color);

    /**
     * Returns the color stored at the specified address
     * @param c column pixel address
     * @param r row pixel address
     * @return Optional of color if the given address is valid; otherwise empty optional
     */
    Optional<Integer> getColor(int c, int r);
}
