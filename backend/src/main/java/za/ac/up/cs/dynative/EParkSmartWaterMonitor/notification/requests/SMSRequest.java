package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SMSRequest
{
    private ArrayList<User> recipients;
    private List<UUID> userIds;
    private String message;
    @JsonCreator
    public SMSRequest(List<UUID> userIds , String message)
    {
        this.userIds=userIds;
        this.message=message;
    }

    public SMSRequest(ArrayList<User> recipients , String message)
    {
        this.recipients=recipients;
        this.message=message;
    }
    public List<UUID> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UUID> userIds) {
        this.userIds = userIds;
    }


    public ArrayList<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<User> recipients) {
        this.recipients = recipients;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
