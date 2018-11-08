
package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class AssociateAlarmReq  {

    @JsonProperty("alarmSid")
    private String alarmSid;

    public String getAlarmSid() {
        return alarmSid;
    }

    @JsonIgnore
    public void setAlarmSid(String alarmSid) {
        this.alarmSid = alarmSid;
    }
    
}