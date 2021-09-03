package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WaterLevelProjectionStrategy extends AbstractProjectionStrategy
{
    /**
     * Variables used throughout the program
     */
    private final String apikey;
    private final String weatherUrl;
    private final int lowerPolynomialDegreeLimit;
    private final int upperPolynomialDegreeLimit;
    private double upperLimit;
    private final double gravity = 9.81;
    private final double betaValue = 0.000195;
    private final double surfaceTemperature = 15.0;
    private final double heatOfVaporization = 2454000;
    private final double[] prandtlNumbers = {0.735, 0.7336, 0.7323, 0.7309, 0.7296, 0.7282,0.7268, 0.7255};
    private final double[] thermalConductivity = {0.02401, 0.02439, 0.02476, 0.02514, 0.02551, 0.02588,0.02625,0.02662};
    private final double[] kinematicViscosities = {0.00001382, 0.00001426, 0.00001470, 0.00001516, 0.00001562, 0.00001608, 0.00001655, 0.00001702};

    /**
     * Constructor for the WaterLevelProjectionStrategy
     * Initialized the necessary variables and calls the super class
     *
     * @param deviceProjectionRequest request for the projections
     * @param deviceDataResponse metrics gathered by a specific device
     * @param waterSiteByDeviceResponse water site thath is monitored by this specific device
     * @param apikey weather api key
     * @param weatherUrl url for the weather api
     */
    public WaterLevelProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest,
                                        GetDeviceDataResponse deviceDataResponse,
                                        FindWaterSiteByDeviceResponse waterSiteByDeviceResponse,
                                        String apikey,
                                        String weatherUrl)
    {
        super(deviceProjectionRequest, deviceDataResponse, waterSiteByDeviceResponse);
        this.apikey = apikey;
        this.weatherUrl = weatherUrl;
        this.upperLimit = 0;
        this.lowerPolynomialDegreeLimit = 2;
        this.upperPolynomialDegreeLimit = 10;
    }

    /**
     * Prediction method of the strategy pattern.
     * Varies based on which metric we want to have a prediction for.
     *
     * General structure of the algorithm:
     * Step 1: Data contains arbitrary frequency, group into usable frequency in this case daily.
     * Step 2: Sort and filter the data, filter out garbage values, sort the data such that latest data point is last entry.
     * Step 3: Check prediction request period is valid and not larger than observed data
     * Step 4: Get temperature forecast for the length of prediction
     * Step 5: Check if a device has enough data to do a polynomial regression otherwise do linear
     *
     *
     * General development remarks:
     * Sorting and trimming of the data:
     * Take note the sorting of labelDates should be removed once in production.
     * This sort has to happen because we have to change the date of measurements as they get
     * sent in by the device, but our DB sorts by an internal unmodifiable timestamp.
     *
     * Furthermore, once in production we would have to reverse the dates, water level
     * and temperature data.
     *
     * If the dataset contains more than 30 data points it should be trimmed down to only 30 days
     * worth of data, our projections are more short term than long term as the water sites get
     * refilled relatively often.
     *
     * Final variables declared, as they are now ready to be used. Also, java doesn't like
     * it if you modify a variable if you use it in a lambda function.
     *
     * General remarks on optimistic prediction:
     * Optimistic predictions: Functions as an upwards "pull" factor, towards higher/more optimistic future water level prediction
     *  - Premised on the fact that our measurements are negatively biased i.e.
     * more often than not the error in our measurements are overcompensating.
     *  - Evaporation will also have an effect on water level and can be calculated in a generalized environment.
     * This functions as a counterbalance to the upward pulling factor of the error.
     *
     *
     * General remarks on conservative prediction:
     * Conservative predictions: Functions as a downward "pull" factor, towards lower/more conservative future water level predictions.
     *  - Premised on the fact that our measurements are positively biased i.e.
     * more often than not the error in our measurements are painting a "better picture" than it should.
     *  - Evaporation will also have an effect on water level and can be calculated in a generalized
     * environment.
     *
     * @return DeviceProjectionResponse
     */
    @Override
    public DeviceProjectionResponse predict()
    {
        // STEP 1: Extraction of data relevant to water level prediction
        ArrayList<Measurement> waterLevelData = extractData("WATER_LEVEL");
        ArrayList<Measurement> temperatureData = extractData("WATER_TEMP");

        if (waterLevelData.size() > 0)
        {
            // STEP 2: Grouping of extracted water level & temperature data into daily frequency
            ArrayList<Double> dailyAverageWaterLevel = new ArrayList<>();
            ArrayList<Double> dailyAverageWaterLevelError = new ArrayList<>();
            ArrayList<Double> dailyAverageTemperature = new ArrayList<>();
            ArrayList<String> labelDates = new ArrayList<>();

            Map<String, List<Measurement>> groupedWaterLevelMeasurements = waterLevelData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
            groupedWaterLevelMeasurements.forEach((key, value) -> {
                dailyAverageWaterLevel.add(average(value, false));
                labelDates.add(value.get(0).getDeviceDateTime().substring(0,10));
                dailyAverageWaterLevelError.add(average(value, true));
            });
            upperLimit = Collections.max(dailyAverageWaterLevel);

            // General development remarks
            Map<String, List<Measurement>> groupedTemperatureMeasurements = temperatureData.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
            groupedTemperatureMeasurements.forEach((key, value) -> dailyAverageTemperature.add(average(value, false)));

            ArrayList<String> labelDatesFinal = labelDates;
            ArrayList<Double> dailyAverageTemperatureFinal = dailyAverageTemperature;
            ArrayList<Double> dailyAverageWaterLevelFinal = dailyAverageWaterLevel;
            ArrayList<Double> dailyAverageWaterLevelErrorFinal = dailyAverageWaterLevelError;

            if (labelDates.size() > 30)
            {
                labelDatesFinal = (ArrayList<String>) labelDates.subList(0, 30);
            }
            if (dailyAverageTemperature.size() > 30)
            {
                dailyAverageTemperatureFinal = (ArrayList<Double>) dailyAverageTemperature.subList(0,30);
            }
            if (dailyAverageWaterLevel.size() > 30)
            {
                dailyAverageWaterLevelFinal = (ArrayList<Double>) dailyAverageWaterLevel.subList(0,30);
            }
            if (dailyAverageWaterLevelError.size() > 30)
            {
                dailyAverageWaterLevelErrorFinal = (ArrayList<Double>) dailyAverageWaterLevelError.subList(0,30);
            }

            // General development remarks
            labelDates.sort(String::compareTo);
            /*
                Collections.reverse(labelDatesFinal);
                Collections.reverse(dailyAverageWaterLevelFinal);
                Collections.reverse(dailyAverageTemperatureFinal);
                Collections.reverse(dailyAverageWaterLevelErrorFinal);
             */

            // STEP 3:
            if (getDeviceProjectionRequest().getLength() < dailyAverageWaterLevel.size())
            {
                // STEP 4:
                ArrayList<Double> futureTemperatures = getFutureTemperatures(dailyAverageTemperatureFinal);
                dailyAverageTemperatureFinal.addAll(futureTemperatures);

                // STEP 5:
                ArrayList<Double> realisticWaterLevelPredictions;
                ArrayList<Double> optimisticWaterLevelPrediction;
                ArrayList<Double> conservativeWaterLevelPrediction;
                if (dailyAverageWaterLevelFinal.size() < 7)
                {
                    // linear regression
                    realisticWaterLevelPredictions = linearRegressionPrediction(dailyAverageWaterLevelFinal);
                }
                else
                {
                    // dynamic polynomial regression
                    realisticWaterLevelPredictions = polynomialRegressionPrediction(dailyAverageWaterLevelFinal);
                }

                // copy for optimistic
                ArrayList<Double> dailyAverageWaterLevelFinalCopyForOptimistic = new ArrayList<>(realisticWaterLevelPredictions);
                // copy for conservative
                ArrayList<Double> dailyAverageWaterLevelFinalCopyForConservative = new ArrayList<>(realisticWaterLevelPredictions);
                // optimistic adjustment
                optimisticWaterLevelPrediction = accountForError(dailyAverageWaterLevelFinalCopyForOptimistic,dailyAverageWaterLevelErrorFinal,true);
                // account for estimated evaporation
                accountForEvaporation(optimisticWaterLevelPrediction, dailyAverageTemperatureFinal);
                // conservative adjustment
                conservativeWaterLevelPrediction = accountForError(dailyAverageWaterLevelFinalCopyForConservative,dailyAverageWaterLevelErrorFinal, false);
                // account for estimated evaporation on conservative predictions for both polynomial and linear regression
                accountForEvaporation(conservativeWaterLevelPrediction, dailyAverageTemperatureFinal);
                // add dates as labels to the dataset
                addLabelDates(labelDatesFinal,labelDatesFinal.get(labelDatesFinal.size()-1));

                return succesfulProjection("Success",
                        "waterlevel",
                        realisticWaterLevelPredictions,
                        optimisticWaterLevelPrediction,
                        conservativeWaterLevelPrediction,
                        labelDatesFinal);
            }
            else
            {
                return failedProjection("Insufficient data to project so far into the future.", "waterlevel");
            }
        }
        else
        {
            return failedProjection("Could not generate projections, insufficient data.","waterlevel" );
        }
    }

    /**
     * Polynomial regression occurs here.
     * The best polynomial degree is chosen based on minimizing the Root Mean Squared Error.
     * Once the best degree is found the model is fitted to the data points and a prediction is made
     *
     * @param dailyAverageWaterLevelFinal: daily average water levels for at least 7 days and at most 30 days
     * @return an arraylist of the average water levels + the newly predicted values
     */
    private ArrayList<Double> polynomialRegressionPrediction(ArrayList<Double> dailyAverageWaterLevelFinal)
    {
        List<Double> trainingSet = dailyAverageWaterLevelFinal.subList(0, dailyAverageWaterLevelFinal.size()-5);
        List<Double> testSet = dailyAverageWaterLevelFinal.subList(dailyAverageWaterLevelFinal.size()-5, dailyAverageWaterLevelFinal.size());

        int degree = determineBestPolynomialDegree(trainingSet, testSet);

        for (int x = 0; x < dailyAverageWaterLevelFinal.size(); x++)
        {
            addDataPoint(x,dailyAverageWaterLevelFinal.get(x));
        }

        setFitter(PolynomialCurveFitter.create(degree));
        setCoefficients(getFitter().fit(getDataPoints().toList()));

        return getPredictionValues(dailyAverageWaterLevelFinal);
    }

    /**
     * Linear regression occurs here.
     * If a certain data set does not contain enough data points to make a prediction linear regression is used.
     *
     * @param dailyAverageWaterLevelFinal:  daily average water levels for at least 7 days and at most 30 days
     * @return an arraylist of the average water levels + the newly predicted values
     */
    private ArrayList<Double> linearRegressionPrediction(ArrayList<Double> dailyAverageWaterLevelFinal)
    {
        for (int x = 0; x < dailyAverageWaterLevelFinal.size(); x++)
        {
            addDataPoint(x,dailyAverageWaterLevelFinal.get(x));
        }

        setFitter(PolynomialCurveFitter.create(1));
        setCoefficients(getFitter().fit(getDataPoints().toList()));

        return getPredictionValues(dailyAverageWaterLevelFinal);
    }

    /**
     * Utility function that populates the predicted values and merges it with the original dataset.
     *
     * @param dailyAverageWaterLevelFinal: average water level over a period n where n >= 7 && n <= 30
     * @return combined predicted values for period m and daily average water level over period n
     */
    @NotNull
    private ArrayList<Double> getPredictionValues(ArrayList<Double> dailyAverageWaterLevelFinal) {
        ArrayList<Double> predictedValues = new ArrayList<>();
        int size = dailyAverageWaterLevelFinal.size();
        double futureValue;
        for (int i = 0; i < getDeviceProjectionRequest().getLength(); i++)
        {
            futureValue = 0;
            for (int j = 0; j < getCoefficients().length; j++)
            {
                futureValue += futureRegressionValue(getCoefficients()[j],size + i, j);
            }
            predictedValues.add(Math.min(futureValue, upperLimit));
        }
        dailyAverageWaterLevelFinal.addAll(predictedValues);

        return dailyAverageWaterLevelFinal;
    }

    /**
     * Helper function that dynamically determines the best degree to use for polynomial fitting.
     * Does this by doing a polynomial fit with degrees 2 - 10 (can increase the bounds, be careful of over fitting)
     * Then it does a prediction for 5 future values, after which the testSet is used for comparison.
     * The polynomial curve with the smallest Root Mean Squared Error(RMSE) is then used for prediction.
     *
     * @param trainingSet: set of n - 5 data points on which to perform polynomial regression
     * @param testSet: set of 5 data points on which predicted values are tested and RMSE is calculated
     * @return the integer value of the best fitting line
     */
    private int determineBestPolynomialDegree(List<Double> trainingSet, List<Double> testSet)
    {
        int bestDegree = 0;
        double RMSE = Double.POSITIVE_INFINITY;

        for (int x = 0; x < trainingSet.size(); x++)
        {
            addDataPoint(x,trainingSet.get(x));
        }

        ArrayList<Double> predictedValues = new ArrayList<>();
        int size = trainingSet.size();
        double futureValue;
        for (int i = lowerPolynomialDegreeLimit; i <= upperPolynomialDegreeLimit; i++)
        {
            setFitter(PolynomialCurveFitter.create(i));
            setCoefficients(getFitter().fit(getDataPoints().toList()));

            for (int j = 0; j < testSet.size(); j++)
            {
                futureValue = 0;
                for (int k = 0; k < getCoefficients().length; k++)
                {
                    futureValue += futureRegressionValue(getCoefficients()[k],size + j, k);
                }
                predictedValues.add(futureValue);
            }

            double newRMSE = calculateRMSE(predictedValues, testSet);
            if (newRMSE < RMSE)
            {
                RMSE = newRMSE;
                bestDegree = i;
            }
            predictedValues.clear();
        }

        return bestDegree;
    }

    /**
     * Helper function to calculate the Root Mean Squared Error(RMSE) for predicted values vs actual values
     * The test and prediction list should have the same length
     *
     * @param predictedValues: list of predicted values
     * @param testSet: list of observed values
     * @return the RMSE
     */
    private double calculateRMSE(ArrayList<Double> predictedValues, List<Double> testSet)
    {
        double error = 0;
        for (int i = 0; i < predictedValues.size(); i++) {
            error += Math.pow(predictedValues.get(i) - testSet.get(i),2);
        }
        error /= predictedValues.size();

        return Math.sqrt(error);
    }

    /**
     * Function that takes into account the predicted temperature and rate of evaporation.
     * @param predictedDailyAverageWaterLevelFinal: array to mutate and adjust according to evaporation rate
     * @param dailyAverageTemperatureFinal: the future temperatures to take into account when calculating evaporation
     */
    private void accountForEvaporation(ArrayList<Double> predictedDailyAverageWaterLevelFinal,
                                       ArrayList<Double> dailyAverageTemperatureFinal)
    {
        int startIndex = predictedDailyAverageWaterLevelFinal.size() - getDeviceProjectionRequest().getLength();
        int size = getDeviceProjectionRequest().getLength();

        double adjustedWaterLevel;
        for (int i = startIndex; i < size; i++)
        {
            adjustedWaterLevel = evaporationCalc(predictedDailyAverageWaterLevelFinal.get(i),dailyAverageTemperatureFinal.get(i));
            predictedDailyAverageWaterLevelFinal.set(i,adjustedWaterLevel);
        }
    }

    /**
     * Function that takes into account the error in the measurement, calculated with the Kalman filter.
     * Has 2 variations an optimistic or conservative variation.
     *
     * Optimistic: being that we underestimate the actual water level(add).
     * Conservative: being we overestimate the actual water level(subtract)
     *
     * @param dailyAverageWaterLevelFinal: array to mutate
     * @param dailyAverageWaterLevelErrorFinal: error to mutate with
     * @param isOptimistic: boolean on how to mutate the value
     * @return mutated arraylist with the adjusted values
     */
    private ArrayList<Double> accountForError(ArrayList<Double> dailyAverageWaterLevelFinal,
                                              ArrayList<Double> dailyAverageWaterLevelErrorFinal,
                                              boolean isOptimistic)

    {
        int size = dailyAverageWaterLevelFinal.size() - getDeviceProjectionRequest().getLength();
        double averageError = getAverageError(dailyAverageWaterLevelErrorFinal);

        double adjustedPredictionValue;
        if (isOptimistic)
        {
            for (int i = size; i < dailyAverageWaterLevelFinal.size(); i++)
            {
                adjustedPredictionValue = dailyAverageWaterLevelFinal.get(i);
                adjustedPredictionValue += averageError;
                dailyAverageWaterLevelFinal.set(i,Math.abs(adjustedPredictionValue));
            }
        }
        else
        {
            for (int i = size; i < dailyAverageWaterLevelFinal.size(); i++)
            {
                adjustedPredictionValue = dailyAverageWaterLevelFinal.get(i);
                adjustedPredictionValue -= averageError;
                dailyAverageWaterLevelFinal.set(i,Math.abs(adjustedPredictionValue));
            }
        }
        return dailyAverageWaterLevelFinal;
    }

    /**
     * Utility function that populates the passed in array with the future temperatures for up to a week
     * after which it varies the temperature based on the last known predicted value if someone wants a
     * longer term prediction.
     *
     * @param dailyAverageTemperaturesFinal: array to populate with future temperatures
     * @return array with future temperatures, length of the predicted time periodd
     */
    private ArrayList<Double> getFutureTemperatures(ArrayList<Double> dailyAverageTemperaturesFinal)
    {
        String rawWeatherResult = getWeatherDataApiCall();
        int length = getDeviceProjectionRequest().getLength();

        if (rawWeatherResult.equals(""))
        {
            if (Math.random() > 0.5) {
                return populateFutureAmbientTemperature(dailyAverageTemperaturesFinal, true);
            }
            else
            {
                return populateFutureAmbientTemperature(dailyAverageTemperaturesFinal, false);
            }
        }
        else
        {
            ArrayList<Double> futureAmbientTemperature = new ArrayList<>();
            ObjectMapper tempMapper = new ObjectMapper();
            double temperature = 0;
            try
            {
                JsonNode rootNode = tempMapper.readTree(rawWeatherResult);
                for (int i = 1; i < length; i++)
                {
                    if (i < 8)
                    {
                        temperature = rootNode.path("daily").get(i).path("temp").path("day").asDouble();
                    }
                    futureAmbientTemperature.add(temperature);
                }
            } catch (JsonProcessingException e)
            {
                e.printStackTrace();
                return futureAmbientTemperature;
            }
            return futureAmbientTemperature;
        }
    }

    /**
     * Function that returns a json string of weather data for 7 days.
     * Retrieves this data from an url specified in the application.properties file
     *
     * @return json string
     */
    private String getWeatherDataApiCall()
    {
        double latitude = getWaterSiteByDeviceResponse().getWaterSite().getLatitude();
        double longitude = getWaterSiteByDeviceResponse().getWaterSite().getLongitude();

        StringBuilder customWeatherUrl = new StringBuilder(weatherUrl);

        customWeatherUrl.replace(customWeatherUrl.indexOf("{lat}"),
                findEndIndex(customWeatherUrl.toString(), customWeatherUrl.indexOf("{lat}"),"{lat}"),
                String.valueOf(latitude));

        customWeatherUrl.replace(customWeatherUrl.indexOf("{long}"),
                findEndIndex(customWeatherUrl.toString(), customWeatherUrl.indexOf("{long}"),"{long}"),
                String.valueOf(longitude));

        customWeatherUrl.replace(customWeatherUrl.indexOf("{API_KEY}"),
                findEndIndex(customWeatherUrl.toString(), customWeatherUrl.indexOf("{API_KEY}"),"{API_KEY}"),
                apikey);

        URIBuilder uriBuilder;
        String rawWeatherResult = "";
        try
        {
            uriBuilder = new URIBuilder(customWeatherUrl.toString());
            HttpGet getRequest = new HttpGet(uriBuilder.build());
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                rawWeatherResult = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                response.close();
            }
            response.close();
        }
        catch (URISyntaxException | IOException e)
        {
            e.printStackTrace();
            return "";
        }

        return rawWeatherResult;
    }

    /**
     * Function that calculates the evaporation rate based on the current water level and temperature
     *
     * @param waterLevel: current predicted water level
     * @param futureAmbientTemperature: current predicted temperature
     * @return new future water level after evaporation has been taken into account
     */
    private double evaporationCalc(double waterLevel, double futureAmbientTemperature)
    {
        if (getWaterSite().getShape().equals("circle") && getWaterSite().getRadius() > 0)
        {
            double surfaceArea = Math.PI * Math.pow(getWaterSite().getRadius(), 2.0);
            double diameter = getWaterSite().getRadius() * 2.0;
            double characteristicLength = diameter / 4.0;

            double evaporationRateCircle = rateOfEvaporationCalc(futureAmbientTemperature, surfaceArea, characteristicLength);

            double currWaterWeight = calculateCurrentWaterWeight(getWaterSite(), waterLevel);
            double deltaWaterWeight = currWaterWeight - Math.abs(evaporationRateCircle);
            return calculateFutureWaterLevel(getWaterSite(),deltaWaterWeight);
        }
        else if (getWaterSite().getShape().equals("rectangle") && getWaterSite().getLength() > 0 && getWaterSite().getWidth() > 0)
        {
            double surfaceArea = getWaterSite().getLength() * getWaterSite().getWidth();
            double perimeter = (getWaterSite().getLength() + getWaterSite().getWidth()) * 2.0;
            double characteristicLength = surfaceArea / perimeter;

            double evaporationRateRectangle = rateOfEvaporationCalc(futureAmbientTemperature, surfaceArea, characteristicLength);

            double currWaterWeight = calculateCurrentWaterWeight(getWaterSite(), waterLevel);
            double deltaWaterWeight = currWaterWeight - Math.abs(evaporationRateRectangle);
            return calculateFutureWaterLevel(getWaterSite(),deltaWaterWeight);
        }
        return 0;
    }

    /**
     * Rate of evaporation calculation takes into account environmental constants which are associated
     * with the rate of evaporation of liquid at 1 bar atmospheric pressure.
     *
     * @param futureAmbientTemperature: the forecasted ambient temperature
     * @param surfaceArea: surface area of the water source
     * @param characteristicLength: effective length of surface area
     * @return rate of evaporation of water in kg/day
     */
    private double rateOfEvaporationCalc(Double futureAmbientTemperature,
                                         double surfaceArea,
                                         double characteristicLength)
    {
        double PR;
        double K;
        double kinematicViscosity;
        ArrayList<Double> PRandKandViscosity = determinePRandKandViscosity(futureAmbientTemperature);
        PR = PRandKandViscosity.get(0);
        K = PRandKandViscosity.get(1);
        kinematicViscosity = PRandKandViscosity.get(2);
        double rayleighNumber = ((getGravity() * getBetaValue())
                * Math.abs(getSurfaceTemperature() - futureAmbientTemperature)
                * Math.pow(characteristicLength, 3))
                / Math.pow(kinematicViscosity, 2)
                * PR;
        double nusseltNumber = 0.15 * Math.cbrt(rayleighNumber);
        double heatTransferCoefficient = (K * nusseltNumber) / characteristicLength;
        double heatTransferRate = heatTransferCoefficient * (surfaceArea - (futureAmbientTemperature));
        double massFlowRate = heatTransferRate / getHeatOfVaporization();
        double secondsInDay = 24 * 60 * 60;
        return massFlowRate * secondsInDay;
    }

    /**
     * Utility function that adds the future dates to the labels array to make it easier to process on the
     * front end
     * @param labelDatesFinal: array that to which future dates are added
     * @param currDate: current date of last measurement
     */
    private void addLabelDates(ArrayList<String> labelDatesFinal, String currDate) {
        int year = Integer.parseInt(currDate.substring(0,4));
        int month = Integer.parseInt(currDate.substring(5,7)) - 1;
        int dayOfMonth = Integer.parseInt(currDate.substring(8));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        for (int i = 0; i < getDeviceProjectionRequest().getLength(); i++) {
            calendar.add(Calendar.DATE,1);
            labelDatesFinal.add(dateFormat.format(calendar.getTime()));
        }
    }

    /**
     * Utility function that averages the error in the device's measurements over a certain period
     * @param dailyAverageWaterLevelErrorFinal: array of error in measurements
     * @return the average error over a certain period of time
     */
    private Double getAverageError(ArrayList<Double>  dailyAverageWaterLevelErrorFinal)
    {
        int size = dailyAverageWaterLevelErrorFinal.size();
        Double averageError = 0.0;
        for (Double measurementError :
                dailyAverageWaterLevelErrorFinal)
        {
            averageError += measurementError;
        }
        return averageError / size;
    }

    /**
     * Utility function that takes in a regression coefficient, timePeriod i.e. x-value(independant variable
     * and the exponent of the term.
     *
     * @param coefficient: coefficient of the linear or polynomial function
     * @param timePeriod the time period in the future e.g. we have today's date so to get the future date use today's date + 1
     * @param exponent: exponent of the term(ties in with the degree of the function)
     * @return returns a double value that is used to predict the future regression value
     */
    private double futureRegressionValue(double coefficient, int timePeriod, int exponent)
    {
        return coefficient * Math.pow(timePeriod,exponent);
    }

    /**
     * Calculates the current water weight,in kilograms, based on the height of the water in the "container"
     * and the surface area of the "container".
     *
     * @param waterSite the water site this specific IoT device monitors
     * @param currentWaterlevel the current water level of the water site monitored by this specific IoT device.
     * @return new weight of the water after evaporation has occurred
     */
    public double calculateCurrentWaterWeight(WaterSite waterSite, double currentWaterlevel)
    {
        if (waterSite.getShape().equals("circle"))
        {
            return (Math.PI * Math.pow(waterSite.getRadius(),2) * currentWaterlevel) * 10;
        }
        else if (waterSite.getShape().equals("rectangle"))
        {
            return waterSite.getLength() * waterSite.getWidth() * currentWaterlevel;
        }
        return 0;
    }

    /**
     * Converts the predicted water level in kilograms back to our current unit of measurement,
     * centimeters.
     *
     * @param waterSite the water site this specific IoT device monitors
     * @param waterWeight current weight of the water.
     * @return new water level in cm
     */
    public double calculateFutureWaterLevel(WaterSite waterSite, double waterWeight)
    {
        if (waterSite.getShape().equals("circle"))
        {
            return waterWeight / (Math.PI * Math.pow(waterSite.getRadius(), 2) * 10);
        }
        else if (waterSite.getShape().equals("rectangle"))
        {
            return waterWeight / (waterSite.getLength() * waterSite.getWidth());
        }
        return 0;
    }

    /**
     * Redundancy function in case the weather api does not work, will populate
     * futureAmbientTemperatures based on previously logged temperatures.
     *
     * @param dailyAverageTemperatures array to which to add the future temperatures
     * @param optimistic does the temperature go up(!optimistic), hence increasing evaporation or down(optimistic) decreasing evaporation
     * @return new future temperatures
     */
    private ArrayList<Double> populateFutureAmbientTemperature(ArrayList<Double> dailyAverageTemperatures, boolean optimistic)
    {
        ArrayList<Double> futureAmbientTemperatures = new ArrayList<>();
        for (int i = 0; i < getDeviceProjectionRequest().getLength(); i++)
        {
            if (Math.random() < 0.3)
            {
                if (optimistic)
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) - 2);
                else
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) + 2);
            }
            else if (Math.random() >= 0.3 && Math.random() < 0.6)
            {
                if (optimistic)
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) - 1);
                else
                    futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1) + 1);
            }
            else
            {
                futureAmbientTemperatures.add(dailyAverageTemperatures.get(dailyAverageTemperatures.size() - 1));
            }
        }
        return futureAmbientTemperatures;
    }

    /**
     * Utility function to determine the PR, K and Viscosity values.
     * These values are constants, determined through evaporation experimentation at 1 bar of
     * atmospheric air pressure and influence the rate of evaporation.
     *
     * @param ambientTemp temperature on the day
     * @return arraylist of PrandtlNumber, ThermalConductivity, Viscosity
     */
    private ArrayList<Double> determinePRandKandViscosity(Double ambientTemp)
    {
        ArrayList<Double> PRandK = new ArrayList<>();
        if (ambientTemp >= 5 && ambientTemp < 10)
        {
            PRandK.add(getPrandtlNumbers()[0]);
            PRandK.add(getThermalConductivity()[0]);
            PRandK.add(getKinematicViscosities()[0]);
        }
        else if (ambientTemp >= 10 && ambientTemp < 15)
        {
            PRandK.add(getPrandtlNumbers()[1]);
            PRandK.add(getThermalConductivity()[1]);
            PRandK.add(getKinematicViscosities()[1]);
        }
        else if (ambientTemp >= 15 && ambientTemp < 20)
        {
            PRandK.add(getPrandtlNumbers()[2]);
            PRandK.add(getThermalConductivity()[2]);
            PRandK.add(getKinematicViscosities()[2]);
        }
        else if (ambientTemp >= 20 && ambientTemp < 25)
        {
            PRandK.add(getPrandtlNumbers()[3]);
            PRandK.add(getThermalConductivity()[3]);
            PRandK.add(getKinematicViscosities()[3]);
        }
        else if (ambientTemp >= 25 && ambientTemp < 30)
        {
            PRandK.add(getPrandtlNumbers()[4]);
            PRandK.add(getThermalConductivity()[4]);
            PRandK.add(getKinematicViscosities()[4]);
        }
        else if (ambientTemp >= 30 && ambientTemp < 35)
        {
            PRandK.add(getPrandtlNumbers()[5]);
            PRandK.add(getThermalConductivity()[5]);
            PRandK.add(getKinematicViscosities()[5]);
        }
        else if (ambientTemp >= 35 && ambientTemp < 40)
        {
            PRandK.add(getPrandtlNumbers()[6]);
            PRandK.add(getThermalConductivity()[6]);
            PRandK.add(getKinematicViscosities()[6]);
        }
        else
        {
            PRandK.add(getPrandtlNumbers()[7]);
            PRandK.add(getThermalConductivity()[7]);
            PRandK.add(getKinematicViscosities()[7]);
        }
        return PRandK;
    }

    /**
     * Utility function to locate the last position/index of a substring in a string
     *
     * @param original string to search
     * @param indexOf starting position of the search
     * @param substring what we're searching for
     * @return the position of the last/end occurrence of the substring e.g. search for "le" in "apple" returns the index of the e
     */
    private int findEndIndex(String original ,int indexOf, String substring)
    {
        char[] originalCharArray = original.toCharArray();
        for (int i = indexOf; i < originalCharArray.length; i++)
        {
            if (originalCharArray[i] == substring.charAt(substring.length() - 1))
            {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Getters for the environmental variables used in the evaporation rate calculation.
     */
    public double getGravity()
    {
        return gravity;
    }

    public double getBetaValue()
    {
        return betaValue;
    }

    public double getSurfaceTemperature()
    {
        return surfaceTemperature;
    }

    public double getHeatOfVaporization()
    {
        return heatOfVaporization;
    }

    public double[] getPrandtlNumbers()
    {
        return prandtlNumbers;
    }

    public double[] getThermalConductivity()
    {
        return thermalConductivity;
    }

    public double[] getKinematicViscosities()
    {
        return kinematicViscosities;
    }
}
