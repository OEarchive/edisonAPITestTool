package Model.DataModels.Datapoints.simulator;

public class XLSPointRow {

    private String group;
    private String stAttrName;
    private String graphAttName;
    private String uom;
    private EnumXLSPointType pointType;

    private Object min;
    private Object max;
    private EnumPattern pattern;
    private EnumPeriod period;
    private Object offset;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStAttrName() {
        return stAttrName;
    }

    public void setStAttrName(String stAttrName) {
        this.stAttrName = stAttrName;
    }

    public String getGraphAttrName() {
        return graphAttName;
    }

    public void setGraphAttrName(String graphAttName) {
        this.graphAttName = graphAttName;
    }

    public String getUOM() {
        return uom;
    }
    
    public void setUOM( String uom ){
        this.uom = uom;
    }

    public EnumXLSPointType getPointType() {
        return this.pointType;
    }
    
    public void setPointType( EnumXLSPointType pointType ){
        this.pointType = pointType;
    }

    public Object getMin() {
        return this.min;
    }
    
    public void setMin( Object min ){
        this.min = min;
    }

    public Object getMax() {
        return this.max;
    }
    
    public void setMax( Object max ){
        this.max = max;
    }

    public EnumPattern getPattern() {
        return this.pattern;
    }
    
    public void setPattern( EnumPattern pattern ){
        this.pattern = pattern;
    }

    public EnumPeriod getPeriod() {
        return this.period;
    }
    
    public void setPeriod( EnumPeriod period ){
        this.period = period;
    }
    
    public Object getOffset() {
        return this.offset;
    }
    
    public void setOffset( Object offset ){
        this.offset = offset;
    }

}
