
package Model.DataModels.ReportVerification.CalcPointClassification;

public enum EnumPercentStatusTypes {
    Optimized(1),
    PartiallyOptimzied(2),
    Disabled(3),
    BasCommFailure(5),
    PlantOff(4);

    private int statusValue;

    EnumPercentStatusTypes(int statusValue) {
        this.statusValue = statusValue;

    }

    public int getStatusValue(){
        return this.statusValue;
    }

}
