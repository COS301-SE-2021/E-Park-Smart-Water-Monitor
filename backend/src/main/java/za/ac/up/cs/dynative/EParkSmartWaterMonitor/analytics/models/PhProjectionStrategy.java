package za.ac.up.cs.dynative.EParkSmartWaterMonitor.analytics.models;


import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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

public class PhProjectionStrategy extends AbstractProjectionStrategy
{

    private final DeviceProjectionRequest deviceProjectionRequest;
    private final GetDeviceDataResponse deviceDataResponse;
    private final String arimaUrl;

    public PhProjectionStrategy(DeviceProjectionRequest deviceProjectionRequest,
                                GetDeviceDataResponse deviceDataResponse,
                                FindWaterSiteByDeviceResponse waterSiteByDeviceResponse,
                                String arimaUrl)
    {
        super(deviceProjectionRequest,deviceDataResponse,waterSiteByDeviceResponse);
        this.deviceProjectionRequest = deviceProjectionRequest;
        this.deviceDataResponse = deviceDataResponse;
        this.arimaUrl = arimaUrl;
    }

    public DeviceProjectionRequest getDeviceProjectionRequest() {
        return deviceProjectionRequest;
    }

    @Override
    public DeviceProjectionResponse predict()
    {
        if (getDeviceProjectionRequest().getLength() != 5) {
            return failedProjection("Invalid projection period. Period must be 5!", "ph");
        }
        else {
            Map<String, List<Measurement>> groupedPhMeasurements;
            ArrayList<Double> dailyAveragePhMeasurements = new ArrayList<>();
            ArrayList<String> labelDates = new ArrayList<>();

            ArrayList<Measurement> phMeasurements = extractData("WATER_QUALITY");
            groupedPhMeasurements = phMeasurements.stream().collect(Collectors.groupingBy(Measurement::getDeviceDate));
            groupedPhMeasurements.forEach((key, value) -> {
                dailyAveragePhMeasurements.add(average(value,false) / 10 + 3);
                labelDates.add(value.get(0).getDeviceDateTime().substring(0,10));
            });

            ArrayList<String> labelDatesFinal = labelDates;
            ArrayList<Double> dailyAveragePhFinal = dailyAveragePhMeasurements;

            if (labelDates.size() > 30)
            {
                labelDatesFinal = (ArrayList<String>) labelDates.subList(0, 30);
            }
            if (dailyAveragePhMeasurements.size() > 30)
            {
                dailyAveragePhFinal = (ArrayList<Double>) dailyAveragePhMeasurements.subList(0,30);
            }
            labelDates.sort(String::compareTo);

            ArrayList<Double> phPedictions;
            try
            {
                phPedictions = getArimaPrediction(dailyAveragePhFinal);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return failedProjection("Couldn't generate a prediction","ph");
            }
            addLabelDates(labelDatesFinal,labelDatesFinal.get(labelDatesFinal.size()-1));


            return new DeviceProjectionResponse(
                    "PH",
                    true,
                    "ph",
                    deviceProjectionRequest.getLength(),
                    null,
                    phPedictions,
                    null,
                    labelDates);
        }
    }

    private ArrayList<Double> getArimaPrediction(ArrayList<Double> thirtyDayDailyAveragePhLevel) throws IOException
    {
        String phLevels = thirtyDayDailyAveragePhLevel.toString().replaceAll("\\[+|]+","");

        phLevels = sendPost(phLevels);

        if (phLevels.equals("")) {
            return null;
        }
        return processModelOutput(phLevels.trim());
    }

    private String sendPost(String phLevels) throws IOException
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

    private ArrayList<Double> processModelOutput(String predictions)
    {
        predictions = predictions.replaceAll("\\[+|]+|'","");
        predictions = predictions.replaceAll(",,",",");
        String[] predictedStringValues = predictions.split(",");

        ArrayList<Double> predictionVals = new ArrayList<>();
        for (String value :
                predictedStringValues)
        {
            predictionVals.add(Double.parseDouble(value));
        }
        return predictionVals;
    }
}
