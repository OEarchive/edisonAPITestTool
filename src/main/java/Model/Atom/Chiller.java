package Model.Atom;

import java.util.HashMap;


public class Chiller {

    private final int chillerId;
    private int AddFlag;
    private final boolean isOn;
    
    private final HashMap< EnumChillerValues, Double> chillerValues;

    public Chiller(
            int chillerId,
            boolean isOn,
            double chwst,
            double chwrt,
            double chwflo,
            double kw,
            double tons,
            double kwPerTon) {

        this.chillerValues = new HashMap<>();

        this.chillerId = chillerId;
        this.AddFlag = 0;
        this.isOn = isOn;

        this.setValue( EnumChillerValues.CHWST, chwst);
        this.setValue( EnumChillerValues.CHWRT, chwrt);
        this.setValue( EnumChillerValues.CHWFLO, chwflo);
        this.setValue( EnumChillerValues.KW, kw);
        this.setValue( EnumChillerValues.TONS, tons);
        this.setValue( EnumChillerValues.KW_PER_TON, kwPerTon);
    }

    public Chiller(
            int chillerId,
            String addStr,
            String isOnStr,
            String chwstStr,
            String chwrtStr,
            String chwfloStr,
            String kwStr,
            String tonsStr,
            String kwPerTonStr) {

        this.chillerValues = new HashMap<>();

        this.chillerId = chillerId;

        if (addStr.length() > 0) {
            this.AddFlag = Integer.parseInt(addStr);
        } else {
            this.AddFlag = 0;
        }

        this.isOn = "1".equals(isOnStr);

        this.setValue( EnumChillerValues.CHWST, chwstStr);
        this.setValue( EnumChillerValues.CHWRT, chwrtStr);
        this.setValue( EnumChillerValues.CHWFLO, chwfloStr);
        this.setValue( EnumChillerValues.KW, kwStr);
        this.setValue( EnumChillerValues.TONS, tonsStr);
        this.setValue( EnumChillerValues.KW_PER_TON, kwPerTonStr);
         
    }
    
    private void setValue( EnumChillerValues ecv, String tempString ){
        if (tempString.length() > 0) {
            this.chillerValues.put(ecv, Double.parseDouble(tempString));
        } else {
            this.chillerValues.put(ecv, 0.0);
        }
        
    }

    private void setValue( EnumChillerValues ecv, double cv ){
        this.chillerValues.put(ecv, cv);
    }

    public int getChillerId() {
        return this.chillerId;
    }

    public int getAddFlag() {
        return this.AddFlag;
    }

    public void setAddFlag() {
        this.AddFlag = 1;
    }

    public boolean getIsOn() {
        return this.isOn;
    }
    
    
    public Double getChillerValue( EnumChillerValues ecv ){
        return this.chillerValues.get( ecv );
    }



}
