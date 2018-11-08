package Model.DataModels.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Permission {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("perms")
    private List<String> perms;

    public String getSid() {
        return this.sid;
    }

    public List<String> getPerms() {
        return this.perms;
    }

}
