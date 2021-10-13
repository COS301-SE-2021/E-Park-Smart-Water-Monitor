package za.ac.up.cs.dynative.EParkSmartWaterMonitor.geodata;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.parameters.P;
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
    Double minLoss = 99999.0;
    Double maxLoss = -99999.0;

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

    public List<Point> lineApproximation(Point from, Point to)
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

//        System.out.println(approximatedPath.toString());

//      t
//        System.out.println("LENGTH: "+approximatedPath.size());
        return approximatedPath;

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
        double range = maxLoss-minLoss;
        double segmentSize = range/9;
        if (loss-minLoss<segmentSize)
        {
            return "#00ff5b";
        }
        else if (loss-minLoss<segmentSize*3)
        {
            return "#00ff29";//
        }
        else if (loss-minLoss<segmentSize*6)
        {
            return "#09ff00";
        }
        else if (loss-minLoss<segmentSize*7)
        {
            return "#6dff00";
        }
        else if (loss-minLoss<segmentSize*8.2)
        {
            return "#d2ff00";
        }
        else if (loss-minLoss<segmentSize*8.8)
        {
            return "#ffc800";
        }
//        else if (loss-minLoss<segmentSize*8)
//        {
        else   return "#ff6400";


    };

    public String getLoss2(double height){
        double range = maxLoss-minLoss;
        double segmentSize = range/13;
        if (height-minLoss<segmentSize)
        {
            return "#00ff5b";
        }
        else if (height-minLoss<segmentSize*2)
        {
            return "#00ff29";
        }
        else if (height-minLoss<segmentSize*3)
        {
            return "#09ff00";
        }
        else if (height-minLoss<segmentSize*4)
        {
            return "#3bff00";
        }
        else if (height-minLoss<segmentSize*5)
        {
            return "#6dff00";
        }
        else if (height-minLoss<segmentSize*6)
        {
            return "#9fff00";
        }
        else if (height-minLoss<segmentSize*7)
        {
            return "#d2ff00";
        }
        else if (height-minLoss<segmentSize*8)
        {
            return "#fffa00";
        }
        else if (height-minLoss<segmentSize*9)
        {
            return "#ffc800";
        }
        else if (height-minLoss<segmentSize*10)
        {
            return "#ff9600";
        }
        else if (height-minLoss<segmentSize*11)
        {
            return "#ff6400";
        }
        else if (height-minLoss<segmentSize*12)
        {
            return "#ff3200";
        }
        else return "#ff0000";

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


    public double calculateFreeSpaceLoss(double frequency, List<Point> line)
    {
        int numberOfBlocks = line.size()-1;
        double meters = numberOfBlocks*30.0;

        if (line.get(0).x==line.get(line.size()-1).x||line.get(0).y==line.get(line.size()-1).y) {
            double sideOneBlock = Math.abs(line.get(0).x - line.get(line.size() - 1).x) * 30.0;
            double sideTwoLength = Math.abs(line.get(0).y - line.get(line.size() - 1).y) * 30.0;
            double diagonalLength = Math.sqrt(Math.pow(sideOneBlock, 2) + Math.pow(sideTwoLength, 2));

            meters = diagonalLength;
        }
        double kilometerToMileRatio= 0.621;
        double totalInMiles = (meters/1000.0)*kilometerToMileRatio;

        double dBLoss;

        if (numberOfBlocks==0) {
            dBLoss = 36.56 + (20 * Math.log10(frequency)+(20 * Math.log10(0.03*kilometerToMileRatio)));
        }
        else {
            dBLoss = 36.56 + (20 * Math.log10(frequency) + (20 * Math.log10(totalInMiles)));
        }
        return  dBLoss;

    }


    public double modifyLossBasedOnElevation(List<Point> line,double loss)
    {
        int count = 0;
        double startingElevation = dataGrid[line.get(0).x][line.get(0).y];
        for (Point current:line)
        {
            if (dataGrid[current.x][current.y]>startingElevation)
                count++;

        }
        return loss*(1+(0.3*count));
    }

    public GeoJSON getSignalLoss(double gatewayX ,double gatewayY)
    {
        Coordinate startingCoord= new Coordinate(gatewayX,gatewayY);
        Point startingPoint = convertCoordToGridBlock(startingCoord);
        int count = 0;
        min = 99999.0;
        max = -99999.0;

        ArrayList<GeoFeatures> features = new ArrayList<>();
        ArrayList<Double> lossArray = new ArrayList<>();

        for (int lng = 0; lng < blocksBreadth; lng++)
        {
            for (int lat = 0; lat < blocksWidth; lat++)
            {
                count++;
                List<Point> approximatedLine= lineApproximation(startingPoint,new Point(lat,lng));

                double lossValue =calculateFreeSpaceLoss(2400,approximatedLine);//loss calc


                lossValue = modifyLossBasedOnElevation(approximatedLine,lossValue);
                lossArray.add(lossValue);
                if (lossValue>maxLoss)
                    maxLoss = lossValue;

                if (lossValue<minLoss)
                    minLoss = lossValue;

            }
        }

        int lossCounter = 0;
        for (int lng = 0; lng < blocksBreadth; lng++)
        {
            for (int lat = 0; lat < blocksWidth; lat++)
            {

                FeatureProperties auxProperties = new FeatureProperties(lossArray.get(lossCounter),getLoss2(lossArray.get(lossCounter++)));
                GeometryData auxGeometry = new GeometryData(geoSquareBuilder( convertGridBlockToCoord(lat,lng)));
                features.add(new GeoFeatures(auxProperties,auxGeometry));


            }
        }

        System.out.println(minLoss);
        System.out.println(maxLoss);
        GeoJSON responseGeoJSON = new GeoJSON(features);
        System.out.println();
        return responseGeoJSON;
//        return new GetLossDataResponse("Data successfully retrieved",true,minLoss,maxLoss,responseGeoJSON);

    }
}
