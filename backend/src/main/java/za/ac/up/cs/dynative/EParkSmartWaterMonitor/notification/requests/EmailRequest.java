package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;

import java.util.ArrayList;

public class EmailRequest
{
    private String from ;
    private String subject  ;
    private java.util.ArrayList<String> toAddresses ;
    private ArrayList<String> ccAddresses   ;
    private ArrayList<String> bccAddresses  ;
    private String body ;
    private Topic topic;

    public EmailRequest(@JsonProperty("from") String from,
                        @JsonProperty("subject")String subject,
                        @JsonProperty("toAddresses")ArrayList<String> toAddresses,
                        @JsonProperty("ccAddresses")ArrayList<String> ccAddresses,
                        @JsonProperty("bccAddresses")ArrayList<String> bccAddresses,
                        @JsonProperty("body")String body,
                        @JsonProperty("topic")Topic topic)
    {
        this.from = from;
        this.subject = subject;
        this.toAddresses = toAddresses;
        this.ccAddresses = ccAddresses;
        this.bccAddresses = bccAddresses;
        this.body = body;
        this.topic = topic;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<String> getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(ArrayList<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public ArrayList<String> getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(ArrayList<String> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public ArrayList<String> getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(ArrayList<String> bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
