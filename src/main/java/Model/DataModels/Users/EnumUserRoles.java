package Model.DataModels.Users;

import java.util.ArrayList;
import java.util.List;

public enum EnumUserRoles {

    OESuperAdmin("oe:superadmin", "super", 0),
    OEPortalAdmin("oepp:oeadmin","oeppadmin",  1),
    PortalAccExec("oepp:oeaccountexec","exec",  2),
    PortalPartnerAdmin("oepp:partneradmin","ppadmin",  3),
    PortalUser("oepp:partneruser","ppuser",  4),
    PortalTechReviewer("oepp:oetechreview", "techreview", 5),
    Station("opticx:station", "station", 6),
    OEAdmin("opticx:admin", "ocxadmin", 7),
    OESiteAdmin("opticx:siteadmin","ocxsiteadmin",  8),
    OEUser("opticx:user", "ocxuser", 9),
    PortalAPIRole("opticx:oepp-api","ocxoeppapi", 10);

    private String edisonRoleName;
    private String friendlyName;
    private int dropDownIndex;

    EnumUserRoles(String name, String friendlyName, int dropDownIndex) {
        this.edisonRoleName = name;
        this.friendlyName = friendlyName;
        this.dropDownIndex = dropDownIndex;

    }

    public String getEdisonRoleName() {
        return this.edisonRoleName;
    }
    
    public String getFriendlyName(){
        return this.friendlyName;
    }

    static public List<String> getDropdownNames() {
        List<String> dropdownNames = new ArrayList<>();
        for (EnumUserRoles et : EnumUserRoles.values()) {
            dropdownNames.add(et.name());
        }
        return dropdownNames;
    }

    public int getDropdownIndex() {
        return this.dropDownIndex;
    }

    static public EnumUserRoles getRoleFromDropDownName(String name) {

        for (EnumUserRoles et : EnumUserRoles.values()) {
            if (et.name().compareTo(name) == 0) {
                return et;
            }
        }
        return null;
    }

}
