package Model.DataModels.TeslaModels;

public class MappingTableRow {

    private EnumMapStatus mapStatus;
    private String edisonShortName;
    private String edisonSid;
    private String teslaName;
    private String teslaType;
    private String teslaID;

    public MappingTableRow() {

        mapStatus = EnumMapStatus.NoInfo;
        edisonShortName = "?";
        edisonSid = "?";
        teslaName = "?";
        teslaType = "?";
        teslaID = "?";

    }
    
    public void setMapStatus(EnumMapStatus mapStatus) {
        this.mapStatus = mapStatus;
    }

    public EnumMapStatus getMapStatus() {
        return mapStatus;
    }
    
   
    public void setEdisonShortName(String edisionShortName) {
        edisonShortName = edisionShortName;
    }

    public String getEdsionShortName() {
        return edisonShortName;
    }

    public void setEdisonSid(String edisonSid) {
        this.edisonSid = edisonSid;
    }

    public String getEdsionSid() {
        return edisonSid;
    }

    public void setTeslaName(String teslaName) {
        this.teslaName = teslaName;
    }

    public String getTeslaName() {
        return teslaName;
    }
    
    public void setTeslaType(String teslaType) {
        this.teslaType = teslaType;
    }

    public String getTeslaType() {
        return teslaType;
    }

    public void setTeslaID(String teslaID) {
        this.teslaID = teslaID;
    }

    public String getTeslaID() {
        return teslaID;
    }

}
