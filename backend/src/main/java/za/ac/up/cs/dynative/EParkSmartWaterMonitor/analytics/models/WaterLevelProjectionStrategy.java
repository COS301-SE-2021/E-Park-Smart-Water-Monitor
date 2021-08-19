package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceInnerResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;

import java.util.*;
import java.util.stream.Collectors;

public class WaterLevelProjectionStrategy implements ProjectionStrategyInterface {

    private final DeviceProjectionRequest deviceProjectionRequest;
    private final GetDeviceDataResponse deviceDataResponse;
    private final FindWaterSiteByDeviceResponse waterSiteByDeviceResponse;

    public WaterLevelProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest,
                                        GetDeviceDataResponse deviceDataResponse,
                                        FindWaterSiteByDeviceResponse waterSiteByDeviceResponse) {
        this.deviceProjectionRequest = deviceProjectionRequest;
        this.deviceDataResponse = deviceDataResponse;
        this.waterSiteByDeviceResponse = waterSiteByDeviceResponse;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }


    @Override
    public DeviceProjectionResponse predict() {
        /*
            When changing the regression degree, you must also
            add the necessary coefficients polynomialRegressionPrediction function
         */
        final int regressionDegree = 3;
        final WeightedObservedPoints dataPoints = new WeightedObservedPoints();
        final PolynomialCurveFitter fitter;
        final double[] coefficients;
        Map<String, List<Measurement>> groupedWaterLevelMeasurements;
        Map<String, List<Measurement>> groupedTemperatureMeasurements;
        ArrayList<Double> dailyAverageWaterLevel = new ArrayList<>();
        ArrayList<Double> dailyAverageTemperature = new ArrayList<>();
        ArrayList<Double> optimisticPredictions;
        ArrayList<Double> conservativePredictions;
        ArrayList<Measurement> waterLevelData = new ArrayList<>();
        ArrayList<Measurement> temperatureData = new ArrayList<>();
        ArrayList<String> labelDates = new ArrayList<>();
        WaterSite waterSite = waterSiteByDeviceResponse.getWaterSite();

        extractWaterLevelData(waterLevelData);
        latestTemperatureData(temperatureData);

        groupedWaterLevelMeasurements = waterLevelData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
        groupedWaterLevelMeasurements.forEach((key, value) -> {
            dailyAverageWaterLevel.add(average(value));
            labelDates.add(value.get(0).getDeviceDateTime().substring(0,10));
        });
        labelDates.sort(String::compareTo);


        groupedTemperatureMeasurements = temperatureData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
        groupedTemperatureMeasurements.forEach((key, value) -> dailyAverageTemperature.add(average(value)));

        System.out.println("dailyAverages=" + dailyAverageWaterLevel);

        for (int x = 0; x < dailyAverageWaterLevel.size(); x++){
            dataPoints.add(x,dailyAverageWaterLevel.get(x));
        }

        fitter = PolynomialCurveFitter.create(regressionDegree);
        coefficients = fitter.fit(dataPoints.toList());
        polynomialRegressionPrediction(dailyAverageWaterLevel, coefficients);

        optimisticPredictions = new ArrayList<>(dailyAverageWaterLevel);
        conservativePredictions = new ArrayList<>(dailyAverageWaterLevel);

        boolean validOptimistic = optimisticProjection(optimisticPredictions,
                dailyAverageTemperature,
                waterSite,
                deviceProjectionRequest);

        boolean validConservative  = conservativeProjection(conservativePredictions,
                dailyAverageTemperature,
                waterSite,
                deviceProjectionRequest);
        if (validConservative && validOptimistic) {
            System.out.println("coef="+ Arrays.toString(coefficients));
            System.out.println("optimisticWaterLevel and predicted=" + optimisticPredictions);
            System.out.println("dailyAverageWaterLevel and predicted=" + dailyAverageWaterLevel);
            System.out.println("conservativeWaterLevel and predicted=" + conservativePredictions);

            return new DeviceProjectionResponse(
                    "Success",
                    true,
                    "waterlevel",
                    deviceProjectionRequest.getLength(),
                    optimisticPredictions,
                    dailyAverageWaterLevel,
                    conservativePredictions,
                    labelDates);
        }
        else return new DeviceProjectionResponse(
                "Incorrect Watersite dimensions",
                false,
                "waterlevel",
                deviceProjectionRequest.getLength(),
                null,
                null,
                null,
                null);
    }

    private boolean conservativeProjection(ArrayList<Double> optimisticPredictions,
                                        ArrayList<Double> dailyAverageTemperatures,
                                        WaterSite waterSite,
                                        DeviceProjectionRequest deviceProjectionRequest) {
        // premised on the fact that the ambient temperature will increase or remain the same in the coming days
        double gravity = 9.81;
        double betaValue = 0.000195;
        double surfaceTemperature = 15.0;
        double heatOfVaporization = 2454000; // winter & summer option
        double[] prandtlNumbers = {0.735, 0.7336, 0.7323, 0.7309, 0.7296, 0.7282,0.7268, 0.7255};
        double[] thermalConductivity = {0.02401, 0.02439, 0.02476, 0.02514, 0.02551, 0.02588,0.02625,0.02662};
        double[] kinematicViscosities = {0.00001382, 0.00001426, 0.00001470, 0.00001516, 0.00001562, 0.00001608, 0.00001655, 0.00001702};
        ArrayList<Double> futureAmbientTemperatures = new ArrayList<>();

        populatateFutureAmbient(futureAmbientTemperatures,dailyAverageTemperatures,false);

        return evaporationCalc(optimisticPredictions,
                waterSite,
                deviceProjectionRequest,
                gravity,
                betaValue,
                surfaceTemperature,
                heatOfVaporization,
                prandtlNumbers,
                thermalConductivity,
                kinematicViscosities,
                futureAmbientTemperatures);
    }

    private boolean optimisticProjection(ArrayList<Double> optimisticPredictions,
                                      ArrayList<Double> dailyAverageTemperatures,
                                      WaterSite waterSite,
                                      DeviceProjectionRequest deviceProjectionRequest) {
        /*
            premised on the fact that the ambient temperature will decrease or remain the same in the coming days
         */
        double gravity = 9.81;
        double betaValue = 0.000195;
        double surfaceTemperature = 15.0;
        double heatOfVaporization = 2454000; // winter & summer option
        double[] prandtlNumbers = {0.735, 0.7336, 0.7323, 0.7309, 0.7296, 0.7282,0.7268, 0.7255};
        double[] thermalConductivity = {0.02401, 0.02439, 0.02476, 0.02514, 0.02551, 0.02588,0.02625,0.02662};
        double[] kinematicViscosities = {0.00001382, 0.00001426, 0.00001470, 0.00001516, 0.00001562, 0.00001608, 0.00001655, 0.00001702};
        ArrayList<Double> futureAmbientTemperatures = new ArrayList<>();

        populatateFutureAmbient(futureAmbientTemperatures,dailyAverageTemperatures,true);

        return evaporationCalc(optimisticPredictions,
                waterSite,
                deviceProjectionRequest,
                gravity,
                betaValue,
                surfaceTemperature,
                heatOfVaporization,
                prandtlNumbers,
                thermalConductivity,
                kinematicViscosities,
                futureAmbientTemperatures);
    }

    private void polynomialRegressionPrediction(ArrayList<Double> dailyAverageWaterLevel, double[] coefficients) {
        int size = dailyAverageWaterLevel.size();
        for (int counter = 0; counter < deviceProjectionRequest.getLength(); counter++) {
            dailyAverageWaterLevel.add(coefficients[0]
                    + coefficients[1] * (counter + 1 + size)
                    + coefficients[2] * (Math.pow(counter + 1 + size, 2))
                    + coefficients[3] * (Math.pow(counter + 1 + size, 3)));
        }
    }

    private void extractWaterLevelData(ArrayList<Measurement> waterLevelData) {
        for (GetDeviceInnerResponse innerData :
                deviceDataResponse.getInnerResponses()) {
            for (Measurement waterLevelMeasurement :
                    innerData.getMeasurements()) {
                if (waterLevelMeasurement.getType().equals("WATER_LEVEL")) {
                    waterLevelData.add(waterLevelMeasurement);
                }
            }
        }
    }

    private void latestTemperatureData(ArrayList<Measurement> temperatureData) {
        for (GetDeviceInnerResponse innerData :
                deviceDataResponse.getInnerResponses()) {
            for (Measurement temperatureMeasurement :
                    innerData.getMeasurements()) {
                if (temperatureMeasurement.getType().equals("WATER_TEMP")) {
                    temperatureData.add(temperatureMeasurement);
                }
            }
        }
    }

    private double average(List<Measurement> value) {
        double total = 0;
        if (value != null) {
            for (Measurement m :
                    value) {
                total += m.getEstimateValue();
            }
            return total/value.size();
        }
        else return 0;
    }

    private boolean evaporationCalc(ArrayList<Double> optimisticPredictions,
                                 WaterSite waterSite,
                                 DeviceProjectionRequest deviceProjectionRequest,
                                 double gravity,
                                 double betaValue,
                                 double surfaceTemperature,
                                 double heatOfVaporization,
                                 double[] prandtlNumbers,
                                 double[] thermalConductivity,
                                 double[] kinematicViscosities,
                                 ArrayList<Double> futureAmbientTemperatures) {

        if (waterSite.getShape().equals("circle") && waterSite.getRadius() > 0) {
            double surfaceArea = Math.PI * Math.pow(waterSite.getRadius(), 2.0);
            double diameter = waterSite.getRadius() * 2.0;
            double characteristicLength = diameter / 4.0;

            evaporationRateAdjustments(optimisticPredictions,
                    waterSite,
                    deviceProjectionRequest,
                    gravity,
                    betaValue,
                    surfaceTemperature,
                    heatOfVaporization,
                    prandtlNumbers,
                    thermalConductivity,
                    kinematicViscosities,
                    futureAmbientTemperatures,
                    surfaceArea,
                    characteristicLength);
            return true;
        }
        else if (waterSite.getShape().equals("rectangle") && waterSite.getLength() > 0 && waterSite.getWidth() > 0){
            double surfaceArea = waterSite.getLength() * waterSite.getWidth();
            double perimeter = (waterSite.getLength() + waterSite.getWidth()) * 2.0;
            double characteristicLength = surfaceArea / perimeter;

            evaporationRateAdjustments(optimisticPredictions,
                    waterSite,
                    deviceProjectionRequest,
                    gravity,
                    betaValue,
                    surfaceTemperature,
                    heatOfVaporization,
                    prandtlNumbers,
                    thermalConductivity,
                    kinematicViscosities,
                    futureAmbientTemperatures,
                    surfaceArea,
                    characteristicLength);
            return true;
        }
        else {
            return false;
        }
    }

    private void evaporationRateAdjustments(ArrayList<Double> optimisticPredictions,
                                            WaterSite waterSite,
                                            DeviceProjectionRequest deviceProjectionRequest,
                                            double gravity,
                                            double betaValue,
                                            double surfaceTemperature,
                                            double heatOfVaporization,
                                            double[] prandtlNumbers,
                                            double[] thermalConductivity,
                                            double[] kinematicViscosities,
                                            ArrayList<Double> futureAmbientTemperatures,
                                            double surfaceArea,
                                            double characteristicLength) {

        for (int i = 0; i < deviceProjectionRequest.getLength(); i++) {
            double evaporationRate = rateOfEvaporationCalc(gravity,
                    betaValue,
                    surfaceTemperature,
                    heatOfVaporization,
                    prandtlNumbers,
                    thermalConductivity,
                    kinematicViscosities,
                    futureAmbientTemperatures.get(i),
                    surfaceArea,
                    characteristicLength);

            double currWaterLevel = optimisticPredictions.get(optimisticPredictions.size() - deviceProjectionRequest.getLength() + i);
            double currWaterWeight = calculateCurrentWaterWeight(waterSite, currWaterLevel);
            double deltaWaterWeight = currWaterWeight - Math.abs(evaporationRate);
            optimisticPredictions.set(optimisticPredictions.size() - deviceProjectionRequest.getLength() + i,
                    calculateFutureWaterLevel(waterSite,deltaWaterWeight));
        }
    }

    private double calculateCurrentWaterWeight(WaterSite waterSite,  double currentWaterlevel) {
        if (waterSite.getShape().equals("circle")) {
            return (Math.PI * Math.pow(waterSite.getRadius(),2) * currentWaterlevel) * 10;
        }
        else if (waterSite.getShape().equals("rectangle")) {
            return waterSite.getLength() * waterSite.getWidth() * currentWaterlevel;
        }
        return 0;
    }

    private double calculateFutureWaterLevel(WaterSite waterSite, double waterWeight) {
        if (waterSite.getShape().equals("circle")) {
            return waterWeight / (Math.PI * Math.pow(waterSite.getRadius(), 2) * 10);
        }
        else if (waterSite.getShape().equals("rectangle")) {
            return waterWeight / (waterSite.getLength() * waterSite.getWidth());
        }
        return 0;
    }

    private double rateOfEvaporationCalc(double gravity,
                                         double betaValue,
                                         double surfaceTemperature,
                                         double heatOfVaporization,
                                         double[] prandtlNumbers,
                                         double[] thermalConductivity,
                                         double[] kinematicViscosities,
                                         Double futureAmbientTemperature,
                                         double surfaceArea,
                                         double characteristicLength) {
        double PR;
        double K;
        double kinematicViscosity;
        ArrayList<Double> PRandKandViscosity = determinePRandKandViscosity(futureAmbientTemperature,
                prandtlNumbers,
                thermalConductivity,
                kinematicViscosities);
        PR = PRandKandViscosity.get(0);
        K = PRandKandViscosity.get(1);
        kinematicViscosity = PRandKandViscosity.get(2);
        double filmTemp = (surfaceArea + futureAmbientTemperature) / 2.0;
        double rayleighNumber = ((gravity * betaValue)
                * Math.abs(surfaceTemperature - futureAmbientTemperature)
                * Math.pow(characteristicLength, 3))
                / Math.pow(kinematicViscosity, 2)
                * PR;
        double nusseltNumber = 0.15 * Math.cbrt(rayleighNumber);
        double heatTransferCoefficient = (K * nusseltNumber) / characteristicLength;
        double heatTransferRate = heatTransferCoefficient * (surfaceArea - (futureAmbientTemperature));
        double massFlowRate = heatTransferRate / heatOfVaporization;
        double secondsInDay = 24 * 60 * 60;
        return massFlowRate * secondsInDay;
    }

    private void populatateFutureAmbient(ArrayList<Double> futureAmbientTemperatures,
                                         ArrayList<Double> dailyAverageTemperatures, boolean optimistic) {
        for (int i = 0; i < deviceProjectionRequest.getLength(); i++) {
            if (Math.random() < 0.3) {
                if (optimistic)
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) - 2);
                else
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) + 2);
            }
            else if (Math.random() >= 0.3 && Math.random() < 0.6) {
                if (optimistic)
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) - 1);
                else
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) + 1);
            }
            else {
                futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1));
            }
        }
    }


    private ArrayList<Double> determinePRandKandViscosity(Double ambientTemp,
                                              double[] prandtlNumbers,
                                              double[] thermalConductivity,
                                              double[] kinematicViscosites) {
        ArrayList<Double> PRandK = new ArrayList<>();
        if (ambientTemp >= 5 && ambientTemp < 10) {
            PRandK.add(prandtlNumbers[0]);
            PRandK.add(thermalConductivity[0]);
            PRandK.add(kinematicViscosites[0]);
        }
        else if (ambientTemp >= 10 && ambientTemp < 15) {
            PRandK.add(prandtlNumbers[1]);
            PRandK.add(thermalConductivity[1]);
            PRandK.add(kinematicViscosites[1]);
        }
        else if (ambientTemp >= 15 && ambientTemp < 20) {
            PRandK.add(prandtlNumbers[2]);
            PRandK.add(thermalConductivity[2]);
            PRandK.add(kinematicViscosites[2]);
        }
        else if (ambientTemp >= 20 && ambientTemp < 25) {
            PRandK.add(prandtlNumbers[3]);
            PRandK.add(thermalConductivity[3]);
            PRandK.add(kinematicViscosites[3]);
        }
        else if (ambientTemp >= 25 && ambientTemp < 30) {
            PRandK.add(prandtlNumbers[4]);
            PRandK.add(thermalConductivity[4]);
            PRandK.add(kinematicViscosites[4]);
        }
        else if (ambientTemp >= 30 && ambientTemp < 35) {
            PRandK.add(prandtlNumbers[5]);
            PRandK.add(thermalConductivity[5]);
            PRandK.add(kinematicViscosites[5]);
        }
        else if (ambientTemp >= 35 && ambientTemp < 40) {
            PRandK.add(prandtlNumbers[6]);
            PRandK.add(thermalConductivity[6]);
            PRandK.add(kinematicViscosites[6]);
        }
        else {
            PRandK.add(prandtlNumbers[7]);
            PRandK.add(thermalConductivity[7]);
            PRandK.add(kinematicViscosites[7]);
        }
        return PRandK;
    }
}
