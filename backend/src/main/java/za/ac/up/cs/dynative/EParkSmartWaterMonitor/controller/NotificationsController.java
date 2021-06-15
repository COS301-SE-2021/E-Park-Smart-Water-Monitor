package za.ac.up.cs.dynative.EParkSmartWaterMonitor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.NotificationService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EMailRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/notifications")
public class NotificationsController
{

    NotificationService notificationService;

    @Autowired
    NotificationsController(@Qualifier("NotificationServiceImpl") NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @PostMapping("/mail")
    public ResponseEntity<Object> sendMail(@RequestBody EMailRequest eMailRequest) {
//        return new ResponseEntity<>(notificationService.sendMail(eMailRequest), HttpStatus.OK);
        return null;
    }
}
