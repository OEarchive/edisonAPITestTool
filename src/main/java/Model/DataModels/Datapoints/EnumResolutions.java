
package Model.DataModels.Datapoints;

import java.util.ArrayList;
import java.util.List;


public enum EnumResolutions {
    MINUTE("minute", 0),
    MINUTE5("fiveMinutes", 1),
    HOUR("hour", 2),
    DAY("day", 3),
    WEEK("week", 4),
    MONTH("month", 5),
    YEAR("year", 6);

    private String name;
    private int dropDownIndex;

    EnumResolutions(String name, int dropDownIndex) {
        this.name = name;
        this.dropDownIndex = dropDownIndex;

    }

    public String getName() {
        return this.name;
    }
    
    public int getDropDownIndex(){
        return this.dropDownIndex;
    }

    static public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (EnumResolutions res : EnumResolutions.values()) {
            names.add(res.getName());
        }
        return names;
    }
    
    static public EnumResolutions getResolutionFromName( String name ){
        for (EnumResolutions res : EnumResolutions.values()) {
            if( res.getName().compareTo(name) == 0 ){
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

