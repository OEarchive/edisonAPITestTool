package View.Sites.EditSite.J_Contacts;

public enum EnumContactType {

    site,
    support,
    sales;

    public static EnumContactType getContactTypeFromName(String name) {

        for (EnumContactType v : EnumContactType.values()) {
            if (v.name().compareTo(name) == 0) {
                return v;
            }
        }

        return null;
    }
}
