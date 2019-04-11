package Model.DataModels.TeslaModels.ComboHistories;

public class ComboHistoryPointValuePair {

    private Object edisonValue;
    private Object teslaValue;


    public ComboHistoryPointValuePair() {
        edisonValue = null;
        teslaValue = null;

    }
    
    public void setEdisonValue(Object v) {
        this.edisonValue = v;
    }

    public Object getEdisonValue() {
        return this.edisonValue;
    }

    public void setTeslaValue(Object v) {
        this.teslaValue = v;
    }

    public Object getTeslaValue() {
        return this.teslaValue;
    }



}
