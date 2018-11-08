package Model.DataModels.Atom;

public class AtomChillerInfo {

    private final String chillerName;
    private final String chillerSid;
    private final String CHSBooleanName;
    private final String CHWFLOName;
    private final String CHWRTName;
    private final String CHWSTName;
    private final String CHkWName;
    private final int chillerCapacity;

    public AtomChillerInfo(
            String chillerName,
            String chillerSid,
            String CHSBooleanName,
            String CHWFLOName,
            String CHWRTName,
            String CHWSTName,
            String CHkWName,
            int chillerCapacity) {

        this.chillerName = chillerName;
        this.chillerSid = chillerSid;
        this.CHSBooleanName = CHSBooleanName;
        this.CHWFLOName = CHWFLOName;
        this.CHWRTName = CHWRTName;
        this.CHWSTName = CHWSTName;
        this.CHkWName = CHkWName;
        this.chillerCapacity = chillerCapacity;

    }

    public String getChillerName() {
        return this.chillerName;
    }

    public String getChillerSid() {
        return this.chillerSid;
    }

    public String getCHSBoooleanName() {
        return this.CHSBooleanName;
    }

    public String getCHWFLOName() {
        return this.CHWFLOName;
    }

    public String getCHWRTName() {
        return this.CHWRTName;
    }

    public String getCHWSTName() {
        return this.CHWSTName;
    }

    public String getCHkWName() {
        return this.CHkWName;
    }

    public int getChillerCapcity() {
        return this.chillerCapacity;
    }
}
