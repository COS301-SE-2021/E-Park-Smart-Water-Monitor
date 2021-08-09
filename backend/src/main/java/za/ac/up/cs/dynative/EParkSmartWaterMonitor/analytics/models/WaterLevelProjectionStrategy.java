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
        int regressionDegree = 4;
        ArrayList<Measurement> waterLevelData = new ArrayList<>();
        for (GetDeviceInnerResponse innerData :
                deviceDataResponse.getInnerResponses()) {
            for (Measurement waterLevelMeasurement :
                    innerData.getMeasurements()) {
                if (waterLevelMeasurement.getType().equals("WATER_LEVEL")) {
                    waterLevelData.add(waterLevelMeasurement);
                }
            }
        }

        Map<String, List<Measurement>> set = waterLevelData.stream()
                .collect(Collectors.groupingBy(Measurement::getDeviceDate));
        ArrayList<Double> dailyAverage = new ArrayList<>();
//        set.forEach((key, value) -> dailyAverage.add(average(value)));

        /*
            The business below is done on line 50
         */
        double[] test = {75, 71, 70, 74, 30, 28, 25, 30, 24, 23, 18, 22, 21, 18, 18, 16, 14, 10, 11, 9};
        for (double t : test) {
            dailyAverage.add(t);
        }
        System.out.println(dailyAverage);

        final WeightedObservedPoints dataPoints = new WeightedObservedPoints();
        for (int x = 0; x < dailyAverage.size(); x++){
            dataPoints.add(x,dailyAverage.get(x));
        }
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(regressionDegree);
        final double[] coeff = fitter.fit(dataPoints.toList());
        System.out.println("coef="+ Arrays.toString(coeff));

        int counter = 0;
        int size = dailyAverage.size();
        while (counter < deviceProjectionRequest.getLength()) {
            dailyAverage.add(coeff[0]
                    + coeff[1] * (counter + 1 + size)
                    + coeff[2] * (Math.pow(counter + 1 + size, 2))
                    + coeff[3] * (Math.pow(counter + 1 + size, 3))
                    + coeff[4] * (Math.pow(counter + 1 + size, 4)));
            counter++;
        }

        System.out.println(dailyAverage);

        return new DeviceProjectionResponse(
                "Success",
                true,
                "waterlevel",
                deviceProjectionRequest.getLength(),
                null,
                dailyAverage,
                null);
    }

    private double average(List<Measurement> value) {
        double total = 0;
        for (Measurement m :
                value) {
            total += m.getEstimateValue();
        }
        return total/value.size();
    }
}
