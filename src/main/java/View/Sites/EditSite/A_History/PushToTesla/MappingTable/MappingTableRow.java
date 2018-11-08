package View.Sites.EditSite.A_History.PushToTesla.MappingTable;

public class MappingTableRow {

    private String edisonShortName;
    private String edisonSid;
    private String teslaName;
    private String teslaID;

    public MappingTableRow() {

        edisonShortName = "?";
        edisonSid = "?";
        teslaName = "?";
        teslaID = "?";

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

    public void setTeslaID(String teslaID) {
        this.teslaID = teslaID;
    }

    public String getTeslaID() {
        return teslaID;
    }

}
