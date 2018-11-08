
package Model.Atom;

public class OLSRow {

    private final double kw_per_ton;
    private final double tons;
    private final double oatwb;
    private final double totalActive;
    private final double dayTimeFlag;

    public OLSRow(
            double kw_per_ton,
            double tons,
            double oatwb,
            double totalActive,
            double dayTimeFlag) {

        this.kw_per_ton = kw_per_ton;
        this.tons = tons;
        this.oatwb = oatwb;
        this.totalActive = totalActive;
        this.dayTimeFlag = dayTimeFlag;
    }
    
    public double getKWPerTon(){
        return this.kw_per_ton;
    }
      
    public double[] getXArray(){
        double[] arr = new double[4];
        
        arr[0] = this.tons;
        arr[1] = this.oatwb;
        arr[2] = this.totalActive;
        arr[3] = this.dayTimeFlag;
        
        return arr;
                                          
    }

}
