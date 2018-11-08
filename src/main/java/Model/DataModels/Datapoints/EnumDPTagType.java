
package Model.DataModels.Datapoints;

public enum EnumDPTagType {
    summary("summary"),
    calculated("calculated"),
    config("configuration"),
    ratio("ratio");

    private final String tagTypeName;


    EnumDPTagType(String tagTypeName) {
        this.tagTypeName = tagTypeName;
    }

    public String getTagTypeName() {
        return this.tagTypeName;
    }
    

    static public EnumDPTagType getTagTypeFromName( String tagTypeName ){
        for (EnumDPTagType res : EnumDPTagType.values()) {
            if( res.getTagTypeName().compareTo(tagTypeName) == 0 ){
                return res;
            }
        }
        return null;  
    }
}
