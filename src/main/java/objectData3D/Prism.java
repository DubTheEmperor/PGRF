package objectData3D;

import transfroms.Mat4Identity;
import transfroms.Point3D;

import java.util.List;

public class Prism extends Polyhedron
{
	public Prism(int color)
	{
		super(
				List.of(
						new Point3D(3, -2, -2),  // 0
						new Point3D(3, 0, -2),   // 1
						new Point3D(5, 0, -2),    // 2
						new Point3D(5, -2, -2),   // 3

						new Point3D(3, -1, 0),   // 4
						new Point3D(5, -1, 0)      // 5
				),
				List.of(
						0, 1,
						1, 2,
						2, 3,
						0, 3,

						1, 4,
						0, 4,
						2, 5,
						3, 5,
						4, 5
				),
				new Mat4Identity(),
				color
		);
	}
}
