package Model.DataModels.Sites.SiteDatapoints;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteDatapoint {

    @JsonProperty("live")
    private Boolean live;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private Object pointValue;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("measure")
    private String measure;

    @JsonProperty("uom")
    private String uom;
    
    public Boolean getLive(){
        return live;
    }

    public String getUom() {
        return uom;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return pointValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSid() {
        return sid;
    }
}
