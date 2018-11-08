package Model.DataModels.Users;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Users {

    @JsonProperty("users")
    private List<UserInfo> users;

    public List<UserInfo> getUsers() {
        return this.users;
    }

}
