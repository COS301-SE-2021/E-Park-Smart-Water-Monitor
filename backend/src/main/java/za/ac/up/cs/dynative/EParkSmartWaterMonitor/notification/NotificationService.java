package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EMailRequest;

public interface NotificationService {
    public void sendMail(EMailRequest eMailRequest);
}
