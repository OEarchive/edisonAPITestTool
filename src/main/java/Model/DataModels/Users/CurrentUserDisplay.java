package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentUserDisplay {

    @JsonProperty("units")
    private String units;

    @JsonProperty("date_format")
    private String date_format;

    public String getUnits() {
        return this.units;
    }

    @JsonIgnore
    public void setUnits(String units) {
        this.units = units;
    }

    public String getDateFormat() {
        return this.date_format;
    }

    @JsonIgnore
    public void setDateFormat(String date_format) {
        this.date_format = date_format;
    }
}
