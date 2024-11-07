package objectOps;

import objectData.Line;
import objectData.Polygon;

import java.util.ArrayList;
import java.util.List;

public class SutherlandHodgman
{
    public Polygon cut(Polygon cuttingPolygon, Polygon cutPolygon)
    {
        List<Line> cuttingLines = cuttingPolygon.toLines();
        List<Line> cutLines = cutPolygon.toLines();

        Polygon currentPolygon = new Polygon();

        for(Line cuttingLine: cuttingLines)
        {
            currentPolygon = new Polygon();
            for(Line cutLine: cutLines)
            {
                //startInside = cutLine.isInside(cutLine.start)
                //endInside = cutLine.isInside(cutLine.end)
                //decide on the case
                //  currentPolygon.addPoint()
            }
                //cutLines <- from currentPolygon
        }
        return currentPolygon;
    }
}
