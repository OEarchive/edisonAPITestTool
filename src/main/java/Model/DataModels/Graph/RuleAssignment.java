package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RuleAssignment {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("associationId")
    private String associationId;

    @JsonProperty("pointNames")
    private List<String> pointNames;

    @JsonProperty("lastExecutionTimestamp")
    private String lastExecutionTimestamp;

    @JsonProperty("lastSuccessTimestamp")
    private String lastSuccessTimestamp;

    @JsonProperty("lastFailureTimestamp")
    private String lastFailureTimestamp;

    @JsonProperty("lastExecutionTime")
    private int lastExecutionTime;

    @JsonProperty("lastExecutionMsg")
    private String lastExecutionMsg;

    public String getSid() {
        return sid;
    }

    public String getAssociationId() {
        return associationId;
    }

    public List<String> getPointNames() {
        return pointNames;
    }

    public String getLastExecutionTimestamp() {
        return lastExecutionTimestamp;
    }

    public String getLastSuccessTimestamp() {
        return lastSuccessTimestamp;
    }

    public String getLastFailureTimestamp() {
        return lastFailureTimestamp;
    }

    public int getLastExecutionTime() {
        return lastExecutionTime;
    }

    public String getLastExecutionMsg() {
        return lastExecutionMsg;
    }

}
