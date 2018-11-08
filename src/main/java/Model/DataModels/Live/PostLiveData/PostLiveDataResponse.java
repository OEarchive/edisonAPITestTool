
package Model.DataModels.Live.PostLiveData;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class PostLiveDataResponse {
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("messages")
    private List<PostLiveDataResponseMessage> messages;

    @JsonProperty("timestamp")
    private String timestamp;


    public String getType() {
        return type;
    }

    public List<PostLiveDataResponseMessage> getMessages() {
        return messages;
    }

    public String getTimestamp() {
        return timestamp;
    }
    
}
