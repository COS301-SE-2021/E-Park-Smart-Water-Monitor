package za.ac.up.cs.dynative.EParkSmartWaterMonitor.park.models;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Node
public class WeatherData {

    @Id
    @GeneratedValue
    private long id;

    private double temperature;

    private String moonPhase;

    private double humidity;

    private double windSpeed;

    private Date dateTime;


    public WeatherData(int id, double temperature, String moonPhase, double humidity, double windSpeed, Date dateTime) {
        this.id = id;
        this.temperature = temperature;
        this.moonPhase = moonPhase;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.dateTime = dateTime;
    }

    public WeatherData() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", moonPhase='" + moonPhase + '\'' +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", dateTime=" + dateTime +
                '}';
    }
}
