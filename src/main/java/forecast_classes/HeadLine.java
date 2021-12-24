package forecast_classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadLine {
    @JsonProperty("EffectiveDate")
    private String effectiveDate;
    @JsonProperty("EffectiveEpochDate")
    private long effectiveEpochDate;
    @JsonProperty("Severity")
    private int severity;
    @JsonProperty("Text")
    private String text;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("EndEpochDate")
    private String endEpochDate;

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public long getEffectiveEpochDate() {
        return effectiveEpochDate;
    }

    public int getSeverity() {
        return severity;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndEpochDate() {
        return endEpochDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setEffectiveEpochDate(long effectiveEpochDate) {
        this.effectiveEpochDate = effectiveEpochDate;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setEndEpochDate(String endEpochDate) {
        this.endEpochDate = endEpochDate;
    }
}