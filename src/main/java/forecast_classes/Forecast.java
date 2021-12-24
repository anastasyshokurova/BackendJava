package forecast_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("EpochDate")
    private String epochDate;
    @JsonProperty("Temperature")
    private DayTemperature temperature;
    @JsonProperty("Day")
    private Precipitation day;
    @JsonProperty("Night")
    private Precipitation night;

    public String getDate() {
        return date;
    }

    public String getEpochDate() {
        return epochDate;
    }

    public DayTemperature getTemperature() {
        return temperature;
    }

    public Precipitation getDay() {
        return day;
    }

    public Precipitation getNight() {
        return night;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEpochDate(String epochDate) {
        this.epochDate = epochDate;
    }

    public void setTemperature(DayTemperature temperature) {
        this.temperature = temperature;
    }

    public void setDay(Precipitation day) {
        this.day = day;
    }

    public void setNight(Precipitation night) {
        this.night = night;
    }
}
