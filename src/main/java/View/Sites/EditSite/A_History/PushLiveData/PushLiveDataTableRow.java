
package View.Sites.EditSite.A_History.PushLiveData;

import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.simulator.EnumPointType;


public class PushLiveDataTableRow {

    private final String pointName;
    private final EnumPointType pointType;
    private Object val;


    public PushLiveDataTableRow(DatapointMetadata dataPoint) {
        
        this.pointName = (String) dataPoint.getMetadata().get("name");
        this.pointType = EnumPointType.getTypeFromName(dataPoint.getPointType());
        this.val = 0;

    }
    
    public String getPointName() {
        return this.pointName;
    }
    
    public EnumPointType getPointType(){
        return this.pointType;
    }

    
    public Object getVal() {
        return this.val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}