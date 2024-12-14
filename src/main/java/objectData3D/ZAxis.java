package objectData3D;

import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.stream.IntStream;

public class ZAxis extends Object3D
{
	public ZAxis(int color)
	{
		super(
				IntStream
						.rangeClosed(0, 100)
						.mapToObj(i -> new Point3D(0, 0, i))
						.toList(),
				IntStream.range(0, 100).flatMap(i -> IntStream.of(i, i + 1)).boxed().toList(),
				new Mat4Identity(),
				color
		);
	}
}
