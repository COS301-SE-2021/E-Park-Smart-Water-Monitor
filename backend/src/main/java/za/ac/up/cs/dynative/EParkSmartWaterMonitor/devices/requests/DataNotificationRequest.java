package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.DataNotificationQuery;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.WaterSourceData;

import java.util.List;

public class DataNotificationRequest {

    List<DataNotificationQuery> data;

    public DataNotificationRequest(List<DataNotificationQuery> incomingData)
    {
        data=incomingData;
    }

    public List<DataNotificationQuery> getData() {
        return data;
    }

    public void setData(List<DataNotificationQuery> data) {
        this.data = data;
    }
}
