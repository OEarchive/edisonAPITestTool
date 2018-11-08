package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GetChildrenResponse {

    @JsonProperty("ctx")
    private String ctx;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("message")
    private String message;

    @JsonProperty("nodes")
    private List<EdisonNode> nodes;

    public String getCtx() {
        return ctx;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public List<EdisonNode> getNodes() {
        return nodes;
    }

}
