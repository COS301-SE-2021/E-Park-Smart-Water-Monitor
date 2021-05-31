package za.ac.up.cs.dynative.EParkSmartWaterMonitor.reporting.models;

import java.util.Date;
import java.util.UUID;

public class Report {

    private UUID reportId;
    private Date dateTime;

    public Report(UUID reportId, Date dateTime) {
        this.reportId = reportId;
        this.dateTime = dateTime;
    }

    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", dateTime=" + dateTime +
                '}';
    }
}
