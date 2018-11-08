package Model.DataModels.Sites;

import java.util.List;

public class GetSiteInfoQueryParams {

    List<String> dataPoints;

    public GetSiteInfoQueryParams(
        List<String> dataPoints ) {
        this.dataPoints = dataPoints;

    }

    public String getQueryParams() {

        String params = "";
        params = "?datapoints=[]"; //+ this.dataPoints;
        return params;
    }

}
