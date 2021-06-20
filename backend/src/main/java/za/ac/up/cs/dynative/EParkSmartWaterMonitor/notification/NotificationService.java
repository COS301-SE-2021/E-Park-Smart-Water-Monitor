package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification;

import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.SMSResponse;

public interface NotificationService {
    public EmailResponse sendMail(EmailRequest eMailRequest) throws InvalidRequestException;
    public SMSResponse sendSMS(SMSRequest smsRequest) throws InvalidRequestException;
}
