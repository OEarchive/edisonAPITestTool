
package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckForUpdatesResponse {

    @JsonProperty("message")
    private String message;
    
    @JsonProperty("timestamp")
    private String timestamp;

    public String getMessage() {
        return this.message;
    }
    
    public String getTimestamp() {
        return this.timestamp;
    }

}
