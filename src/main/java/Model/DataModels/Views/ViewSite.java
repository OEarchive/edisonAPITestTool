
package Model.DataModels.Views;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class ViewSite {
    
    @JsonProperty("name")
    private String name;

    @JsonProperty("sid")
    private String sid;
    
    @JsonProperty("pointGroups")
    private List<PointGroup> pointGroups;

    @JsonProperty("points")
    private List<Point> points;


    public List<PointGroup> getPointGroups() {
        return pointGroups;
    }

    public List<Point> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public String getSid() {
        return sid;
    }
    
}
