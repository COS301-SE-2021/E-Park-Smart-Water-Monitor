package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.DevicesService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.devices.requests.ReceiveDeviceDataRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;


@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
public class NotificationsController
{

    NotificationService notificationService;

    @Autowired
    NotificationsController(@Qualifier("NotificationServiceImpl") NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    @PostMapping("/mail")
    public ResponseEntity<Object> sendMail(@RequestBody EmailRequest eMailRequest) throws InvalidRequestException {
        return new ResponseEntity<>(notificationService.sendMail(eMailRequest), HttpStatus.OK);
    }

    @PostMapping("/sms")
    public  ResponseEntity<Object> sendSMS(@RequestBody SMSRequest smsRequest) throws InvalidRequestException {
        return new ResponseEntity<>(notificationService.sendSMS(smsRequest), HttpStatus.OK);

    }

}
