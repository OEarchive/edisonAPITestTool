package Model.DataModels.Datapoints.simulator.CalcPointsXLSReader;

public enum E3OSToEdisonNamesEnum {

    kWTon("THC kW/Ton (kW/Ton)", "kWTon"),
    kWh("THC kWh (kWh)", "kWh"),
    //BaselinekWh("THC OldkWh (kWh)", "BaselinekWh"),
    //kWhDelta("THC kWh Delta (kWh)", "kWhDelta"),
    DollarsCost("THC DollarCost (Currency)", "DollarsCost"),
    //BaselineDollarsCost("THC OldDollarCost (Currency)", "BaselineDollarsCost"),
    //DollarsSaved("THC DollarsSaved (Currency)", "DollarsSaved"),
    CO2Produced("THC CO2Cost (lbs of CO2)", "CO2Produced"),
    //BaselineCO2Produced("THC OldCO2Cost (lbs of CO2)", "BaselineCO2Produced"),
    //CO2Saved("THC CO2Saved (lbs of CO2)", "CO2Saved"),
    TonHours("THC TonHours (TonHrs)", "TonHours"),
    PercentOptimized("THC PercentOptimized (%)", "PercentOptimized"),
    ChillerkWTon("THCEDGE CH_kW/Ton (kW/Ton)", "ChillerkWTon"),
    PercentPlantOff("THC PercentNotOperating (%)", "PercentPlantOff");

    private final String E3OSName;
    private final String EdgeName;

    E3OSToEdisonNamesEnum(String E3OSName, String EdgeName) {
        this.E3OSName = E3OSName;
        this.EdgeName = EdgeName;
    }

    public String getE3OSName() {
        return this.E3OSName;
    }

    public String getEdgeName() {
        return this.EdgeName;
    }

    public static E3OSToEdisonNamesEnum getEnumFromE3OSName(String name) {

        for (E3OSToEdisonNamesEnum v : E3OSToEdisonNamesEnum.values()) {
            if (v.getE3OSName().compareTo(name) == 0) {
                return v;
            }
        }

        return null;
    }

    public static E3OSToEdisonNamesEnum getEnumFromEdgeName(String name) {

        for (E3OSToEdisonNamesEnum v : E3OSToEdisonNamesEnum.values()) {
            if (v.getEdgeName().compareTo(name) == 0) {
                return v;
            }
        }

        return null;
    }

}
