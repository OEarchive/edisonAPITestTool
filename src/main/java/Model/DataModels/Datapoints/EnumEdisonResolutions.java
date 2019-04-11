
package Model.DataModels.Datapoints;

import java.util.ArrayList;
import java.util.List;


public enum EnumEdisonResolutions {
    MINUTE("minute", 0),
    MINUTE5("fiveMinutes", 1),
    HOUR("hour", 2),
    DAY("day", 3),
    WEEK("week", 4),
    MONTH("month", 5),
    YEAR("year", 6);

    private final String friendlyName;
    private final int dropDownIndex;

    EnumEdisonResolutions(String name, int dropDownIndex) {
        this.friendlyName = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
    
    public int getDropDownIndex(){
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumEdisonResolutions res : EnumEdisonResolutions.values()) {
            names.add(res.getFriendlyName());
        }
        return names;
    }
    
    static public EnumEdisonResolutions getResolutionFromName( String name ){
        for (EnumEdisonResolutions res : EnumEdisonResolutions.values()) {
            if( res.getFriendlyName().compareTo(name) == 0 ){
                return res;
            }
        }
        return null;
        
    }
    
    public int getMinutes(){
        switch( this ){
            case MINUTE:
                return 1;
            case MINUTE5:
                return 5;
            case HOUR:
                return 60;
            case DAY:
                return 60 * 24;
            case WEEK:
                return 60 * 24 * 7;
            default:
                return 0;
        }
    }

}

