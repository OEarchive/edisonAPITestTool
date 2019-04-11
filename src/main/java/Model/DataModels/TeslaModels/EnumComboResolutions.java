
package Model.DataModels.TeslaModels;

import Model.DataModels.Datapoints.EnumEdisonResolutions;
import java.util.ArrayList;
import java.util.List;


public enum EnumComboResolutions {
    FIVEMINUTE(0, "fiveMinute", EnumEdisonResolutions.MINUTE5,  EnumTeslaResolutions.FIVEMINUTE),
    HOUR(1, "hour", EnumEdisonResolutions.HOUR,  EnumTeslaResolutions.HOUR),
    DAY(2, "day", EnumEdisonResolutions.DAY,  EnumTeslaResolutions.DAY),
    WEEK(3, "week", EnumEdisonResolutions.WEEK,  EnumTeslaResolutions.WEEK),
    MONTH(4, "month", EnumEdisonResolutions.MONTH,  EnumTeslaResolutions.MONTH),
    QUARTER(5, "quarter", EnumEdisonResolutions.MONTH,  EnumTeslaResolutions.QUARTER),
    YEAR(6, "year", EnumEdisonResolutions.YEAR,  EnumTeslaResolutions.YEAR);

    private final String name;
    private final int dropDownIndex;
    private final EnumEdisonResolutions edisonResolution;
    private final EnumTeslaResolutions teslaResolution;

    EnumComboResolutions(int dropDownIndex, String name, EnumEdisonResolutions edisonResolution, EnumTeslaResolutions teslaResolution ) {
        this.dropDownIndex = dropDownIndex;
        this.name = name;
        this.edisonResolution = edisonResolution;
        this.teslaResolution = teslaResolution;
    }
    
    public int getDropDownIndex(){
        return this.dropDownIndex;
    }

    public String getName() {
        return this.name;
    }
    

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumTeslaResolutions res : EnumTeslaResolutions.values()) {
            names.add(res.getName());
        }
        return names;
    }
    
    static public EnumComboResolutions getResolutionFromName( String name ){
        for (EnumComboResolutions res : EnumComboResolutions.values()) {
            if( res.getName().compareTo(name) == 0 ){
                return res;
            }
        }
        return null;
        
    }
    
    public EnumEdisonResolutions getEdisonResolution(){
        return this.edisonResolution;
    }
    
    public EnumTeslaResolutions getTeslaResolution(){
        return teslaResolution;
    }
}
