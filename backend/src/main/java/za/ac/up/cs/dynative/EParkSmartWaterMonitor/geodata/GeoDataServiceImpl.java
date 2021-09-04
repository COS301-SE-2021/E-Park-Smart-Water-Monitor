package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GeoDataServiceImpl
{
    public void lineApproxcimation(Point from, Point to)
    {

        Point[][] grid =new Point[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                grid[i][j] = new Point(i, j);
        int startX=2;
        int startY=2;
        int endX=2;
        int endY=5;

        int diffX = Math.abs(endX - startX);
        int diffY = Math.abs(endY - startY);

        List<Point> approximatedPath = new ArrayList<Point>();

        int slopeX;
        int slopeY;
        if(startX < endX)
            slopeX=1;
        else slopeX =-1;

        if(startY < endY)
            slopeY=1;
        else slopeY =-1;

        int decisionVar;
        int err = diffX-diffY;

        while (!(startX == endX && startY == endY))
        {
            approximatedPath.add(grid[startX][startY]);

            decisionVar = 2 * err;
            if (decisionVar > -diffY)
            {
                startX = startX + slopeX;
                err = err - diffY;
            }

            if (decisionVar < diffX)
            {
                startY = startY + slopeY;
                err = err + diffX;
            }
        }
        approximatedPath.add(grid[startX][startY]);

        System.out.println(approximatedPath.toString());

    }
}
