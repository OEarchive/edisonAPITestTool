package Model.DataModels.ReportVerification.PreProcess;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.util.List;
import java.util.Map;

public class UnCalculatedPoint {

    private final DatapointsAndMetadataResponse resp;

    public UnCalculatedPoint(DatapointsAndMetadataResponse resp) {
        this.resp = resp;
    }
    
    public String getSid() {
        return resp.getSid();
    }

    public String getName() {
        return resp.getName();
    }

    public String getLabel() {
        return resp.getLabel();
    }

    public Object getValue() {
        return resp.getValue();
    }

    public String getUOM() {
        return resp.getUOM();
    }

    public String getMeasure() {
        return resp.getMeasure();
    }

    public List<Map> getDatapointAssociations() {
        return resp.getDatapointAssociations();
    }

}
