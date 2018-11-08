package Model.DataModels.Graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class EdisonNode {

    @JsonProperty("id")
    private String id;

    @JsonProperty("sid")
    private String sid;
    
    @JsonProperty("customerSid")
    private String customerSid;

    @JsonProperty("nodeType")
    private String nodeType;

    @JsonProperty("name")
    private String name;

    @JsonProperty("properties")
    private Map properties;

    @JsonProperty("edges")
    private List<Map> edges;

    @JsonProperty("alarms")
    private List<Map> alarms;

    @JsonProperty("datapoints")
    private List<Map> datapoints;
    
    @JsonProperty("tags")
    private List<String> tags;

    public String getId() {
        return id;
    }

    public String getSid() {
        return sid;
    }
    
    public String getCustomerSid() {
        return customerSid;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getName() {
        return name;
    }

    public Map getProperties() {
        return properties;
    }

    public List<Map> getAlarms() {
        return alarms;
    }

    public List<Map> getEdges() {
        return edges;
    }

    public List<Map> getDatapoints() {
        return datapoints;
    }
    
    public List<String> getTags() {
        return tags;
    }
}
