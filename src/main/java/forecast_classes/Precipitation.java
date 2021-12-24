package forecast_classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Precipitation {
    @JsonProperty("Icon")
    private int icon;
    @JsonProperty("IconPhrase")
    private String iconPhrase;
    @JsonProperty("HasPrecipitation")
    private boolean hasPrecipitation;

    public int getIcon() {
        return icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public boolean isHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public void setHasPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }
}