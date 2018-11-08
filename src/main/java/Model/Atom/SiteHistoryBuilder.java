package Model.Atom;

import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Instant;

import java.util.HashMap;

/**
 * Builds history with repeated calls to add() with chiller points or with site points.
 */
public class SiteHistoryBuilder extends HistoryBuilder {

    private final DateTimeZone dateTimeZone;
    private final HashMap<String, ChillerInfo> chillers = new HashMap<>();

    /**
     * Construct a site history builder with a given time zone offset
     * @param dateTimeZone  the time zone offset for these chillers
     */
    public SiteHistoryBuilder(DateTimeZone dateTimeZone) {
        this.dateTimeZone = dateTimeZone;
    }

    /**
     * Add site history to this builder.
     *
     * @param siteHistory  site history to add
     */
    public void add(SiteHistory siteHistory) {

        Instant start = Instant.parse("1582-10-15T12:00:00Z");  // Start of Gregorian cal...
        long firstDay = -1;

        SiteHistory.SiteHistoryIterator siteHistoryIterator = siteHistory.getIterator();
        while (siteHistoryIterator.hasNext()) {

            SiteHistory.Row row = siteHistoryIterator.next();

            Instant ts = row.getTimestamp();
            long day = Days.daysBetween(start, ts).getDays();

            if (firstDay == -1) {
                firstDay = day;
            }
            long dayAdjusted = day - firstDay + 1;

            if (siteHistory.isForChiller()) {
                addChiller(row.getObsNumber(), row.getTimestamp(), dateTimeZone, dayAdjusted, row.getOatwb(),
                        siteHistory.getName(), row.getIsOn(), row.getChwst(), row.getChwrt(), row.getChwflo(),
                        row.getKw(), row.getTons(), row.getKwPerTon());
                chillers.put(siteHistory.getName(), new ChillerInfo(siteHistory.getName(), siteHistory.getCapacity()));
            } else {
                add(row.getObsNumber(), row.getTimestamp(), dateTimeZone, dayAdjusted, row.getOatwb());
            }

        }

    }

    public void generateChillerModels() {

        double maxCapacity = 0.0;
        double siteCapacity = 0.0;

        // Get total capacity for the site, and note the maximum capacity of a chiller because
        // we'll use that to determine a "hypothetical" capacity where all chillers have the
        // same capacity
        for (String name : chillers.keySet()) {
            ChillerInfo chillerInfo = chillers.get(name);
            siteCapacity += chillerInfo.getChillerCapacity();
            if (chillerInfo.getChillerCapacity() > maxCapacity) {
                maxCapacity = chillerInfo.getChillerCapacity();
            }
        }

        // Calculate a factor for sites where chillers have different capacities
        double hypotheticalSiteCapacity = chillers.size() * maxCapacity;
        double siteGrossUp;
        if (Double.compare(siteCapacity, 0.0) != 0) {
            siteGrossUp = hypotheticalSiteCapacity / siteCapacity;
        } else {
            siteGrossUp = 0.0;
        }

        // Now, create chiller models
        for (String name: chillers.keySet()) {
            ChillerInfo chillerInfo = chillers.get(name);
            double chillerSize;
            if (Double.compare(maxCapacity, 0.0) != 0) {
                chillerSize = chillerInfo.getChillerCapacity() / maxCapacity;
            } else {
                chillerSize = 0.0;
            }
            addChillerModel(name, chillerInfo.getChillerCapacity(), chillerSize, siteGrossUp);
        }

        applyMeanCoefficients();

    }

    class ChillerInfo {
        private final String name;
        private final double chillerCapacity;

        public ChillerInfo(String name, double chillerCapacity) {
            this.name = name;
            this.chillerCapacity = chillerCapacity;
        }

        public double getChillerCapacity() {
            return chillerCapacity;
        }

        public String getName() {
            return name;
        }
    }
}
