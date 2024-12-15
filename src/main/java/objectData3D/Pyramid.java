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
                        new Point3D(4, 4, 4),  // 0
                        new Point3D(4, 6, 4),   // 1
                        new Point3D(6, 4, 4),   // 2
                        new Point3D(6, 6, 4),    // 3

                        new Point3D(5, 5, 6)      // 4
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
