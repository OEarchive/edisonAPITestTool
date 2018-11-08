package Model.DataModels.Views;

import Model.DataModels.Views.Point;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PointGroup {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String typeName;

    @JsonProperty("points")
    private List<Point> points;

    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<Point> getPoints() {
        return points;
    }

}
