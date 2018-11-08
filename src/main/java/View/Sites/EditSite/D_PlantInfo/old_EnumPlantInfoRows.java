package View.Sites.EditSite.D_PlantInfo;

import java.util.ArrayList;
import java.util.List;

public enum old_EnumPlantInfoRows {

    //utility rate
    //totalcapcity
    co2EmissionFactor("CO2 Emission Factor", 0),
    minimumChilledWaterFlow("Minimum Chilled Water Flo", 1),
    BlendedUtilityRate("Blended Utility Rate", 2),
    chillerPlantCoolingCapacity("Chiller Plant Cooling Capacity", 3),
    chilledWaterDistType("Chilled Water Distribution Type", 4),
    condenserWaterDistType("Condenser Water Distribution Type", 5),
    currentDpSetPoint("Current DP Setpoint", 6),
    chilledWaterMinimumDp("Chilled Water Minimum DP", 7),
    chilledWaterMaximumDp("Chilled Water Maximum DP", 8),
    chilledWaterDpOsaMin("Chilled Water OSA Min", 9),
    chilledWaterDpOsaMax("Chilled Water OSA Max", 10),
    chilledWaterStResetEnable("Chilled Water St Reset Enable", 11),
    chilledWaterSupplyTempMin("Chiller Water Supply Temp Min", 12),
    chilledWaterSupplyTempMax("Chiller Water Supply Temp Max", 13),
    currentChilledWaterStSetPoint("Current Chilled Water ST Setpoint", 14),
    secondaryChilledWaterPumpMinSpeed("2ndary Chilled Water Pump Min Speed", 15),
    primaryChilledWaterPumpMinSpeed("Primary Chilled Water Pump Min Speed", 16),
    currentEnteringCondenserWaterSetPoint("Current Entering Condenser Water Setpoint", 17),
    minimumCondWaterEnteringTemp("Minumum Condenser Water Entering Temperature", 18),
    maximumCondWaterEnteringTemp("Maximum Condenser Water Entering Temperature", 19),
    condWaterPumpMinSpeed("Condenser Water Minimum Pump Speed", 20);

    private final String name;
    private final int row;

    old_EnumPlantInfoRows(String name, int row) {
        this.name = name;
        this.row = row;

    }

    public String getName() {
        return this.name;
    }

    public int getRow() {
        return this.row;
    }

    static public old_EnumPlantInfoRows getEnumFromRowNumber(int row) {
        for (old_EnumPlantInfoRows rowEnum : old_EnumPlantInfoRows.values()) {
            if (rowEnum.getRow() == row) {
                return rowEnum;
            }
        }

        return null;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (old_EnumPlantInfoRows rowEnum : old_EnumPlantInfoRows.values()) {
            names.add(rowEnum.getName());
        }
        return names;
    }

}
