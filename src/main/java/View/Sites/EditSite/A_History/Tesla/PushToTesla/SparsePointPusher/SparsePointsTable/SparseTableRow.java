
package View.Sites.EditSite.A_History.Tesla.PushToTesla.SparsePointPusher.SparsePointsTable;

import View.Sites.EditSite.A_History.Tesla.PushToTesla.MappingTable.EnumMapStatus;


public class SparseTableRow {


    private EnumSparsePointNames enumSparsePointName;
    private String edisonSid;
    private String teslaType;
    private String teslaID;
    private Object pointValue;

    public SparseTableRow( EnumSparsePointNames enumSparsePointName ) {

        this.enumSparsePointName = enumSparsePointName;
        this.edisonSid = "?";
        this.teslaType = "?";
        this.teslaID = "?";
        this.pointValue = "?";

    }
    
    public EnumSparsePointNames getSparsePointNameEnum(){
        return this.enumSparsePointName;
    }

    public void setEdisonSid(String edisonSid) {
        this.edisonSid = edisonSid;
    }

    public String getEdsionSid() {
        return edisonSid;
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
    
    public void setPointValue(Object pointValue) {
        this.pointValue = pointValue;
    }

    public Object getPointValue() {
        return pointValue;
    }

}