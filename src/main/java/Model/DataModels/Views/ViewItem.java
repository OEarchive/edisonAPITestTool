package Model.DataModels.Views;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ViewItem {

    @JsonProperty("pointGroupSections")
    private List<PointGroupSection> pointGroupSections;
    
    @JsonProperty("pointGroups")
    private List<PointGroup> pointGroups;

    @JsonProperty("points")
    private List<Point> points;

    @JsonProperty("name")
    private String name;

    @JsonProperty("stationName")
    private String stationName;

    @JsonProperty("sid")
    private String sid;
    
    

    public List<PointGroupSection> getPointGroupSections() {
        return pointGroupSections;
    }
    
    public List<PointGroup> getPointGroups() {
        return pointGroups;
    }

    public List<Point> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public String getStationName() {
        return stationName;
    }

    public String getSid() {
        return sid;
    }

}
