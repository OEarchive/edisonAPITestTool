package Model.DataModels.Stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class StationAuditHistory {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("sendingSid")
    private String sendingSid;

    @JsonProperty("lastSuccessTimestamp")
    private String lastSuccessTimestamp;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("historyType")
    private String historyType;

    @JsonProperty("data")
    private List<StationAuditData> data;

    public String getSid() {
        return sid;
    }

    @JsonIgnore
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSendingSid() {
        return sendingSid;
    }

    @JsonIgnore
    public void setSendingSid(String sendingSid) {
        this.sendingSid = sendingSid;
    }

    public String getLastSuccessTimestamp() {
        return lastSuccessTimestamp;
    }

    @JsonIgnore
    public void setLastSuccessTimestamp(String lastSuccessTimestamp) {
        this.lastSuccessTimestamp = lastSuccessTimestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHistoryType() {
        return historyType;
    }

    @JsonIgnore
    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public List<StationAuditData>  getData() {
        return data;
    }

    @JsonIgnore
    public void setData(List<StationAuditData>  data) {
        this.data = data;
    }

}
