package objectData3D;

import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.List;

public class Pyramid extends Object3D
{
    public Pyramid(int color)
    {
        super(
                List.of(
                        new Point3D(-1, -1, -1),  // 0
                        new Point3D(-1, 1, -1),   // 1
                        new Point3D(1, -1, -1),   // 2
                        new Point3D(1, 1, -1),    // 3

                        new Point3D(0, 0, 1)      // 4
                ),
                List.of(
                        0, 1,
                        1, 3,
                        2, 3,
                        0, 2,

                        0, 4,
                        2, 4,
                        3, 4,
                        1, 4
                ),
                new Mat4Identity(),
                color
        );
    }
}
