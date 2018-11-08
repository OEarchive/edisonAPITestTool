package Model.DataModels.Sites.SiteDPConfig;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class SiteDPConfigPoints {

    @JsonProperty("communicationProtocol")
    private String communicationProtocol;

    @JsonProperty("groups")
    private Map<String,List<SiteDPConfigPoint>> groups;

    @JsonProperty("lastUpdated")
    private String lastUpdated;

    public String getCommProtocol() {
        return communicationProtocol;
    }

    public Map<String,List<SiteDPConfigPoint>> getGroups() {
        return groups;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

}
