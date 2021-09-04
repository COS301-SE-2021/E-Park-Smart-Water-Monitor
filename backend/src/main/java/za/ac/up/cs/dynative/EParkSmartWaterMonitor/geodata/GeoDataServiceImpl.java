package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.Coordinate;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("GeoDataServiceImpl")
public class GeoDataServiceImpl implements GeoDataService
{
    Coordinate firstPoint;
    int[][] dataGrid = new int[1261][1175];
    //[long][lat]
    //  |   _
    //  y    x
    //1175, 1261
    int blocksWidth=1175;
    int blocksBreadth=1261;

    public GeoDataServiceImpl()
    {
        loadElevation(new Coordinate(28.1677777778889364 ,-25.7566666668889752),1.5);
    }

    public void lineApproximation(Point from, Point to)
    {

        Point[][] grid =new Point[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                grid[i][j] = new Point(i, j);
        int startX=from.x;
        int startY=from.y;
        int endX=to.x;
        int endY=to.y;

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

    public void loadElevation(Coordinate startingLocation, double SquaredKm)
    {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/za/ac/up/cs/dynative/EParkSmartWaterMonitor/geodata/PtaJhb.xyz")))
        {
            String line;
            int count =0;
            int longNum =0;
            int latNumb =0;
            while ((line = br.readLine()) != null)
            {
                count++;
                System.out.print("Line#"+count +" ");
                String[] auxLine = line.split(" ");

                if (count==1)
                {
                    firstPoint = new Coordinate(Double.parseDouble(auxLine[0]),Double.parseDouble(auxLine[1]));
                }
                System.out.println(line);
                System.out.println("Setting: ["+longNum+"]["+latNumb+"]");
                dataGrid[longNum][latNumb]=Integer.parseInt(auxLine[2]);

                latNumb++;

                if (latNumb==blocksWidth)
                {
                    System.out.println("RESET");
                    latNumb=0;
                    longNum++;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
