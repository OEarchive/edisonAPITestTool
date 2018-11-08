package View.Sites.EditSite.A_History.ReportVerification.B_RVQueryStatsTable;

import Model.DataModels.ReportVerification.PostProcess.CalculatedBucket;
import Model.DataModels.ReportVerification.PreProcess.UnCalculatedBucket;
import Model.DataModels.Sites.SiteDatapoints.DatapointStatistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RVQueryStats {

    private final Map< String, DatapointStatistics> map;
    private final List<String> uNames;

    public RVQueryStats(CalculatedBucket bucket) {
        this.map = new HashMap<>();
        this.uNames = new ArrayList<>();

        for (String uName : bucket.getNewPointNamesAndSids()) {
            uNames.add(uName);
            map.put(uName, new DatapointStatistics(bucket.getPointNameAndSidToListOfValuesMap().get(uName)));
        }
    }

    public List<String> getDataPointNamesAndSids() {
        return this.uNames;
    }

    public DatapointStatistics getDataPointStatistics(String uName) {
        return map.get(uName);
    }

}
