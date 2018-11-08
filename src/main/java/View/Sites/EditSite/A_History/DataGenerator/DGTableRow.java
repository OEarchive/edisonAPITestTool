package View.Sites.EditSite.A_History.DataGenerator;

import Model.DataModels.Datapoints.DatapointMetadata;
import Model.DataModels.Datapoints.simulator.EnumPattern;
import Model.DataModels.Datapoints.simulator.EnumPeriod;
import Model.DataModels.Datapoints.simulator.EnumPointType;
import Model.DataModels.Graph.EdisonNode;
import Model.DataModels.Graph.EnumGraphNodeTypes;

public class DGTableRow {

    private final EnumGraphNodeTypes scope;
    private final String sid;
    private final String pointName;
    private String stAttrName;
    private String graphAttrName;
    private final String uom;
    private final EnumPointType pointType;

    private Object minValue;
    private Object maxValue;

    private EnumPattern pattern;
    private EnumPeriod period;
    
    private Object offset;
    
    private DatapointMetadata md;

    public DGTableRow(EnumGraphNodeTypes scope, String sid, DatapointMetadata dataPoint) {
        
        this.scope = scope;
        this.sid = sid;
        this.pointName = (String) dataPoint.getMetadata().get("name");
        this.stAttrName = "?";
        this.graphAttrName = "?";
        this.pointType = EnumPointType.getTypeFromName(dataPoint.getPointType());
        this.uom = (String) dataPoint.getMetadata().get("uom");
        this.minValue = null;
        this.maxValue = null;
        this.pattern = EnumPattern.notSpecified;
        this.period = EnumPeriod.notSpecified;
        this.offset = null;
        
        this.md = dataPoint;

    }
    
    public EnumGraphNodeTypes getScope(){
        return this.scope;
    }
    
    public String getSid(){
        return this.sid;
    }

    public String getPointName() {
        return this.pointName;
    }

    public String getStAttrName() {
        return this.stAttrName;
    }

    public void setStAttrName(String stAttrName) {
        this.stAttrName = stAttrName;
    }

    public String getGraphAttrName() {
        return this.graphAttrName;
    }

    public void setGraphAttrName(String graphAttrName) {
        this.graphAttrName = graphAttrName;
    }

    public EnumPointType getPointType() {
        return this.pointType;
    }

    public String getUOM() {
        return this.uom;
    }

    public Object getMinValue() {
        return minValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public Object getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public EnumPeriod getPeriod() {
        return this.period;
    }

    public void setPeriod(EnumPeriod period) {
        this.period = period;
    }

    public EnumPattern getPattern() {
        return this.pattern;
    }

    public void setPattern(EnumPattern pattern) {
        this.pattern = pattern;
    }
    
    public Object getOffset() {
        return this.offset;
    }

    public void setOffset(Object offset) {
        this.offset = offset;
    }
    
    public DatapointMetadata getMetadata(){
        return this.md;
    }
}
