package Model.DataModels.Live.PostLiveData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PostLiveDataRequest {

    @JsonProperty("sendingSid")
    private String sendingSid;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("data")
    private List<PostLiveDataRequestData> data;

    public String getSendingSid() {
        return sendingSid;
    }

    @JsonIgnore
    public void setSendingSid(String sendingSid) {
        this.sendingSid = sendingSid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<PostLiveDataRequestData> getData() {
        return data;
    }

    @JsonIgnore
    public void setData(List<PostLiveDataRequestData> data) {
        this.data = data;
    }

}
