package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;

/**
 * This class will map all the http requests made related to the notifications to their
 * respected functions in the notification service implementation.
 * The http methods that will be used: post.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    /**
     * The notificationService will be used to call functions in the notifications service implementation.
     */
    NotificationService notificationService;

    /**
     * This constructor will set the notificationService variable defined previously so that it is not of null value
     * @param notificationService will be set and used across the rest of this class.
     */
    @Autowired
    NotificationsController(@Qualifier("NotificationServiceImpl") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * This post request will be used to send an email.
     * @param eMailRequest object contains all the details for sending an email (from, subject, to, cc's, bcc's, body, description, entity and the topic.)
     * @return An EmailResponse object is returned with the success value and status.
     */
    @PostMapping("/mail")
    public ResponseEntity<Object> sendMail(@RequestBody EmailRequest eMailRequest) throws InvalidRequestException {
        return new ResponseEntity<>(notificationService.sendMail(eMailRequest), HttpStatus.OK);
    }

    /**
     * This post request will be used to send a sms.
     * @param smsRequest will contain the recipients, their user id's and the message.
     * @return An SMSResponse object is returned with the success value and status.
     */
    @PostMapping("/sms")
    public  ResponseEntity<Object> sendSMS(@RequestBody SMSRequest smsRequest) throws InvalidRequestException {
        return new ResponseEntity<>(notificationService.sendSMS(smsRequest), HttpStatus.OK);
    }

}
