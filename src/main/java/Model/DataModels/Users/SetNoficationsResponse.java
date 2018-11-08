package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetNoficationsResponse {

    @JsonProperty("message")
    private String message;

    public String getMessage() {
        return message;
    }

}
