
package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;


public class AlarmListResponse {
    @JsonProperty("alarms")
    private List<Map> alarms;

    public List<Map> getAlarms() {
        return alarms;
    }
 
}
