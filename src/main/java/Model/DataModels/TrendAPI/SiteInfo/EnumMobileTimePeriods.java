package Model.DataModels.TrendAPI.SiteInfo;

import java.util.ArrayList;
import java.util.List;

public enum EnumMobileTimePeriods {

    year("year"),
    month("month"),
    week("week"),
    today("today");

    final private String friendlyName;

    EnumMobileTimePeriods(String name) {
        this.friendlyName = name;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
    
    static public List<String> getFriendlyNames(){
        List<String> names = new ArrayList<>();
        
        for( EnumMobileTimePeriods enumMobileTimePeriod : EnumMobileTimePeriods.values() ){
            names.add( enumMobileTimePeriod.getFriendlyName() );
        }
        
        return names;
    }

    static public EnumMobileTimePeriods getEnumFromDropdownName(String name) {
        for (EnumMobileTimePeriods res : EnumMobileTimePeriods.values()) {
            if (res.name().compareTo(name) == 0) {
                return res;
            }
        }
        return null;
    }
}
