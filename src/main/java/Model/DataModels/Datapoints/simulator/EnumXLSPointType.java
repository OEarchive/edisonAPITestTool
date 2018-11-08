
package Model.DataModels.Datapoints.simulator;


public enum EnumXLSPointType {

    analog("analog"),
    binary("binary");

    private String xlsPointTypeName;

    EnumXLSPointType(String xlsPointTypeName) {
        this.xlsPointTypeName = xlsPointTypeName;

    }

    public String getXLSPointTypeName() {
        return this.xlsPointTypeName;
    }

    public static EnumXLSPointType getTypeFromName(String name) {
        for (EnumXLSPointType pt : EnumXLSPointType.values()) {
            if (pt.getXLSPointTypeName().compareTo(name) == 0) {
                return pt;
            }
        }

        return null;
    }
}