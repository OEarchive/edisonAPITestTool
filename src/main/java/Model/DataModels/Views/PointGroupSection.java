
package Model.DataModels.Views;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PointGroupSection {

    @JsonProperty("name")
    private String name;

    @JsonProperty("pointGroups")
    private List<PointGroup> pointGroups;

    public String getName() {
        return name;
    }

    public List<PointGroup> getPointGroups() {
        return pointGroups;
    }

}
