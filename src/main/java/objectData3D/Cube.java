package objectData3D;

import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.List;

public class Cube extends Object3D
{
    public Cube()
    {
        super(
                List.of(
                        new Point3D(-1, -1, -1),  // 0
                        new Point3D(-1, 1, -1),   // 1
                        new Point3D(1, -1, -1),   // 2
                        new Point3D(1, 1, -1),    // 3

                        new Point3D(-1, -1, 1),   // 4
                        new Point3D(-1, 1, 1),    // 5
                        new Point3D(1, -1, 1),    // 6
                        new Point3D(1, 1, 1)      // 7
                ),
                List.of(
                        0, 1,
                        0, 2,
                        1, 3,
                        2, 3,

                        3, 7,
                        6, 7,
                        0, 3,

                        4, 6,
                        0, 4,
                        4, 5,
                        1, 5,
                        5, 7
                ),
                new Mat4Identity()
        );
    }
}
