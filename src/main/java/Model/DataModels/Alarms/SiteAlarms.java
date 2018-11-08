package Model.DataModels.Alarms;

import Model.DataModels.Alarms.SiteAlarm;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SiteAlarms {

    @JsonProperty("alarms")
    private List<SiteAlarm> alarms;

    public List<SiteAlarm> getAlarms() {
        return alarms;
    }

}
