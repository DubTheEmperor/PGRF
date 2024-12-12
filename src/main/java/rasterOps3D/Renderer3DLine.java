package rasterOps3D;

import objectData.Line;
import objectData.Point2D;
import objectData3D.Object3D;
import objectData3D.Scene;
import rasterData.Raster;
import rasterOps.Liner;
import transfroms.Mat4;
import transfroms.Point3D;
import transfroms.Vec2D;

import java.util.List;

public class Renderer3DLine
{
    public void renderScene(Raster raster, Scene scene, Mat4 matView, Mat4 matProjection, Liner liner, int color)
    {
        for (Object3D object : scene.getObjects())
        {
            final Mat4 objectTransformation = object.getModelMat().mul(matView).mul(matProjection);
            renderObject(raster, object, objectTransformation, liner, color);
        }
    }

    private void renderObject(Raster raster, Object3D object, Mat4 transformation, Liner liner, int color)
    {
        final List<Point3D> transformedVertices = object
                .getVertexBuffer()
                .stream()
                .map(p -> p.mul(transformation))
                .toList();

        for (int i = 0; i < object.getIndexBuffer().size(); i += 2)
        {
            final Point3D first = transformedVertices.get(object.getIndexBuffer().get(i));
            final Point3D second = transformedVertices.get(object.getIndexBuffer().get(i + 1));

            if (first.getX() < -first.getW() || first.getX() > first.getW() ||
                    first.getY() < -first.getW() || first.getY() > first.getW() ||
                    first.getZ() < 0 || first.getZ() > first.getW())
                continue;

            first.dehomog().ifPresent(p1 -> {
                second.dehomog().ifPresent(p2 -> {
                    Vec2D first2D = p1.ignoreZ();
                    Vec2D second2D = p2.ignoreZ();

                    Vec2D firstInViewSpace = toViewSpace(raster, first2D);
                    Vec2D secondInViewSpace = toViewSpace(raster, second2D);

                    liner.draw(raster, new Point2D((int) firstInViewSpace.getX(), (int) firstInViewSpace.getY()), new Point2D((int) secondInViewSpace.getX(), (int) secondInViewSpace.getY()), color);
                });
            });
        }
    }

    private Vec2D toViewSpace(Raster raster, Vec2D point)
    {
        double scaleX = raster.getWidth();
        double scaleY = raster.getHeight();

        return point.withX((point.getX() + 1) / 2 * (scaleX - 1))
                    .withY((-point.getY() + 1) / 2 * (scaleY - 1));
    }
}

