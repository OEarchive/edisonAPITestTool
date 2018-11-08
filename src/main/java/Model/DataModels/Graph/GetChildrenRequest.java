package Model.DataModels.Graph;

import Model.DataModels.Sites.EnumActiveAlarmFlag;
import Model.DataModels.Sites.EnumHierarchyAlarmsFlag;
import Model.DataModels.Sites.EnumIncludeDescendantNodes;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class GetChildrenRequest {

    private Boolean includeDatapoints;
    private List<String> datapoints;
    private EnumActiveAlarmFlag includeActiveAlarms;
    private EnumHierarchyAlarmsFlag includeHeirarchyAlarms;
    private EnumIncludeDescendantNodes includeDown;

    public GetChildrenRequest(
            boolean includeDatapoints, List<String> datapoints,
            EnumActiveAlarmFlag includeActiveAlarms, 
            EnumHierarchyAlarmsFlag includeHeirarchyAlarms,
            EnumIncludeDescendantNodes includeDown) {

        this.includeDatapoints = includeDatapoints;
        this.datapoints = datapoints;
        this.includeActiveAlarms = includeActiveAlarms;
        this.includeHeirarchyAlarms = includeHeirarchyAlarms;
        this.includeDown = includeDown;
    }

    public String getQueryParamsString() throws UnsupportedEncodingException {

        String queryString = "";

        if (includeDatapoints) {
            String pointsList = String.join(",", datapoints);
            queryString += "datapoints=" + pointsList;
        }

        if (includeActiveAlarms != EnumActiveAlarmFlag.noIncludeQueryParam) {

            if (queryString.length() > 0) {
                queryString += "&";
            }
            
            queryString += "activeAlarms=" + (includeActiveAlarms == EnumActiveAlarmFlag.includeAnyActiveAlarms ? "true" : "false");
        }

        if (includeHeirarchyAlarms != EnumHierarchyAlarmsFlag.noIncludeQueryParameter) {
            if (queryString.length() > 0) {
                queryString += "&";
            }
            queryString += "hierarchyAlarms=" + (includeHeirarchyAlarms == EnumHierarchyAlarmsFlag.returnAlarmsAndBelow ? "true" : "false");
        }

        if (includeDown != EnumIncludeDescendantNodes.DontIncludeParameter) {
            if (queryString.length() > 0) {
                queryString += "&";
            }
            queryString += "down=" + (includeDown == EnumIncludeDescendantNodes.Yes ? "true" : "false");
        }

        if (queryString.length() > 0) {
            queryString = "?" + queryString;
        }

        return queryString;
    }
}
