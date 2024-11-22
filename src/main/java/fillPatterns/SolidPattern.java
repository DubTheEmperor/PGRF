package fillPatterns;

public class SolidPattern implements PatternFill
{
	private final int fillColor;

	public SolidPattern(int fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public int getColorAt(int x, int y) {
		return fillColor;
	}

	@Override
	public int getColor()
	{
		return fillColor;
	}
}
