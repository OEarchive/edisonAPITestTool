package Model.Atom;

import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.chrono.GregorianChronology;

public class IntervalInHistory {

    private final DateTime timeStamp;
    private final int OBS_Number;
    private final long DayNumber;
    private final boolean isDaytime;
    private final boolean isWeekendDay;
    private int totalActive;
    private double chillerTons;
    private double OATWB;
    private String comments;
    private final HashMap< Integer, Chiller> chillerList;

    public IntervalInHistory(int obsNumber, Instant ts, DateTimeZone tz, long dayNumberAdjusted, double oatwb) {
        int currentHour = ts.get(GregorianChronology.getInstance(tz).hourOfDay());
        int dayOfWeek = ts.get(GregorianChronology.getInstance(tz).dayOfWeek());

        this.timeStamp = ts.toDateTime().withZone(DateTimeZone.UTC);
        this.OBS_Number = obsNumber;
        this.DayNumber = dayNumberAdjusted;
        this.isDaytime = currentHour >= 6 && currentHour < 20;
        this.isWeekendDay = dayOfWeek == 6 || dayOfWeek == 7;
        this.totalActive = 0;
        this.chillerTons = 0.0;
        this.comments = "";
        this.chillerList = new HashMap<>();
        this.OATWB = oatwb;
    }

    public void add(Chiller chiller, IntervalInHistory previous) {
        if (chiller.getIsOn()) {
            chillerTons += chiller.getChillerValue(EnumChillerValues.TONS);
            totalActive++;
            if (previous != null) {
                Chiller previousChiller = previous.getChiller(chiller.getChillerId());
                if (previousChiller != null && !previousChiller.getIsOn()) {
                    // Chiller was previously "off" and now it is "on",
                    // so set the "added" flag
                    chiller.setAddFlag();
                }
            }
        }
        chillerList.put(chiller.getChillerId(), chiller);
    }

    public IntervalInHistory(int obsNumber, int dayNumberAdjusted, Map<?, ?> row) {

        this.timeStamp = new DateTime(row.get("Timestamp"), DateTimeZone.UTC);

        this.OBS_Number = obsNumber;
        this.DayNumber = dayNumberAdjusted;
        this.isDaytime = "1".equals(row.get("day"));
        this.isWeekendDay = "1".equals(row.get("Weekend"));

        this.OATWB = -1.0;
        try {
            this.OATWB = Double.parseDouble((String) row.get("OATWB"));
        } catch (Exception ex) {
            this.OATWB = -1.0;
        }

        this.totalActive = Integer.parseInt((String) row.get("TotalActive"));
        this.chillerTons = Double.parseDouble((String) row.get("ChillerTons"));
        this.comments = (String) row.get("Comments");

        this.chillerList = new HashMap<>();

        int chillerId = 1;
        boolean keepGoing = true;

        do {

            try {

                Chiller chiller = new Chiller(chillerId,
                        (String) row.get(String.format("Add%s", chillerId)),
                        (String) row.get(String.format("CH%sSBoolean", chillerId)),
                        (String) row.get(String.format("CH%sCHWST", chillerId)),
                        (String) row.get(String.format("CH%sCHWRT", chillerId)),
                        (String) row.get(String.format("CH%sCHWFLO", chillerId)),
                        (String) row.get(String.format("CH%skW", chillerId)),
                        (String) row.get(String.format("CH%sTon", chillerId)),
                        (String) row.get(String.format("CH%s_kWperTon", chillerId))
                );

                this.chillerList.put(chillerId, chiller);
                chillerId++;

            } catch (Exception ex) {
                keepGoing = false;
            }

        } while (keepGoing);
    }
    
    public int getNumberOfChillers(){
        return this.chillerList.size();
    }

    public DateTime getTimeStamp() {
        return this.timeStamp;
    }

    public int getOBS_Number() {
        return this.OBS_Number;
    }

    public long getDayNumber() {
        return this.DayNumber;
    }

    public boolean getIsDaytime() {
        return this.isDaytime;
    }

    public boolean getIsWeekendDay() {
        return this.isWeekendDay;
    }

    public int getTotalActive() {
        return this.totalActive;
    }

    public double getChillerTons() {
        return this.chillerTons;
    }

    public double getOATWB() {
        return this.OATWB;
    }

    public void setOATWB(double OATWB) {
        this.OATWB = OATWB;
    }

    public String getComments() {
        return this.comments;
    }

    public Chiller getChiller(int chillerId) {
        return this.chillerList.get(chillerId);
    }
}
