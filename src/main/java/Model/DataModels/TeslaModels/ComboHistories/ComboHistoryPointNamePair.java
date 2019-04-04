
package Model.DataModels.TeslaModels.ComboHistories;


public class ComboHistoryPointNamePair {

    private String teslaValue;
    private String edisonValue;

    public ComboHistoryPointNamePair() {
        teslaValue = null;
        edisonValue = null;
    }

    public void setTeslaValue(String v) {
        this.teslaValue = v;
    }

    public String getTeslaValue() {
        return this.teslaValue;
    }

    public void setEdisonValue(String v) {
        this.edisonValue = v;
    }

    public String getEdisonValue() {
        return this.edisonValue;
    }

}


