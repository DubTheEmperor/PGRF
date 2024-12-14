package objectData3D;

import transfroms.Cubic;
import transfroms.Mat4;
import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.stream.IntStream;

public class Curve extends Object3D
{
    public Curve(int color, Mat4 type, Point3D p1, Point3D p2, Point3D p3, Point3D p4)
    {
        super(
                IntStream
                        .rangeClosed(0, 100)
                        .mapToObj(i -> new Cubic(type, p1, p2, p3, p4).compute(i / 100.0)).toList(),
                IntStream.range(0, 100).flatMap(i -> IntStream.of(i, i + 1)).boxed().toList(),
                new Mat4Identity(),
                color
        );
    }
}
