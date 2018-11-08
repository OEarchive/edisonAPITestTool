package Model.Atom;

import java.util.Arrays;
import java.util.HashMap;
import static org.apache.commons.math3.stat.StatUtils.max;
import static org.apache.commons.math3.stat.StatUtils.min;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public final class ChillerModel {

    private final HashMap<Integer, OLSRow> model;
    private final HashMap<Integer, IntervalInHistory> history;
    private final int chillerId;
    
    private boolean modelIsValid;

    private double beta[];
    private double[] residuals;
    private double[][] parametersVariance;
    private double regressandVariance;
    private double rSquared;
    private double sigma;

    private int countOfValidIntervals;

    private double sumOfKWPerTonPredicted;
    private HashMap<Integer, Prediction> predictions;

    private int startingOBSForPredictions;
    private int endingOBSForPredictions;

    private double chillerCapacity;
    private double chillerSize;
    private double plantGrossUp;

    public ChillerModel(int chillerId, HashMap<Integer, IntervalInHistory> history,
                        double chillerCapacity, double chillerSize, double plantGrossUp) {

        this.chillerCapacity = chillerCapacity;
        this.chillerSize = chillerSize;
        this.plantGrossUp = plantGrossUp;

        this.model = new HashMap<>();
        this.history = history;
        this.chillerId = chillerId;
        
        this.countOfValidIntervals = 0;
        for (int obs : history.keySet()) {

            IntervalInHistory interval = history.get(obs);
            if (intervalIsValid(interval)) {

                this.countOfValidIntervals++;
                Chiller c = interval.getChiller(this.chillerId);
                OLSRow olsRow = null;

                olsRow = new OLSRow(
                        c.getChillerValue(EnumChillerValues.KW_PER_TON),
                        c.getChillerValue(EnumChillerValues.TONS),
                        interval.getOATWB(),
                        interval.getTotalActive(),
                        (interval.getIsDaytime()) ? 1.0 : 0.0
                );

                this.model.put(obs, olsRow);
            }
        }

        //the chillerModelIsValid only when the count of valid intervals > 5 days worth = 24 * 60 * 5 / 5
        if (this.countOfValidIntervals < (24 * 60)) {
            this.modelIsValid = false;
        } else {
            this.modelIsValid = true;
        }

        if (this.modelIsValid) {
            this.modelIsValid = RunRegression();
        }

        if (this.modelIsValid) {
            RunRegression();
            setStartStopOBSNumbersForPredictions();
            setKWPerTonPredictions();
        }

    }
    
    public int getChillerId() {
        return this.chillerId;
    }

    public boolean modelIsValid() {
        return this.modelIsValid;
    }

    public Double getSumOfKWPerTonPredictions() {

        this.sumOfKWPerTonPredicted = 0.0;

        if (this.getKWPerTonPredictions() != null) {
            for (int index : this.getKWPerTonPredictions().keySet()) {
                Prediction p = this.getKWPerTonPredictions().get(index);
                this.sumOfKWPerTonPredicted += p.getKWPerTonPrediction();
            }
        }

        return this.sumOfKWPerTonPredicted;
    }

    //Returns True if the regression ran successfully 
    private boolean RunRegression() {

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        try {
            regression.newSampleData(getYVector(), getXMatrix());
            this.beta = regression.estimateRegressionParameters();
            this.residuals = regression.estimateResiduals();
            this.parametersVariance = regression.estimateRegressionParametersVariance();
            this.regressandVariance = regression.estimateRegressandVariance();
            this.rSquared = regression.calculateRSquared();
            this.sigma = regression.estimateRegressionStandardError();
        } catch (Exception ex) {
            this.beta = new double[]{};
            this.residuals = new double[]{};
            this.parametersVariance = new double[][]{{}};
            this.regressandVariance = 0.0;
            this.rSquared = 0.0;
            this.sigma = 0.0;
            return false;
        }
        return true;

    }

    public double[][] getParametersVariance() {
        return this.parametersVariance;
    }

    public double getRegressandVariance() {
        return this.regressandVariance;
    }

    public double getRSQuared() {
        return this.rSquared;
    }

    public double getSigma() {
        return this.sigma;
    }

    public double getMinOfResiduals() {
        return this.residuals != null ? min(this.residuals) : 0.0;
    }

    public double getMaxOfResiduals() {
        return this.residuals != null ? max(this.residuals) : 0.0;
    }

    public double getMedianOfResiduals() {
        Median m = new Median();

        double med = this.residuals != null ? m.evaluate(this.residuals) : 0.0;

        return med;

    }

    public ChillerModelStats getModelStats() {

        if (this.residuals == null) {
            return null;
        }

        double min = min(this.residuals);
        double max = max(this.residuals);

        Percentile p = new Percentile();
        Arrays.sort(this.residuals);

        double median = p.evaluate(this.residuals);
        p.setQuantile(25.0);
        double q1 = p.evaluate(this.residuals);
        p.setQuantile(75.0);
        double q3 = p.evaluate(this.residuals);

        ChillerModelStats s = new ChillerModelStats(min, q1, median, q3, max);

        return s;

    }

    //Starting from the end of the list, get 288 oberservations
    //with valid oatwb, ie, set the starting and ending numbers
    //such that when iterating these observations, 288 observations
    //will be found where OATWB > 10.0
    private void setStartStopOBSNumbersForPredictions() {

        long lastDayNumber = 0;
        for (int obs : history.keySet()) {
            if (history.get(obs).getDayNumber() > lastDayNumber) {
                lastDayNumber = history.get(obs).getDayNumber();
            }
        }

        //back up to the previous day so that we get 1 full day's worth of records
        lastDayNumber--;

        int stop = 0;
        for (int obs : history.keySet()) {

            if (history.get(obs).getDayNumber() > lastDayNumber) {
                continue;
            }

            if (obs > stop) {
                stop = obs;
            }

        }

        int countOfValidRows = 0;
        int start = 0;
        for (int obs = stop; obs > 0; obs--) {

            IntervalInHistory interval = history.get(obs);

            if (interval.getOATWB() > 10.0) {
                countOfValidRows++;
                start = obs;
            }

            if (countOfValidRows == 288) {
                break;
            }
        }

        this.startingOBSForPredictions = start;
        this.endingOBSForPredictions = stop;
    }

    public void applyMeanCoeff(HashMap<Integer, ChillerModel> chillerModels) {

        if (!this.modelIsValid) {
            return;
        }

        for (EnumRegressorTypes ert : EnumRegressorTypes.values()) {

            Double mean = 0.0;
            int validChillerCount = 0;

            for (int id : chillerModels.keySet()) {
                ChillerModel m = chillerModels.get(id);
                if (!m.modelIsValid) {
                    continue;
                }
                validChillerCount++;
                mean += chillerModels.get(id).getRegressor(ert);
            }

            if (validChillerCount <= 0) {
                return;
            }

            mean /= validChillerCount;

            if (this.getRegressor(ert) == 0.0) {
                this.setRegressor(ert, mean);

            }

        }

    }

    //=======================================================================
    public int getCountOfValidIntervals() {
        return this.countOfValidIntervals;
    }

    private double[] getYVector() {

        double[] v = new double[model.size()];

        int i = 0;
        for (int obs : model.keySet()) {
            OLSRow o = model.get(obs);
            v[i++] = o.getKWPerTon();
        }
        return v;
    }

    private double[][] getXMatrix() {

        double[][] v = new double[model.size()][];

        int i = 0;
        for (int obs : model.keySet()) {
            OLSRow o = model.get(obs);
            v[i++] = o.getXArray();
        }
        return v;
    }

    public double getRegressor(EnumRegressorTypes ert) {

        if (this.beta == null || this.beta.length == 0) {
            return 0;
        }

        switch (ert) {
            case INTERCEPT:
                return this.beta[0];
            case TONS:
                return this.beta[1];
            case OATWB:
                return this.beta[2];
            case TOTAL_ACTIVE:
                return this.beta[3];
            case DAYTIME_FLAG:
                return this.beta[4];
            default:
                return 0; //can't get here
        }
    }

    private void setRegressor(EnumRegressorTypes ert, Double newValue) {

        switch (ert) {
            case INTERCEPT:
                this.beta[0] = newValue;
                break;
            case TONS:
                this.beta[1] = newValue;
                break;
            case OATWB:
                this.beta[2] = newValue;
                break;
            case TOTAL_ACTIVE:
                this.beta[3] = newValue;
                break;
            case DAYTIME_FLAG:
                this.beta[4] = newValue;
                break;
            default:
                break;
        }

    }

    public double[] getResiduals() {
        return this.residuals;
    }

    private boolean intervalIsValid(IntervalInHistory interval) {

        Chiller c = interval.getChiller(this.chillerId);

        if (!c.getIsOn()) {
            return false;
        }

        if (c.getChillerValue(EnumChillerValues.TONS) <= getChillerCapacity() * 0.3) {
            return false;
        }

        if (c.getChillerValue(EnumChillerValues.KW_PER_TON) <= 0.3) {
            return false;
        }

        if (c.getChillerValue(EnumChillerValues.KW_PER_TON) >= 1.0) {
            return false;
        }

        if (c.getChillerValue(EnumChillerValues.KW) <= 200.0) {
            return false;
        }

        if (interval.getOATWB() <= 10.0) {
            return false;
        }

        if (interval.getTotalActive() <= 0) {
            return false;
        }

        if (interval.getChillerTons() <= 200) {
            return false;
        }

        if (interval.getDayNumber() >= 61) {
            return false;
        }

        return true;
    }

    private Double getChillerCapacity() {

        return chillerCapacity;

    }

    public Double getChillerSize() {

        return chillerSize;

    }


    public Double getPlantGrossUp() {

        return plantGrossUp;

    }

    public HashMap<Integer, Prediction> getKWPerTonPredictions() {

        return this.predictions;
    }

    private void setKWPerTonPredictions() {

        predictions = new HashMap<>();

        this.sumOfKWPerTonPredicted = 0.0;

        int index = 0;

        for (int obs = this.startingOBSForPredictions; obs <= this.endingOBSForPredictions; obs++) {

            IntervalInHistory interval = history.get(obs);
            if (interval.getTotalActive() <= 0) {
                continue;
            }
            if (interval.getOATWB() <= 0) {
                continue;
            }

            Prediction p = new Prediction(index, this, interval);

            this.predictions.put(index, p);
            index++;

        }


        /*
         tempVar <-  get(paste0("CH", var, "tempPredict")) + 
         get(paste0("CH", var, "curve"))$coef[1] + 
         get(paste0("CH", var, "curve"))$coef[2]*(
         ((ChillerTons[i-j]*PlantGrossUp)/TotalActive[i-j])*
         get(paste0("CH",var,"Size"))
         ) + 
         get(paste0("CH", var, "curve"))$coef[3]*OATWB[i-j] + 
         get(paste0("CH", var, "curve"))$coef[4]*TotalActive[i-j] + 
         get(paste0("CH", var, "curve"))$coef[5]*day[i-j]
            
         print(tempVar)
      
            
         */
    }
}
