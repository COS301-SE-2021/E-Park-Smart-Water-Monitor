package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.Coordinate;

import java.awt.*;

public interface GeoDataService
{
    public void loadElevation(Coordinate startingLocation, double SquaredKm);
    public void lineApproximation(Point from, Point to);
}
