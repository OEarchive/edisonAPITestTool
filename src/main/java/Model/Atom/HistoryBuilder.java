package Model.Atom;

import org.joda.time.DateTimeZone;
import org.joda.time.Instant;

import java.util.HashMap;

/**
 * This is a base class for building chiller histories.
 */
public abstract class HistoryBuilder {


    private final HashMap<Integer, IntervalInHistory> history = new HashMap<>();
    private HashMap<Integer, ChillerModel> chillerModels = new HashMap<>();

    /**
     * Get the interval histories.
     *
     * @return List of interval histories
     */
    public HashMap<Integer, IntervalInHistory> getHistory() {
        return this.history;
    }

    /**
     * Get chiller models;
     * @return List of chiller models
     */
    public HashMap<Integer, ChillerModel> getChillerModels() {
        return this.chillerModels;
    }

    /**
     * Subclasses must implement this method and call addChillerModel() for each chiller.
     */
    public abstract void generateChillerModels();

    /**
     * Adds the given interval history to this collection.
     *
     * @param obsNumber           The OBS number for this interval
     * @param intervalInHistory   The interval history to add
     */
    protected void add(Integer obsNumber, IntervalInHistory intervalInHistory) {
        this.history.put(obsNumber, intervalInHistory);
    }

    /**
     * Add OAWTB to an interval.
     *
     * This creates the IntervalInHistory or updates an existing one for the given
     * obsNumber.
     *
     * @param obsNumber   The OBS number of the interval history
     * @param ts          Timestamp of this interval
     * @param tz          Timezone of this interval
     * @param day         The day of this interval
     * @param oatwb       The OATWB for this interval
     */
    protected void add(Integer obsNumber, Instant ts, DateTimeZone tz, long day, double oatwb) {
        getOrCreate(obsNumber, ts, tz, day, oatwb);
    }

    /**
     * Add or update an interval for the given chiller points.
     *
     * @param obsNumber   The OBS number of the interval history
     * @param ts          Timestamp of this interval
     * @param tz          Timezone of this interval
     * @param day         The day of this interval
     * @param oatwb       The OATWB for this interval
     * @param name        The name of the chiller
     * @param isOn        Whether or not the chiller was on for this interval
     * @param chwst       The CHWST for this interval
     * @param chwrt       The CHWRT for this interval
     * @param chwflo      The CHWFLO for this interval
     * @param chkw        The CHkW for this interval
     * @param tons        The tons for this interval
     * @param kwPerTon    The kw per tons for this interval
     */
    protected void addChiller(Integer obsNumber, Instant ts, DateTimeZone tz, long day, double oatwb,
                       String name, boolean isOn, double chwst, double chwrt, double chwflo, double chkw,
                       double tons, double kwPerTon) {
        // FIXME: find a way to use the given chiller name
        int chillerId = getChillerId(name);
        IntervalInHistory intervalInHistory = getOrCreate(obsNumber, ts, tz, day, oatwb);

        Chiller chiller = new Chiller(chillerId, isOn, chwst, chwrt, chwflo, chkw, tons, kwPerTon);
        intervalInHistory.add(chiller, get(obsNumber - 1));
    }

    protected void addChillerModel(String name, double chillerCapacity, double chillerSize, double plantGrossUp) {
        // FIXME: find a way to use the given chiller name
        int chillerId = getChillerId(name);
        addChillerModel(chillerId, chillerCapacity, chillerSize, plantGrossUp);
    }

    protected void addChillerModel(int chillerId, double chillerCapacity, double chillerSize, double plantGrossUp) {
        // FIXME: find a way to use the given chiller name
        chillerModels.put(chillerId, new ChillerModel(chillerId, history, chillerCapacity, chillerSize, plantGrossUp));
    }

    protected void applyMeanCoefficients() {
        for (int index = 0; index < chillerModels.size(); index++) {
            int chillerId = index + 1;
            chillerModels.get(chillerId).applyMeanCoeff(chillerModels);
        }
    }

    IntervalInHistory get(Integer obsNumber) {
        return history.containsKey(obsNumber) ? history.get(obsNumber) : null;
    }

    IntervalInHistory getOrCreate(Integer obsNumber, Instant ts, DateTimeZone tz, long day, double oatwb) {
        IntervalInHistory intervalInHistory;
        if (history.containsKey(obsNumber)) {
            intervalInHistory = this.history.get(obsNumber);
        } else {
            intervalInHistory = new IntervalInHistory(obsNumber, ts, tz, day, oatwb);
            history.put(obsNumber, intervalInHistory);
        }
        if (Double.compare(oatwb, 0.0) != 0) {
            intervalInHistory.setOATWB(oatwb);
        }
        return intervalInHistory;
    }

    private int getChillerId(String name) {
        return Integer.parseInt(name.toLowerCase().replace("chiller", ""));
    }
}
