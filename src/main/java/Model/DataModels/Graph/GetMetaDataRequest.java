package Model.DataModels.Graph;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class GetMetaDataRequest {

    private Boolean includeDatapoints;
    private List<String> datapoints;
    private Boolean includeActiveAlarms;
    private Boolean activeAlarms;
    private Boolean includeHeirarchyAlarms;
    private Boolean heirarchyAlarms;

    public GetMetaDataRequest(
            boolean includeDatapoints, List<String> datapoints,
            Boolean includeActiveAlarms, Boolean activeAlarms,
            Boolean includeHeirarchyAlarms, Boolean heirarchyAlarms) {

        this.includeDatapoints = includeDatapoints;
        this.datapoints = datapoints;

        this.includeActiveAlarms = includeActiveAlarms;
        this.activeAlarms = activeAlarms;

        this.includeHeirarchyAlarms = includeHeirarchyAlarms;
        this.heirarchyAlarms = heirarchyAlarms;

    }

    public void setDatapoints(List<String> datapoints) {
        this.datapoints = datapoints;
    }

    public void setActiveAlarms(Boolean activeAlarms) {
        this.activeAlarms = activeAlarms;
    }

    public void setHeirarchyAlarms(Boolean heirarchyAlarms) {
        this.heirarchyAlarms = heirarchyAlarms;
    }

    public String getQueryParamsString() throws UnsupportedEncodingException {

        String queryString = "";

        if (includeDatapoints) {
            String pointsList = String.join(",", datapoints);
            queryString += "datapoints=" + pointsList;
        }

        if (includeActiveAlarms) {

            if (queryString.length() > 0) {
                queryString += "&";
            }
            queryString += "active_alarms=" + (this.activeAlarms ? "true" : "false");
        }

        if (includeHeirarchyAlarms) {
            if (queryString.length() > 0) {
                queryString += "&";
            }
            queryString += "hierarchy_alarms=" + (this.heirarchyAlarms ? "true" : "false");
        }
        
        if( queryString.length() > 0 ){
            queryString = "?" + queryString;
        }

        return queryString;
    }
}
