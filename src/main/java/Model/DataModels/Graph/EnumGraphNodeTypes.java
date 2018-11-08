package Model.DataModels.Graph;

import java.util.ArrayList;
import java.util.List;

public enum EnumGraphNodeTypes {

    SITE("site", 0),
    CHILLER("chiller", 1),
    AHU("ahu", 2),
    WEATHER("weather", 3),
    CUSTOMER("customer", 4),
    building("building", 5),
    equipment("equipment", 6),
    installation("installation", 7),
    station("station", 8),
    device("device", 9),
    origin("origin", 10),
    chilledWaterPump("chilled-water-pump", 11),
    primaryChilledWaterPump("primary-chilled-water-pump", 12),
    secondaryChilledWaterPump("secondary-chilled-water-pump", 13),
    CondenserWaterPump("condenser-water-pump", 14),
    ahuWaterValve("ahu-water-valve", 15),
    coolingTower("cooling-tower", 16),
    coolingTowerFan("cooling-tower-fan", 17),
    heatExchanger("heat-exchanger", 18);

    private String edisonName;
    private int dropDownIndex;

    EnumGraphNodeTypes(String name, int dropDownIndex) {
        this.edisonName = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getEdisonName() {
        return this.edisonName;
    }

    static public List<String> getDropdownNames() {
        List<String> dropdownNames = new ArrayList<>();
        for (EnumGraphNodeTypes et : EnumGraphNodeTypes.values()) {
            dropdownNames.add(et.name());
        }
        return dropdownNames;
    }

    public int getDropdownIndex() {
        return this.dropDownIndex;
    }

    static public EnumGraphNodeTypes getEquipTypeFromDropDownName(String name) {

        for (EnumGraphNodeTypes et : EnumGraphNodeTypes.values()) {
            if (et.name().compareTo(name) == 0) {
                return et;
            }
        }
        return null;
    }

    static public EnumGraphNodeTypes getEquipTypeFromEdisonNodeName(String name) {

        for (EnumGraphNodeTypes et : EnumGraphNodeTypes.values()) {
            if (et.getEdisonName().compareTo(name) == 0) {
                return et;
            }
        }
        return null;
    }

}
