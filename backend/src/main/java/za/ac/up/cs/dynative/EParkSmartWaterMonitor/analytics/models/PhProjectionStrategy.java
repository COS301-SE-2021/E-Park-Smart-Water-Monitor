package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * This class forecasts the future ph level of a water source.
 * Making use of an ARIMA model a 5-day forecast is made into the future.
 */
public class PhProjectionStrategy extends AbstractProjectionStrategy
{

    private final DeviceProjectionRequest deviceProjectionRequest;
    private final String arimaUrl;

    /**
     * Constructor for the WaterLevelProjectionStrategy
     * Initialized the necessary variables and calls the super class
     *
     * @param deviceProjectionRequest request for the projections
     * @param deviceDataResponse metrics gathered by a specific device
     * @param waterSiteByDeviceResponse water site that is monitored by this specific device
     * @param arimaUrl ARIMA model server url
     */
    public PhProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest,
                                GetDeviceDataResponse deviceDataResponse,
                                FindWaterSiteByDeviceResponse waterSiteByDeviceResponse,
                                String arimaUrl)
    {
        super(deviceProjectionRequest,deviceDataResponse,waterSiteByDeviceResponse);
        this.deviceProjectionRequest = deviceProjectionRequest;
        this.arimaUrl = arimaUrl;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }

    /***
     * Prediction strategy for ph level.
     *
     * Step 1: Group, sort & filter the data.
     * Step 2: Get forecast from the ARIMA model
     * Step 3: Add dates to match forecast period.
     * @return successful forecast
     */
    @Override
    public DeviceProjectionResponse predict()
    {
        if (getDeviceProjectionRequest().getLength() != 5)
        {
            return failedProjection("Invalid projection period. Period must be 5!", "ph");
        }
        else
        {
            // Step 1:
            Map<String, List<Measurement>> groupedPhMeasurements;
            ArrayList<Double> dailyAveragePhMeasurements = new ArrayList<>();
            ArrayList<Double> dailyAveragePhLevelError = new ArrayList<>();
            ArrayList<String> labelDates = new ArrayList<>();

            ArrayList<Measurement> phMeasurements = extractData("WATER_QUALITY");
            groupedPhMeasurements = phMeasurements.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
            groupedPhMeasurements.forEach((key, value) -> {
                dailyAveragePhMeasurements.add(average(value,false) / 10 + 3);
                labelDates.add(value.get(0).getDeviceDateTime().substring(0,10));
                dailyAveragePhLevelError.add(average(value, true));
            });

            ArrayList<String> labelDatesFinal = labelDates;
            ArrayList<Double> dailyAveragePhFinal = dailyAveragePhMeasurements;
            ArrayList<Double> dailyAveragePhErrorFinal = dailyAveragePhLevelError;

            if (labelDates.size() > 30)
            {
                labelDatesFinal = (ArrayList<String>) labelDates.subList(0, 30);
            }
            if (dailyAveragePhMeasurements.size() > 30)
            {
                dailyAveragePhFinal = (ArrayList<Double>) dailyAveragePhMeasurements.subList(0,30);
            }
            if (dailyAveragePhErrorFinal.size() > 30)
            {
                dailyAveragePhErrorFinal = (ArrayList<Double>) dailyAveragePhErrorFinal.subList(0,30);
            }
            labelDates.sort(String::compareTo);

            // Step 2:
            ArrayList<Double> phPredictions;
            ArrayList<Double> optimisticPhLevelPrediction = null;
            ArrayList<Double> conservativePhLevelPrediction = null;
            try
            {
                phPredictions = getArimaPrediction(dailyAveragePhFinal);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return failedProjection("Couldn't generate a prediction","ph");
            }
            // Step 3:
            if(phPredictions != null && (phPredictions.size() > labelDates.size())) {
                addLabelDates(labelDatesFinal,labelDatesFinal.get(labelDatesFinal.size()-1));

                optimisticPhLevelPrediction = new ArrayList<>(phPredictions);
                conservativePhLevelPrediction = new ArrayList<>(phPredictions);

                accountForError(optimisticPhLevelPrediction, dailyAveragePhErrorFinal, true);
                accountForError(conservativePhLevelPrediction, dailyAveragePhErrorFinal, false);
            }

            return successfulProjection("Sucessfully generated Ph projections.",
                    "ph",
                    phPredictions,
                    optimisticPhLevelPrediction,
                    conservativePhLevelPrediction,
                    labelDatesFinal);
        }
    }

    /**
     * This functions acts as a helper wrapper function that sends a post http request.
     * It receives the 5 day ph prediction as a string response back and passes it to
     * a function where this string is processed.
     *
     * @param thirtyDayDailyAveragePhLevel latest dailyAveragePhLevel readings of a specific device
     * @return returns the new array with the forecasts
     * @throws IOException when an exception is thrown during the http request
     */
    private ArrayList<Double> getArimaPrediction(ArrayList<Double> thirtyDayDailyAveragePhLevel) throws IOException
    {
        String phLevels = thirtyDayDailyAveragePhLevel.toString().replaceAll("\\[+|]+","");
        phLevels = sendPostRequest(phLevels);

        if (phLevels.equals("")) {
            return null;
        }
        return processModelOutput(phLevels.trim());
    }

    /**
     * sendPostRequest sends a post request to a python server hosting the ARIMA model
     * The json object passed with the post request looks like:
     * {
     *     "phLevels": "6.2,6.4,..."
     * }
     *
     * @param phLevels latest observed ph levels as a string
     * @return concatenates forecasts to the passed in string of values or the empty string if an error has occurred
     * @throws IOException when an exception is thrown during the http request
     */
    private String sendPostRequest(String phLevels) throws IOException
    {
        HttpPost post = new HttpPost(arimaUrl);

        JSONObject json = new JSONObject();
        json.put("phLevels",phLevels);

        StringEntity param = new StringEntity(json.toString());
        post.addHeader("Content-type", "application/json");
        post.setEntity(param);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post))
        {
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            }
        }
        return "";
    }

    /***
     * Utility function that tris any unwanted characters from the forecasted ph levels.
     * @param predictions forecasted ph levels
     * @return processed forecasted output
     */
    private ArrayList<Double> processModelOutput(String predictions)
    {
        predictions = predictions.replaceAll("\\[+|]+|'","");
        predictions = predictions.replaceAll(",,",",");
        String[] predictedStringValues = predictions.split(",");

        ArrayList<Double> forecastValues = new ArrayList<>();
        for (String value :
                predictedStringValues)
        {
            forecastValues.add(Double.parseDouble(value));
        }
        return forecastValues;
    }

    /**
     * Function that takes into account the error in the measurement, calculated with the Kalman filter.
     * Has 2 variations an optimistic or conservative variation.
     *
     * Optimistic: being that we underestimate the actual water ph level(add).
     * Conservative: being we overestimate the actual water ph level(subtract)
     *
     * @param dailyAveragePhLevel: array to mutate
     * @param dailyPhLevelError: error to mutate with
     * @param isOptimistic: boolean on how to mutate the value
     * @return mutated arraylist with the adjusted values
     */
    private ArrayList<Double> accountForError(ArrayList<Double> dailyAveragePhLevel,
                                              ArrayList<Double> dailyPhLevelError,
                                              boolean isOptimistic)

    {
        int size = dailyAveragePhLevel.size() - getDeviceProjectionRequest().getLength();
        double averageError = getAverageError(dailyPhLevelError);

        double adjustedPredictionValue;
        if (isOptimistic)
        {
            for (int i = size; i < dailyAveragePhLevel.size(); i++)
            {
                adjustedPredictionValue = dailyAveragePhLevel.get(i);
                adjustedPredictionValue += (averageError);// * (i - size) + 1);
                dailyAveragePhLevel.set(i,Math.abs(adjustedPredictionValue));
            }
        }
        else
        {
            for (int i = size; i < dailyAveragePhLevel.size(); i++)
            {
                adjustedPredictionValue = dailyAveragePhLevel.get(i);
                adjustedPredictionValue -= (averageError);// * (i - size) + 1);
                dailyAveragePhLevel.set(i,Math.abs(adjustedPredictionValue));
            }
        }
        return dailyAveragePhLevel;
    }

    /**
     * Utility function that averages the error in the device's measurements over a certain period
     * @param dailyPhLevelError: array of error in measurements
     * @return the average error over a certain period of time
     */
    private Double getAverageError(ArrayList<Double>  dailyPhLevelError)
    {
        int size = dailyPhLevelError.size();
        Double averageError = 0.0;
        for (Double measurementError :
                dailyPhLevelError)
        {
            averageError += measurementError;
        }
        return averageError / size;
    }

}
