
package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Alarm {
    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("state")
    private String state;

    @JsonProperty("lastReceivedTimestamp")
    private String lastReceivedTimestamp;


    public String getAlarmSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getLastReceivedTimestamp() {
        return lastReceivedTimestamp;
    }

}
