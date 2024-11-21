package objectData;

import transfroms.Mat4;
import transfroms.Point3D;

import java.util.List;

public class Object3D
{
    private final List<Point3D> vertexBuffer;
    private final List<Integer> indeBuffer;
    private final Mat4 modelMat;

    public Object3D(List<Point3D> vertexBuffer, List<Integer> indeBuffer, Mat4 modelMat)
    {
        this.vertexBuffer = vertexBuffer;
        this.indeBuffer = indeBuffer;
        this.modelMat = modelMat;
    }

    public List<Point3D> getVertexBuffer()
    {
        return vertexBuffer;
    }

    public List<Integer> getIndeBuffer()
    {
        return indeBuffer;
    }

    public Mat4 getModelMat()
    {
        return modelMat;
    }
}
