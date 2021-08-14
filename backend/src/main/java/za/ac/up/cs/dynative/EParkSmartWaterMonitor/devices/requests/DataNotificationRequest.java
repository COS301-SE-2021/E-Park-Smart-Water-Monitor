package za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.models.DataNotification;

import java.util.List;

public class DataNotificationRequest {

    List<DataNotification> data;

    public DataNotificationRequest(List<DataNotification> incomingData)
    {
        data=incomingData;
    }

    public List<DataNotification> getData() {
        return data;
    }

    public void setData(List<DataNotification> data) {
        this.data = data;
    }
}
