
package Model.DataModels.TeslaModels.ComboHistories;


public class ComboHistoryPointNamePair {

    private String edisonName;
    private String teslaName;

    public ComboHistoryPointNamePair( String edisonName, String teslaName  ) {
        this.edisonName = edisonName;
        this.teslaName = teslaName;
    }

    public void setTeslaName(String v) {
        this.teslaName = v;
    }

    public String getTeslaName() {
        return this.teslaName;
    }

    public void setEdisonName(String v) {
        this.edisonName = v;
    }

    public String getEdisonName() {
        return this.edisonName;
    }
    
    
    public String getEdisonDecorated(){
        return this.edisonName + "(E)";
    }

    
    public String getTeslaDecorated(){
        return this.teslaName + "(T)";
    }

}


