package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class CreateAlarmRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("alarm_sid")
    private String alarm_sid;

    @JsonProperty("definition")
    private AlarmDefinition definition;

    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public String getAlarmSid() {
        return alarm_sid;
    }

    @JsonIgnore
    public void setAlarmSid(String alarm_sid) {
        this.alarm_sid = alarm_sid;
    }

    public AlarmDefinition getDefinition() {
        return definition;
    }

    @JsonIgnore
    public void setDefinition(AlarmDefinition definition) {
        this.definition = definition;
    }

}
