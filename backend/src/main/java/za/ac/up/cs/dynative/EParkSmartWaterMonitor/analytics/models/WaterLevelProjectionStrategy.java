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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        ArrayList<Double> optimisticPredictions = new ArrayList<>();
        ArrayList<Double> conservativePredictions = new ArrayList<>();
        ArrayList<Measurement> waterLevelData = new ArrayList<>();
        ArrayList<Measurement> temperatureData = new ArrayList<>();
        WaterSite waterSite = waterSiteByDeviceResponse.getWaterSite();

        extractWaterLevelData(waterLevelData);
        latestTemperatureData(temperatureData);
        groupedWaterLevelMeasurements = waterLevelData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
        groupedWaterLevelMeasurements.forEach((key, value) -> dailyAverageWaterLevel.add(average(value)));

        groupedTemperatureMeasurements = temperatureData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
        groupedTemperatureMeasurements.forEach((key, value) -> dailyAverageTemperature.add(average(value)));

        System.out.println("dailyAverages=" + dailyAverageWaterLevel);

        for (int x = 0; x < dailyAverageWaterLevel.size(); x++){
            dataPoints.add(x,dailyAverageWaterLevel.get(x));
        }

        fitter = PolynomialCurveFitter.create(regressionDegree);
        coefficients = fitter.fit(dataPoints.toList());
        polynomialRegressionPrediction(dailyAverageWaterLevel, coefficients);

        System.out.println("coef="+ Arrays.toString(coefficients));
        System.out.println("dailyAverageWaterLevel and predicted=" + dailyAverageWaterLevel);

        optimisticProjection(optimisticPredictions,
                dailyAverageTemperature,
                waterSite,
                deviceProjectionRequest);

        conservativeProjection(conservativePredictions);

        return new DeviceProjectionResponse(
                "Success",
                true,
                "waterlevel",
                deviceProjectionRequest.getLength(),
                optimisticPredictions,
                dailyAverageWaterLevel,
                conservativePredictions);
    }

    private void conservativeProjection(ArrayList<Double> conservativePredictions) {
        // premised on the fact that the ambient temperature will increase or remain the same in the coming days

    }

    private void optimisticProjection(ArrayList<Double> optimisticPredictions,
                                      ArrayList<Double> dailyAverageTemperatures,
                                      WaterSite waterSite,
                                      DeviceProjectionRequest deviceProjectionRequest) {
        /*
            premised on the fact that the ambient temperature will decrease or remain the same in the coming days
         */
        double gravity = 9.81;
        double kinematicViscosity = 0.00001516;
        double PR = 0.7309; // prandtl number
        double K = 0.02514; // thermal conductivity
        double betaValue = 0.000195;
        double surfaceTemperature = 15.0;
        double heatOfVaporization = 2454000; // winter & summer option
        ArrayList<Double> futureAmbientTemperatures = new ArrayList<>();
        populatateFutureAmbient(futureAmbientTemperatures,dailyAverageTemperatures);

        if (waterSite.getShape().equals("circle")) {
            double surfaceArea = Math.PI * Math.pow(waterSite.getRadius(), 2.0);
            double diameter = waterSite.getRadius() * 2.0;
            double perimeter = 2 * Math.PI * waterSite.getRadius();
            double characteristicLength = diameter / 4.0;

            double filmTemp = (surfaceArea + futureAmbientTemperatures.get(0)) / 2.0;
            double rayleighNumber = ((gravity * betaValue)
                    * Math.abs(surfaceTemperature - futureAmbientTemperatures.get(0))
                    * Math.pow(characteristicLength, 3))
                    / Math.pow(kinematicViscosity, 2)
                    * PR;
            double nusseltNumber = 0.15 * Math.cbrt(rayleighNumber);
            double heatTransferCoefficient = (K * nusseltNumber) / characteristicLength;
            double heatTransferRate = heatTransferCoefficient * (surfaceArea - (futureAmbientTemperatures.get(0)));
            double massFlowRate = heatTransferRate / heatOfVaporization;
            double secondsInDay = 24 * 60 * 60;
            double evaporation = massFlowRate * secondsInDay;
            System.out.println("vars");
            System.out.println(futureAmbientTemperatures.get(0));
            System.out.println(rayleighNumber);
            System.out.println(nusseltNumber);
            System.out.println(heatTransferCoefficient);
            System.out.println(heatTransferRate);
            System.out.println(evaporation);
        }
        else {
            System.out.println("oogabooga");
        }
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

    private void populatateFutureAmbient(ArrayList<Double> futureAmbientTemperatures,
                                         ArrayList<Double> dailyAverageTemperatures) {
        for (int i = 0; i < deviceProjectionRequest.getLength(); i++) {
            if (Math.random() < 0.2) {
                futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) - 2);
            }
            else if (Math.random() >= 0.2 && Math.random() < 0.5) {
                futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) - 1);
            }
            else {
                futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1));
            }
        }
    }
}
