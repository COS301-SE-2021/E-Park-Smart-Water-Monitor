package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.models.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses.GetElevationDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata.responses.GetLossDataResponse;

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
    Double min = 99999.0;
    Double max = -99999.0;

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
            approximatedPath.add(new Point(startX,startY));

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
        approximatedPath.add(new Point(startX,startY));

        System.out.println(approximatedPath.toString());

    }

    public void loadElevation(Coordinate startingLocation, double SquaredKm)
    {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/za/ac/up/cs/dynative/EParkSmartWaterMonitor/geodata/rietvlei.xyz")))
        {
            min = 99999.0;
            max = -99999.0;

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
                int eVal=Integer.parseInt(auxLine[2]);
                dataGrid[latNumb][longNum]=eVal;

                if (eVal>max)
                    max = (double) eVal;

                if (eVal<min)
                    min = (double) eVal;

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
        ArrayList<Double> lowerLeftCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX(),coordinate.getY()-geoOffset));
        ArrayList<Double> lowerRightCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX()+geoOffset,coordinate.getY()-geoOffset));
        ArrayList<Double> upperRightCorner = new ArrayList<Double>(Arrays.asList(coordinate.getX()+geoOffset,coordinate.getY()));

        geoSquare.add(upperLeftCorner);
        geoSquare.add(lowerLeftCorner);
        geoSquare.add(lowerRightCorner);
        geoSquare.add(upperRightCorner);
        geoSquare.add(upperLeftCorner);

        return geoSquare;

    }

    public String getAreaColor(double height){
        double range = max-min;
        double segmentSize = range/13;
        if (height-min<segmentSize)
        {
            return "#ff0000";
        }
        else if (height-min<segmentSize*2)
        {
            return "#ff3200";
        }
        else if (height-min<segmentSize*3)
        {
            return "#ff6400";
        }
        else if (height-min<segmentSize*4)
        {
            return "#ff9600";
        }
        else if (height-min<segmentSize*5)
        {
            return "#ffc800";
        }
        else if (height-min<segmentSize*6)
        {
            return "#fffa00";
        }
        else if (height-min<segmentSize*7)
        {
            return "#d2ff00";
        }
        else if (height-min<segmentSize*8)
        {
            return "#9fff00";
        }
        else if (height-min<segmentSize*9)
        {
            return "#6dff00";
        }
        else if (height-min<segmentSize*10)
        {
            return "#3bff00";
        }
        else if (height-min<segmentSize*11)
        {
            return "#09ff00";
        }
        else if (height-min<segmentSize*12)
        {
            return "#00ff29";
        }
        else return "#00ff5b";

    };


    public String getLossColor(double loss){
        double range = max-min;
        double segmentSize = range/13;
        if (loss-min<segmentSize)
        {
            return "#00ff5b";//
        }
        else if (loss-min<segmentSize*2)
        {
            return "#00ff29";//
        }
        else if (loss-min<segmentSize*3)
        {
            return "#09ff00";//
        }
        else if (loss-min<segmentSize*4)
        {
            return "#3bff00";//
        }
        else if (loss-min<segmentSize*5)
        {
            return "#6dff00";//
        }
        else if (loss-min<segmentSize*6)
        {
            return "#9fff00";//
        }
        else if (loss-min<segmentSize*7)
        {
            return "#d2ff00";
        }
        else if (loss-min<segmentSize*8)
        {
            return "#fffa00";
        }
        else if (loss-min<segmentSize*9)
        {
            return "#ffc800";//
        }
        else if (loss-min<segmentSize*10)
        {
            return "#ff9600";//
        }
        else if (loss-min<segmentSize*11)
        {
            return "#ff6400";//
        }
        else if (loss-min<segmentSize*12)
        {
            return "#ff3200";//
        }
        else return "#ff0000";//

    };


    public GetElevationDataResponse getElevationData()
//    public GeoJSON getElevationData()
    {
        int count = 0;
         min = 99999.0;
         max = -99999.0;
        ArrayList<GeoFeatures> features = new ArrayList<>();

        for (int lng = 0; lng < blocksBreadth; lng++)
        {
            for (int lat = 0; lat < blocksWidth; lat++)
            {
                count++;
                double elevationValue = dataGrid[lat][lng];
                FeatureProperties auxProperties = new FeatureProperties(elevationValue,getAreaColor(elevationValue));
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
//        return responseGeoJSON;
    }


    public double calculateFreeSpaceLoss(double frequency,double dbm, int numberOfBlocks)
    {
        double kilometerToMileRatio= 0.621;
        int meters = numberOfBlocks*30;
        double totalInMiles = (meters/1000.0)*kilometerToMileRatio;

        return   36.56 + (20*Math.log10(frequency) + (20*Math.log10(totalInMiles)));

    }
    public GetLossDataResponse getSignalLoss(double gatewayX ,double gatewayY)
    {


        int count = 0;
        min = 99999.0;
        max = -99999.0;
        ArrayList<GeoFeatures> features = new ArrayList<>();

        for (int lng = 0; lng < blocksBreadth; lng++)
        {
            for (int lat = 0; lat < blocksWidth; lat++)
            {
                count++;
                double elevationValue = dataGrid[lat][lng];
                FeatureProperties auxProperties = new FeatureProperties(elevationValue,getAreaColor(elevationValue));
                GeometryData auxGeometry = new GeometryData(geoSquareBuilder( convertGridBlockToCoord(lat,lng)));
                features.add(new GeoFeatures(auxProperties,auxGeometry));

                if (elevationValue>max)
                    max = elevationValue;

                if (elevationValue<min)
                    min = elevationValue;

            }
        }

        GeoJSON responseGeoJSON = new GeoJSON(features);
        return new GetLossDataResponse("Data successfully retrieved",true,min,max,responseGeoJSON);

    }
}
