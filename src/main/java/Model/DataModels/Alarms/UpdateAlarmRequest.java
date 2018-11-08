package Model.DataModels.Alarms;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateAlarmRequest {

    @JsonProperty("isEnabled")
    private boolean isEnabled;

    @JsonProperty("definition")
    private AlarmDefinition definition;

    public boolean getEnabled() {
        return isEnabled;
    }

    @JsonIgnore
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public AlarmDefinition getAlarmDefinition() {
        return definition;
    }

    @JsonIgnore
    public void setAlarmDefinition(AlarmDefinition definition) {
        this.definition = definition;
    }

}
