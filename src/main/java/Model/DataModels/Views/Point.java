package Model.DataModels.Views;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {

    //TODO - which of these fields are really being returned?
    @JsonProperty("label")
    private String label;

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("name")
    private String name;
    
    @JsonProperty("queryName")
    private String queryName;

    @JsonProperty("stationName")
    private String stationName;

    @JsonProperty("uom")
    private String uom;

    @JsonProperty("measure")
    private String measure;

    @JsonProperty("live")
    private Boolean live;

    public String getName() {
        return name;
    }
    

    
    public String getLabel() {
        return label;
    }
    
    public String getSid(){
        return sid;
    }
    
    
    public String getQueryName(){
        return queryName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getUOM() {
        return uom;
    }

    public String getMeasure() {
        return measure;
    }

    public Boolean getLive() {
        return live;
    }

}
