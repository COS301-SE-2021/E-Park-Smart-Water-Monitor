package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.Coordinate;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.GeoJSON;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses.GetElevationDataResponse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface GeoDataService
{
    public void loadElevation(Coordinate startingLocation, double SquaredKm);
    public List<Point> lineApproximation(Point from, Point to);
    public Point convertCoordToGridBlock(Coordinate inCoordinate);
    public Coordinate convertGridBlockToCoord(int x,int y);
    public ArrayList<ArrayList<Double>> geoSquareBuilder(Coordinate coordinate);
    public GetElevationDataResponse getElevationData();
//    public GeoJSON getElevationData();


}
