package Model.DataModels.Users.TestUsers;

import java.util.ArrayList;
import java.util.List;

public enum EnumTestUsers {

    Bill("bill@externaltestpersona.com", "opticx:siteadmin", 0),
    Aaron("aaron@externaltestpersona.com", "opticx:user", 1),
    Patty("patty@externaltestpersona.com", "opticx:user", 2),
    Elon("elon@externaltestpersona.com", "opticx:siteadmin", 3),
    Perry("perry@externaltestpersona.com", "opticx:user", 4),
    Eddy("eddy@externaltestpersona.com", "opticx:admin", 5),
    Pete("pete@externaltestpersona.com", "opticx:admin", 6),
    Brenda("brenda@externaltestpersona.com", "opticx:user", 7);

    private final String username;
    private final String rolename;
    private final int dropDownIndex;

    EnumTestUsers(String username, String rolename, int dropDownIndex) {
        this.username = username;
        this.rolename = rolename;
        this.dropDownIndex = dropDownIndex;

    }

    public String getUsername() {
        return this.username;
    }

    public String getRolename() {
        return this.rolename;
    }

    static public List<String> getDropdownNames() {
        List<String> dropdownNames = new ArrayList<>();
        for (EnumTestUsers et : EnumTestUsers.values()) {
            dropdownNames.add(et.name());
        }
        return dropdownNames;
    }

    public int getDropdownIndex() {
        return this.dropDownIndex;
    }

    static public EnumTestUsers getTestUserEnumFromUsername(String username) {

        for (EnumTestUsers et : EnumTestUsers.values()) {
            if (et.getUsername().compareTo(username) == 0) {
                return et;
            }
        }
        return null;
    }

}
