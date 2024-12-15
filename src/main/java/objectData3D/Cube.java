package objectData3D;

import transfroms.*;

import java.util.List;

public class Cube extends Object3D
{
    public Cube(int color)
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
                        2, 6,

                        6, 4,
                        0, 4,
                        4, 5,
                        1, 5,
                        7, 5
                ),
                new Mat4Identity(),
                color
        );
    }
}
