package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate
{
    private double x;
    private double y;

    public Coordinate(@JsonProperty("x") double x, @JsonProperty("y")double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }
}
