package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.requests.DeviceProjectionRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.responses.DeviceProjectionResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.Measurement;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceDataResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.responses.GetDeviceInnerResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.models.WaterSite;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.watersite.responses.FindWaterSiteByDeviceResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class AbstractProjectionStrategy implements ProjectionStrategyInterface
{
    /**
     * General projection variables
     */
    private final DeviceProjectionRequest deviceProjectionRequest;
    private final GetDeviceDataResponse deviceDataResponse;
    private final FindWaterSiteByDeviceResponse waterSiteByDeviceResponse;

    /**
     * Regression variables
     */
    private WeightedObservedPoints dataPoints;
    private PolynomialCurveFitter fitter;
    private double[] coefficients;

    /**
     * AbstractProjectionStrategy constructor.
     * @param deviceProjectionRequest request containing the length, type and deviceId
     * @param deviceDataResponse latest data from requested device
     * @param waterSiteByDeviceResponse water site monitored by the specified device
     */
    public AbstractProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest,
                                      GetDeviceDataResponse deviceDataResponse,
                                      FindWaterSiteByDeviceResponse waterSiteByDeviceResponse)
    {
        this.deviceProjectionRequest = deviceProjectionRequest;
        this.deviceDataResponse = deviceDataResponse;
        this.waterSiteByDeviceResponse = waterSiteByDeviceResponse;
        this.dataPoints = new WeightedObservedPoints();

    }

    /**
     * Utility function that adds the future dates to the labels array to make it easier to process on the
     * front end
     * @param labelDatesFinal: array to which future dates are added
     * @param currDate: current date of last measurement
     */
    protected void addLabelDates(ArrayList<String> labelDatesFinal, String currDate) {
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
     * Utility function to give custom status message if a projection can't be
     * calculated for some reason.
     */
    public DeviceProjectionResponse failedProjection(String status, String type)
    {
        return new DeviceProjectionResponse(
                status,
                false,
                type,
                getDeviceProjectionRequest().getLength(),
                null,
                null,
                null,
                null);
    }

    /**
     * Helper function to give custom status message if a projection can't be
     * calculated for some reason.
    */
    public DeviceProjectionResponse successfulProjection(String status,
                                                        String type,
                                                        ArrayList<Double> realisticWaterLevelPredictions,
                                                        ArrayList<Double> optimisticWaterLevelPredictions,
                                                        ArrayList<Double> conservativeWaterLevelPredictions,
                                                        ArrayList<String> labelDates)
    {
        return new DeviceProjectionResponse(
                status,
                true,
                type,
                getDeviceProjectionRequest().getLength(),
                optimisticWaterLevelPredictions,
                realisticWaterLevelPredictions,
                conservativeWaterLevelPredictions,
                labelDates);
    }
    /**
     * Helper function to extract the measurements based on the string passed in
     * Options include:
     *   - "WATER_LEVEL"
     *   - "WATER_TEMP"
     *   - "WATER_QUALITY"
     */
    public ArrayList<Measurement> extractData(String dataToExtract)
    {
        ArrayList<Measurement> data = new ArrayList<>();
        for (GetDeviceInnerResponse innerData :
                deviceDataResponse.getInnerResponses())
        {
            for (Measurement measurement :
                    innerData.getMeasurements())
            {
                if (measurement.getType().equals(dataToExtract))
                {
                    data.add(measurement);
                }
            }
        }
        return data;
    }

    /***
     * Helper function to calculate the average of a measurement
     *
     * @param value the list of values
     * @param isError flag to vary what field gets averaged in a measurement, see Measurement.java
     * @return either the averaged error or the average estimated value.
     */
    public double average(List<Measurement> value, boolean isError)
    {
        double total = 0;
        if (value != null)
        {
            for (Measurement m :
                    value)
            {
                if (!isError && m.getValue() > 0) {

                    total += m.getEstimateValue();
                }
                else
                {
                    total += m.getEstimateError();
                }
            }
            return total/value.size();
        }
        else return 0;
    }


    /**
     Getters for the various requests used throughout the prediction process.
     */
    public DeviceProjectionRequest getDeviceProjectionRequest()
    {
        return deviceProjectionRequest;
    }

    public GetDeviceDataResponse getDeviceDataResponse()
    {
        return deviceDataResponse;
    }

    public FindWaterSiteByDeviceResponse getWaterSiteByDeviceResponse()
    {
        return waterSiteByDeviceResponse;
    }

    public WaterSite getWaterSite()
    {
        return getWaterSiteByDeviceResponse().getWaterSite();
    }
    /**
     Getters and setter for the curve fitting variables used for regression
     */
    public WeightedObservedPoints getDataPoints()
    {
        return dataPoints;
    }

    public PolynomialCurveFitter getFitter()
    {
        return fitter;
    }

    public double[] getCoefficients()
    {
        return coefficients;
    }

    public void setCoefficients(double[] coefficients)
    {
        this.coefficients = coefficients;
    }

    public void setDataPoints(WeightedObservedPoints dataPoints)
    {
        this.dataPoints = dataPoints;
    }

    public void addDataPoint(double x, double y)
    {
        this.dataPoints.add(x,y);
    }

    public void setFitter(PolynomialCurveFitter fitter)
    {
        this.fitter = fitter;
    }
}
