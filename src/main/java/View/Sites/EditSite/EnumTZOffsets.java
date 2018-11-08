
package View.Sites.EditSite;

import java.util.ArrayList;
import java.util.List;


public enum EnumTZOffsets {

    Hawaii("Hawaii", "Pacific/Honolulu"),
    Seattle("Seattle", "America/Los_Angeles"),
    Chicago("Chicago", "America/Chicago"),
    Texas("Texas", "CST6CDT"),
    UTC("London", "Etc/UCT"),
    Dubai("Dubai", "Asia/Dubai" );

    private final String dropDownName;
    private final String zone;

    EnumTZOffsets(String dropDownName, String zone ) {
        this.dropDownName = dropDownName;
        this.zone = zone;
    }

    public String getDropDownName() {
        return this.dropDownName;
    }

    public String getZoneName() {
        return this.zone;
    }
    
    static public EnumTZOffsets getEnumFromDropDownName( String dropDownName ) {
        for (EnumTZOffsets timezone : EnumTZOffsets.values()) {
            if (timezone.getDropDownName().compareTo(dropDownName) == 0) {
                return timezone;
            }
        }

        return null;
    }
    
    static public List<String> getDropDownNames() {
        List<String> names = new ArrayList<>();
        for (EnumTZOffsets timezone : EnumTZOffsets.values()) {
            names.add(timezone.getDropDownName());
        }
        return names;
    }

}