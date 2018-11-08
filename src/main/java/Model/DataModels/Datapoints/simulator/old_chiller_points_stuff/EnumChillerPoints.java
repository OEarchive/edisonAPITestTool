package Model.DataModels.Datapoints.simulator.old_chiller_points_stuff;

import java.util.ArrayList;
import java.util.List;

public enum EnumChillerPoints {

    ch_FLA("PercentFLA", "percent", 40, 80),
    ch_S("Status", "boolean", 0, 1),
    ch_CHWFLO("ChilledWaterFlow", "gallonsPerMinute", 600, 1600),
    ch_CDWST("CondenserSupplyWaterTemperature", "fahrenheit", 65, 75),
    ch_COM1_kW("Compressor1kW", "kw", 100, 600),
    
    ch_CHWSTSPNotOptimized("NotOptimized", "boolean", 0, 1),
    ch_CHWRT("ChilledWaterReturnTemperature", "fahrenheit", 48, 55),
    ch_CHWST("ChilledWaterSupplyTemperature", "fahrenheit", 42, 48),
    ch_CDWVLV("CDWValveCommand", "boolean", 0, 1),
    ch_CDT("CondenserRefrigerantTemperature", "fahrenheit", 75, 85),
    ch_SURGE("Surge", "boolean", 0, 1),
    ch_COM1_F("Compressor1GeneralFault", "boolean", 0, 1),
    ch_CHWSTSP("ChilledWaterSupplyTemperatureSetpoint", "fahrenheit", 42, 48),
    ch_CDWDP("CondenserWaterDifferentialPressure", "psi", 1, 4),
    ch_CDP("CondenserRefrigerantPressure", "psi", 30, 40),
    ch_COM1_SPD("Compressor1OutputSpeed", "percent", 0, 100),
    ch_COM1_S("Compressor1Status", "boolean", 0, 1),
    ch_CDWFLO("CondenserWaterFlow", "gallonsPerMinute", 800, 1800),
    ch_SS("StartStopCommand", "boolean", 0, 1),
    ch_CHWVLV("CHWValveCommand", "boolean", 0, 1),
    ch_CDWRT("CondenserReturnWaterTemperature", "fahrenheit", 75, 85),
    ch_CHWDP("WaterDifferentialPressure", "psi", 1, 4),
    ch_ALARM("Alarm", "boolean", 0, 1),
    ch_EVT("EvaporatorRefrigerantTemperature", "fahrenheit", 41, 47),
    ch_AVAIL("Available", "boolean", 0, 1),
    ch_kW("kW", "kw", 100, 600),
    ch_DMD("DemandLimiting", "percent", 0, 100),
    ch_COM1_IGV("Compressor1IGVPositionVanePosition", "percent", 0, 100),
    ch_COMDISCHT("CompressorDischargeTemperature", "fahrenheit", 85, 85),
    ch_EVP("EvaporatorRefrigerantPressure", "psi", 14.1, 18.1),
    ch_F("AlarmFault", "boolean", 0, 1);

    private final String edisonName;
    private final String uom;
    private final double min;
    private final double max;

    EnumChillerPoints(String edisonName, String uom, double min, double max) {
        this.edisonName = edisonName;
        this.uom = uom;
        this.min = min;
        this.max = max;

    }

    public String getJaceName() {
        return this.name();
    }

    public String getEdsionName() {
        return this.edisonName;
    }
    
    public String getUOM(){
        return this.uom;
    }
    
    public double getMin(){
        return this.min;
    }
    
    public double getMax(){
        return this.max;
    }

    static public List<String> getJaceNames() {
        List<String> names = new ArrayList<>();
        for (EnumChillerPoints res : EnumChillerPoints.values()) {
            names.add(res.name());
        }
        return names;
    }

    static public List<String> getEdsionNames() {
        List<String> names = new ArrayList<>();
        for (EnumChillerPoints res : EnumChillerPoints.values()) {
            names.add(res.edisonName);
        }
        return names;
    }
    
    static public EnumChillerPoints getPointFromJaceName(String jaceName) {
        for (EnumChillerPoints res : EnumChillerPoints.values()) {
            if (res.name().compareTo(jaceName) == 0) {
                return res;
            }
        }
        return null;
    }

    static public EnumChillerPoints getPointFromEdisonName(String edisonName) {
        for (EnumChillerPoints res : EnumChillerPoints.values()) {
            if (res.getEdsionName().compareTo(edisonName) == 0) {
                return res;
            }
        }
        return null;
    }

}
