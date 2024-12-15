package objectData3D;

import transfroms.*;

import java.util.List;

public class Object3D
{
    private final List<Point3D> vertexBuffer;
    private final List<Integer> indexBuffer;
    private final Mat4 modelMat;
    private final int color;

    public Object3D(List<Point3D> vertexBuffer, List<Integer> indexBuffer, Mat4 modelMat, int color)
    {
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.modelMat = modelMat;
        this.color = color;
    }

    public List<Point3D> getVertexBuffer()
    {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer()
    {
        return indexBuffer;
    }

    public Mat4 getModelMat()
    {
        return modelMat;
    }

    public int getColor()
    {
        return color;
    }

    public void rotateX(double angle)
    {
        Mat4RotX rotation = new Mat4RotX(angle);
        setModelMat(getModelMat().mul(rotation));
    }

    public void rotateY(double angle)
    {
        Mat4RotY rotation = new Mat4RotY(angle);
        setModelMat(getModelMat().mul(rotation));
    }

    public void rotateZ(double angle)
    {
        Mat4RotZ rotation = new Mat4RotZ(angle);
        setModelMat(getModelMat().mul(rotation));
    }

    public void scale(double factor)
    {
        Mat4Scale scaling = new Mat4Scale(factor, factor, factor);
        setModelMat(getModelMat().mul(scaling));
    }

    public void moveTo(double x, double y, double z)
    {
        Point3D center = getCenter();
        Vec3D translationVector = new Vec3D(x - center.getX(), y - center.getY(), z - center.getZ());
        Mat4Transl translation = new Mat4Transl(translationVector);
        setModelMat(getModelMat().mul(translation));
    }

    public Point3D getCenter()
    {
        if (getVertexBuffer() == null || getVertexBuffer().isEmpty())
        {
            throw new IllegalStateException("Polyhedron has no vertices.");
        }

        Vec3D center = new Vec3D(0, 0, 0);

        for (Point3D vertex : getVertexBuffer())
        {
            // Transform the vertex using the model matrix
            Point3D transformedVertex = vertex.mul(getModelMat());
            center = center.add(new Vec3D(transformedVertex.getX(), transformedVertex.getY(), transformedVertex.getZ()));
        }

        int vertexCount = getVertexBuffer().size();
        return new Point3D(center.getX() / vertexCount, center.getY() / vertexCount, center.getZ() / vertexCount);
    }


    private void setModelMat(Mat4 newModelMat)
    {
        try
        {
            var modelMatField = Object3D.class.getDeclaredField("modelMat");
            modelMatField.setAccessible(true);
            modelMatField.set(this, newModelMat);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException("Failed to update model matrix", e);
        }
    }
}
