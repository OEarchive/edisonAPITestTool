
package Model.DataModels.Datapoints;

public enum EnumAggregationType {
    SUM("SUM"),
    AVG("AVG"),
    COUNT("COUNT"),
    NORMAL("non-aggregated");

    private final String pointNameDecoration;


    EnumAggregationType(String pointNameDecoration) {
        this.pointNameDecoration = pointNameDecoration;
    }

    public String getTagTypeName() {
        return this.pointNameDecoration;
    }
    
}