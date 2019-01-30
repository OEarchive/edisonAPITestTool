package View.Sites.EditSite.A_History.Tesla.PushToTesla.SparsePointPusher.SparsePointsTable;

public enum EnumSparsePointNames {

    MinimumChilledWaterFlow("MinimumChilledWaterFlow", "MinimumChilledWaterFlow"),
    TotalCapacity("TotalCapacity", "TotalCapacity"),
    UtilityRate("BlendedUtilityRate", "UtilityRate"),
    CO2Rate("CO2EmissionFactor", "CO2Rate");

    private final String edisonName;
    private final String teslaName;

    EnumSparsePointNames(String edisonName, String teslaName) {
        this.edisonName = edisonName;
        this.teslaName = teslaName;

    }

    public static EnumSparsePointNames getEnumFromEdisonName(String edisonName) {

        for (EnumSparsePointNames v : EnumSparsePointNames.values()) {
            if (v.edisonName.contentEquals(edisonName)) {
                return v;
            }
        }
        return null;
    }
    
    public static EnumSparsePointNames getEnumFromTeslaName(String teslaName) {

        for (EnumSparsePointNames v : EnumSparsePointNames.values()) {
            if (v.teslaName.contentEquals(teslaName)) {
                return v;
            }
        }
        return null;
    }

    public String getEdisonName() {
        return this.edisonName;
    }

    public String getTeslaName() {
        return this.teslaName;
    }


}
