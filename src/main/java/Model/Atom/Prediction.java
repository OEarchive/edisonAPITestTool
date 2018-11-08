package Model.Atom;

public class Prediction {

    private final int predictionNumber;
    private final double kwPerTonPredicted;
    private final int obsNumber;

    public Prediction(int predictionNumber, ChillerModel m, IntervalInHistory interval) {
        
        this.predictionNumber = predictionNumber;
        this.obsNumber = interval.getOBS_Number();

        double interceptComponent = m.getRegressor(EnumRegressorTypes.INTERCEPT);
        
        double tonsComponent = m.getRegressor(EnumRegressorTypes.TONS)
                * (interval.getChillerTons() * m.getPlantGrossUp() / interval.getTotalActive()) * m.getChillerSize();

        double oatwbComponent = m.getRegressor(EnumRegressorTypes.OATWB) * interval.getOATWB();

        double totalActiveComponent = m.getRegressor(EnumRegressorTypes.TOTAL_ACTIVE) * interval.getTotalActive();

        double dayComponent = m.getRegressor(EnumRegressorTypes.DAYTIME_FLAG) * (interval.getIsDaytime() ? 1.0 : 0.0);

        this.kwPerTonPredicted = interceptComponent + tonsComponent + oatwbComponent + totalActiveComponent + dayComponent;

        interval.getOBS_Number();

    }
    
    public int getPredictionNumber(){
        return this.predictionNumber;
    }
    
    public int getObsNumber(){
        return this.obsNumber;
    }
    
    public double getKWPerTonPrediction(){
        return this.kwPerTonPredicted;
    }

}
