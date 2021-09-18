package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * Temperature Projection strategy class.
 * Can get forecast from weather api and adjust it based on observed data.
 */
public class TemperatureProjectionStrategy extends AbstractProjectionStrategy {

    private final DeviceProjectionRequest deviceProjectionRequest;

    /**
     * Constructor for the WaterLevelProjectionStrategy
     * Initialized the necessary variables and calls the super class
     *
     * @param deviceProjectionRequest request for the projections
     * @param deviceDataResponse metrics gathered by a specific device
     * @param waterSiteByDeviceResponse water site that is monitored by this specific device
     */
    public TemperatureProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest,
                                         GetDeviceDataResponse deviceDataResponse,
                                         FindWaterSiteByDeviceResponse waterSiteByDeviceResponse)
    {
        super(deviceProjectionRequest,deviceDataResponse,waterSiteByDeviceResponse);
        this.deviceProjectionRequest = deviceProjectionRequest;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }

    @Override
    public DeviceProjectionResponse predict()
    {
        final int regressionDegree = 1;
        final WeightedObservedPoints dataPoints = new WeightedObservedPoints();
        final PolynomialCurveFitter fitter;
        final double[] coefficients;
        Map<String, List<Measurement>> groupedTemperatureMeasurements;
        ArrayList<Double> dailyAverageTemperature = new ArrayList<>();
        ArrayList<String> labelDates = new ArrayList<>();

        ArrayList<Measurement> temperatureData = extractData("WATER_TEMP");

        groupedTemperatureMeasurements = temperatureData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
        groupedTemperatureMeasurements.forEach((key, value) -> {
            dailyAverageTemperature.add(average(value,false));
            labelDates.add(value.get(0).getDeviceDateTime().substring(0,10));
        });
        labelDates.sort(String::compareTo);

        System.out.println("dailyAverages=" + dailyAverageTemperature);

        for (int x = 0; x < dailyAverageTemperature.size(); x++)
        {
            dataPoints.add(x,dailyAverageTemperature.get(x));
        }

        fitter = PolynomialCurveFitter.create(regressionDegree);
        coefficients = fitter.fit(dataPoints.toList());
        polynomialRegressionPrediction(dailyAverageTemperature, coefficients);

        return new DeviceProjectionResponse(
                "Success",
                true,
                "temperature",
                deviceProjectionRequest.getLength(),
                null,
                dailyAverageTemperature,
                null,
                labelDates);
    }

    private void polynomialRegressionPrediction(ArrayList<Double> dailyAverageTemperature, double[] coefficients)
    {
        int size = dailyAverageTemperature.size();
        for (int counter = 0; counter < deviceProjectionRequest.getLength(); counter++)
        {
            dailyAverageTemperature.add(coefficients[0]
                    + coefficients[1] * (counter + 1 + size)
                    + coefficients[2] * (Math.pow(counter + 1 + size, 2))
                    + coefficients[3] * (Math.pow(counter + 1 + size, 3)));
        }
    }
}
