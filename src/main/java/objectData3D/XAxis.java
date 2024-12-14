package objectData3D;

import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.stream.IntStream;

public class XAxis extends Object3D
{
	public XAxis(int color)
	{
		super(
				IntStream
						.rangeClosed(0, 100)
						.mapToObj(i -> new Point3D(i, 0, 0))
						.toList(),
				IntStream.range(0, 100).flatMap(i -> IntStream.of(i, i + 1)).boxed().toList(),
				new Mat4Identity(),
				color
		);
	}
}
