package Model.DataModels.TeslaModels.ComboHistories;

public class ComboHistoryPointValuePair {

    private Object teslaValue;

    private Object edisonValue;

    public ComboHistoryPointValuePair() {
        teslaValue = null;
        edisonValue = null;
    }

    public void setTeslaValue(Object v) {
        this.teslaValue = v;
    }

    public Object getTeslaValue() {
        return this.teslaValue;
    }

    public void setEdisonValue(Object v) {
        this.edisonValue = v;
    }

    public Object getEdisonValue() {
        return this.edisonValue;
    }

}
