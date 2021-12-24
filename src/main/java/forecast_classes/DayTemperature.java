package forecast_classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DayTemperature {
    @JsonProperty("Minimum")
    private Temperature minimum;
    @JsonProperty("Maximum")
    private Temperature maximum;

    public Temperature getMinimum() {
        return minimum;
    }

    public Temperature getMaximum() {
        return maximum;
    }

    public void setMinimum(Temperature minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(Temperature maximum) {
        this.maximum = maximum;
    }
}