
package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class UpsertElementRequest {
    
    @JsonProperty("sid")
    private String sid;

    @JsonProperty("type")
    private String type;

    //TODO
    @JsonProperty("body")
    private String body;



    public String getSid() {
        return sid;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }


    
}
