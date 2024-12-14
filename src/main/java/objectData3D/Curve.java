package objectData3D;

import transfroms.Cubic;
import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.stream.IntStream;

public class Curve extends Object3D
{
    public Curve(int color)
    {
        super(
                IntStream
                        .rangeClosed(0, 100)
                        .mapToObj(i -> new Cubic(
                                Cubic.BEZIER,
                                new Point3D(1, -1, -1),
                                new Point3D(-1, 1, -1),
                                new Point3D(-1, -1, 1),
                                new Point3D(-1, 1, 1)
                        ).compute(i / 100.0)).toList(),
                IntStream.range(0, 100).flatMap(i -> IntStream.of(i, i + 1)).boxed().toList(),
                new Mat4Identity(),
                color
        );
    }
}
