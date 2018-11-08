package Model.DataModels.TotalSavings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TotalSavings {

    @JsonProperty("last_updated")
    private String lastUpdated;

    @JsonProperty("h2o")
    private Map<String,Double> water;

    @JsonProperty("co2")
    private Map<String,Double>  carbon;

    @JsonProperty("kwh")
    private Map<String,Double> kwh;

    @JsonProperty("dollars")
    private Map<String,Double>  dollars;

    public DateTime getLastUpdated() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
        return DateTime.parse(lastUpdated, fmt);
    }

    public double getRateOfChange(EnumTotalSavingsTypes savingsType) {

        switch (savingsType) {
            case H2O:
                return water.get("rate_of_change");
            case CO2:
                return carbon.get("rate_of_change");
            case KWH:
                return kwh.get("rate_of_change");
            case DOLLARS:
                return dollars.get("rate_of_change");
        }
        
        return 0;
    }
    
        public double getValue(EnumTotalSavingsTypes savingsType) {

        switch (savingsType) {
            case H2O:
                return water.get("value");
            case CO2:
                return carbon.get("value");
            case KWH:
                return kwh.get("value");
            case DOLLARS:
                return dollars.get("value");
        }
        
        return 0;
    }

}
