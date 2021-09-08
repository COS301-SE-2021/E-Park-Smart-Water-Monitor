package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses.GetElevationDataResponse;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("GeoDataServiceImpl")
public class GeoDataServiceImpl implements GeoDataService
{
    Coordinate firstPoint=new Coordinate(28.1677777778889364 ,-25.7566666668889752);
//    int[][] dataGrid = new int[1261][1175];
    double[][] dataGrid = new double[257][336];
    double geoOffset = 0.000277777778;

    //[long][lat]
    //  |   _
    //  y    x
    //1175, 1261
    int blocksWidth=257;
    int blocksBreadth=336;

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
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/za/ac/up/cs/dynative/EParkSmartWaterMonitor/geodata/rietvlei.xyz")))
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
                System.out.println("Setting: ["+auxLine[0]+"]["+auxLine[1]+"]");
                dataGrid[latNumb][longNum]=Integer.parseInt(auxLine[2]);

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
    public Point convertCoordToGridBlock(Coordinate inCoordinate)
    {
        System.out.println("X CALC: " + inCoordinate.getX()+"-"+firstPoint.getX()+" ="+(inCoordinate.getX()-firstPoint.getX()));
        System.out.println("Y CALC: " + inCoordinate.getY()+"-"+firstPoint.getY()+" ="+(inCoordinate.getY()-firstPoint.getY()) +"  /0.000277777778 ="+((inCoordinate.getY()-firstPoint.getY())/0.000277777778)+"  FLOORED:"+ Math.floor((inCoordinate.getY()-firstPoint.getY())/0.000277777778));
        int x =Math.abs((int) Math.floor(Math.abs(inCoordinate.getX()-firstPoint.getX())/0.000277777778));
        int y =Math.abs((int) Math.floor(Math.abs(inCoordinate.getY()-firstPoint.getY())/0.000277777778));

        System.out.println("GRID LOCATION ["+x+"]["+y+"]");
        System.out.println(dataGrid[2][2]);
        return new Point(x,y);
    }

    public Coordinate convertGridBlockToCoord(int x,int y)
    {
        return new Coordinate(firstPoint.getX()+(x*geoOffset),firstPoint.getY()+(-y*geoOffset));
    }

    public ArrayList<ArrayList<Double>> geoSquareBuilder(Coordinate coordinate)
    {
        ArrayList<ArrayList<Double>> geoSquare = new ArrayList<>();
        double geoOffset = 0.000277777778;

        ArrayList<Double> upperLeftCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX(),coordinate.getY()));
        ArrayList<Double> lowerLeftCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX(),coordinate.getY()+geoOffset));
        ArrayList<Double> lowerRightCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX()+geoOffset,coordinate.getY()+geoOffset));
        ArrayList<Double> upperRightCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX(),coordinate.getY()+geoOffset));

        geoSquare.add(upperLeftCorner);
        geoSquare.add(lowerLeftCorner);
        geoSquare.add(lowerRightCorner);
        geoSquare.add(upperRightCorner);
        geoSquare.add(upperLeftCorner);

        return geoSquare;

    }
    public GetElevationDataResponse getElevationData()
    {
        Double min = 99999.0;
        Double max = -99999.0;
        ArrayList<GeoFeatures> features = new ArrayList<>();

        for (int lng = 0; lng < blocksBreadth; lng++)
        {
            for (int lat = 0; lat < blocksWidth; lat++)
            {
                double elevationValue = dataGrid[lng][lat];
                FeatureProperties auxProperties = new FeatureProperties(dataGrid[lng][lat]);
                GeometryData auxGeometry = new GeometryData(geoSquareBuilder( convertGridBlockToCoord(lat,lng)));
                features.add(new GeoFeatures(auxProperties,auxGeometry));

                if (elevationValue>max)
                    max = elevationValue;

                if (elevationValue<min)
                    min = elevationValue;

            }
        }

        GeoJSON responseGeoJSON = new GeoJSON(features);


        return new GetElevationDataResponse("Data successfully retrieved",true,min,max,responseGeoJSON);
    }
}
