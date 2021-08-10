package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceInnerResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WaterLevelProjectionStrategy implements ProjectionStrategyInterface {

    private final DeviceProjectionRequest deviceProjectionRequest;
    private final GetDeviceDataResponse deviceDataResponse;

    public WaterLevelProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest, GetDeviceDataResponse deviceDataResponse) {
        this.deviceProjectionRequest = deviceProjectionRequest;
        this.deviceDataResponse = deviceDataResponse;
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
        Map<String, List<Measurement>> groupedMeasurements;
        ArrayList<Double> dailyAverage = new ArrayList<>();
        ArrayList<Double> optimisticPredictions = new ArrayList<>();
        ArrayList<Double> conservativePredictions = new ArrayList<>();
        ArrayList<Measurement> waterLevelData = new ArrayList<>();

        extractWaterLevelData(waterLevelData);
        groupedMeasurements = waterLevelData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
        groupedMeasurements.forEach((key, value) -> dailyAverage.add(average(value)));

        System.out.println("dailyAverages=" + dailyAverage);

        for (int x = 0; x < dailyAverage.size(); x++){
            dataPoints.add(x,dailyAverage.get(x));
        }

        fitter = PolynomialCurveFitter.create(regressionDegree);
        coefficients = fitter.fit(dataPoints.toList());
        polynomialRegressionPrediction(dailyAverage, coefficients);

        System.out.println("coef="+ Arrays.toString(coefficients));
        System.out.println("dailyAverage and predicted=" + dailyAverage);

        optimisticProjection(optimisticPredictions);
        conservativeProjection(conservativePredictions);

        return new DeviceProjectionResponse(
                "Success",
                true,
                "waterlevel",
                deviceProjectionRequest.getLength(),
                optimisticPredictions,
                dailyAverage,
                conservativePredictions);
    }

    private void conservativeProjection(ArrayList<Double> conservativePredictions) {

    }

    private void optimisticProjection(ArrayList<Double> optimisticPredictions) {
    }

    private void polynomialRegressionPrediction(ArrayList<Double> dailyAverage, double[] coefficients) {
        int size = dailyAverage.size();
        for (int counter = 0; counter < deviceProjectionRequest.getLength(); counter++) {
            dailyAverage.add(coefficients[0]
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
}
