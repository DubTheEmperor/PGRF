package fillPatterns;

public class CheckerboardPattern implements PatternFill {
	private final int fillColor;
	private final int backgroundColor;
	private final int cellSize;

	public CheckerboardPattern(int fillColor, int cellSize) {
		this.fillColor = fillColor;
		this.backgroundColor = 0xFFFFFF; // Default background color (white)
		this.cellSize = cellSize;
	}
	@Override
	public int getColorAt(int x, int y)
	{
		int cellX = x / cellSize;
		int cellY = y / cellSize;
		return (cellX + cellY) % 2 == 0 ? fillColor : backgroundColor;
	}

	@Override
	public int getColor()
	{
		return fillColor;
	}
}