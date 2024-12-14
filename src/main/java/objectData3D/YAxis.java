package objectData3D;

import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.stream.IntStream;

public class YAxis extends Object3D
{
	public YAxis(int color)
	{
		super(
				IntStream
						.rangeClosed(0, 100)
						.mapToObj(i -> new Point3D(0, i, 0))
						.toList(),
				IntStream.range(0, 100).flatMap(i -> IntStream.of(i, i + 1)).boxed().toList(),
				new Mat4Identity(),
				color
		);
	}
}
