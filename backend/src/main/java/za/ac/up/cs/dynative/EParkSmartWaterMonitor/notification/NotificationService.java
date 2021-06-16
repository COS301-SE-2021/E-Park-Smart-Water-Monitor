package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;

public interface NotificationService {
    public EmailResponse sendMail(EmailRequest eMailRequest);
}
