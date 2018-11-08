package Model.DataModels.Datapoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

//not being used

public class DatapointsQueryParams {

    @JsonProperty("start")
    private String start;

    @JsonProperty("end")
    private String end;

    @JsonProperty("resolution")
    private String resolution;

    @JsonProperty("points")
    private List<String> points;

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public String getQueryParamsString() throws UnsupportedEncodingException {

        String pointsList = String.join(",", points);
        String queryString = String.format(
                "start=%s&end=%s&resolution=%s&points=%s",
                start,
                end,
                resolution,
                URLEncoder.encode(pointsList, "UTF-8")
        );

        return queryString;
    }
}
