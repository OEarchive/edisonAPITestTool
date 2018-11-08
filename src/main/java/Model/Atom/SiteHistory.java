package Model.Atom;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of points relating to a site, including chiller points.
 *
 */
public class SiteHistory {

    public static final int DEFAULT_IS_ON = 0;
    public static final double DEFAULT_OATWB = -1.0;
    public static final double DEFAULT_CHWST = 0.0;
    public static final double DEFAULT_CHWRT = 0.0;
    public static final double DEFAULT_CHWFLO = 0.0;
    public static final double DEFAULT_CHKW = 0.0;

    private enum RowType {
        Site,
        Chiller,
        Unknown
    }

    private final String name;
    private final double capacity;
    private final DateTime[] timeStamps;
    private final Map<String, double[]> points;

    public SiteHistory(String name, DateTime[] timeStamps, Map<String, double[]> points) {
        this(name, 0.0, timeStamps, points);
    }

    public SiteHistory(String name, double capacity, DateTime[] timeStamps, Map<String, double[]> points) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is a required argument");
        }
        if (timeStamps == null) {
            throw new IllegalArgumentException("timeStamps is a required argument");
        }
        if (points == null) {
            throw new IllegalArgumentException("points is a required argument");
        }

        this.name = name;
        this.capacity = capacity;
        this.timeStamps = timeStamps;
        this.points = points;

        if (getRowType() == RowType.Unknown) {
            throw new IllegalArgumentException("points does not contain the expected list of columns");
        }
    }

    public String getName() {
        return name;
    }

    public double getCapacity() {
        return capacity;
    }

    public boolean isForChiller() {
        return getRowType() == RowType.Chiller;
    }

    private RowType getRowType() {
        RowType rowType = RowType.Unknown;

        if (points.size() == 1) {
            if (points.containsKey("OATWB")) {
                rowType = RowType.Site;
            }
        } else if (points.size() == 5) {
            if (points.containsKey("CHSBoolean") &&
                    points.containsKey("CHWST") &&
                    points.containsKey("CHWRT") &&
                    points.containsKey("CHWFLO") &&
                    points.containsKey("CHkW")) {
                rowType = RowType.Chiller;
            }
        } else if (points.size() == 6) {
            if (points.containsKey("OATWB") &&
                    points.containsKey("CHSBoolean") &&
                    points.containsKey("CHWST") &&
                    points.containsKey("CHWRT") &&
                    points.containsKey("CHWFLO") &&
                    points.containsKey("CHkW")) {
                rowType = RowType.Chiller;
            }
        }
        return rowType;
    }

    SiteHistoryIterator getIterator() {
        return new SiteHistoryIterator(timeStamps, points);
    }

    /**
     * Helps to iterate over rows within a group of columns.
     */
    public class SiteHistoryIterator {

        private DateTime[] timeStamps;
        private Map<String, double[]> columns;
        private int rowNumber = 0;
        private int max;

        protected SiteHistoryIterator(DateTime[] timeStamps, Map<String, double[]> columns) {
            // all columns should have the same length but if not, use the length of the smallest column
            this.max = Integer.MAX_VALUE;
            
            for( double []values : columns.values() ){
                if (values.length < this.max) {
                    this.max = values.length;
                }  
            }


            if (timeStamps.length < this.max) {
                this.max = timeStamps.length;
            }

            this.columns = columns;
            this.timeStamps = timeStamps;
        }

        public boolean hasNext() {
            return rowNumber < max;
        }

        public Row next() {
            double[] isOnColumn = columns.get("CHSBoolean");
            double[] oatWBColumn = columns.get("OATWB");
            double[] chwstColumn = columns.get("CHWST");
            double[] chwrtColumn = columns.get("CHWRT");
            double[] chwfloColumn = columns.get("CHWFLO");
            double[] kwColumn = columns.get("CHkW");

            Instant ts = timeStamps[rowNumber].toInstant();
            // The CHSBoolean is either 1 or 0 but may be represented as a double
            boolean isOn = Double.compare(getDoubleOrElse(isOnColumn, rowNumber, DEFAULT_IS_ON), 1.0) == 0;
            double oatwb = getDoubleOrElse(oatWBColumn, rowNumber, DEFAULT_OATWB);
            double chwst = getDoubleOrElse(chwstColumn, rowNumber, DEFAULT_CHWST);
            double chwrt = getDoubleOrElse(chwrtColumn, rowNumber, DEFAULT_CHWRT);
            double chwflo = getDoubleOrElse(chwfloColumn, rowNumber, DEFAULT_CHWFLO);
            double chkw = getDoubleOrElse(kwColumn, rowNumber, DEFAULT_CHKW);

            double tons = isOn ? calculateTons(chwst, chwrt, chwflo) : 0.0;
            double kwPerTon = isOn ? calculateKwPerTon(chkw, tons) : 0.0;

            // Out of bounds [ATOM-31]
            if (Double.compare(oatwb, -50.0) < 0 || Double.compare(oatwb, 150) > 0) {
                // FIXME: add a comment
                oatwb = DEFAULT_OATWB;
            }

            int obs = rowNumber + 1;
            Row row = new Row(obs, ts, oatwb, isOn, chwst, chwrt, chwflo, chkw, tons, kwPerTon);

            // advance the iterator
            rowNumber++;

            return row;
        }

        private double calculateTons(double chwst, double chwrt, double chwflo) {
            double tons = ((chwrt - chwst) * chwflo) / 24.0;

            // Out of Bounds check: ATOM-31
            if (Double.compare(tons, 0.0) < 1 || Double.compare(tons, 4200) > 0) {
                // FIXME: add a comment
                tons = 0.0;
            }

            return tons;
        }

        private double calculateKwPerTon(double chkw, double tons) {
            double kwPerTon;
            if (Double.compare(tons, 0.0) != 0 && Double.compare(tons, -0.0) != 0) {
                kwPerTon = chkw / tons;
            } else {
                kwPerTon = 0.0;
            }

            // Out of Bounds check: ATOM-31
            if (Double.compare(kwPerTon, 0.0) < 1 || Double.compare(kwPerTon, 10.0) > 0) {
                // FIXME: add a comment
                kwPerTon = 0.0;
            }

            return kwPerTon;
        }

        private double getDoubleOrElse(double[] values, int rowNumber, double defaultValue) {
            try {
                return values[rowNumber];
            } catch (Exception ex) {
                return defaultValue;
            }
        }
    }

    public class Row {

        private final int obsNumber;
        private final Instant ts;
        private final double oatwb;
        private final boolean isOn;
        private final double chwst;
        private final double chwrt;
        private final double chwflo;
        private final double kw;
        private final double tons;
        private final double kwPerTon;

        public Row(int obsNumber, Instant ts, double oatwb, boolean isOn, double chwst,
                   double chwrt, double chwflo, double kw, double tons, double kwPerTon) {
            this.obsNumber = obsNumber;
            this.ts = ts;
            this.oatwb = oatwb;
            this.isOn = isOn;
            this.chwst = chwst;
            this.chwrt = chwrt;
            this.chwflo = chwflo;
            this.kw = kw;
            this.tons = tons;
            this.kwPerTon = kwPerTon;
        }

        public int getObsNumber() {
            return obsNumber;
        }

        public Instant getTimestamp() {
            return ts;
        }

        public double getOatwb() {
            return oatwb;
        }

        public boolean getIsOn() {
            return isOn;
        }

        public double getChwst() {
            return chwst;
        }

        public double getChwrt() {
            return chwrt;
        }

        public double getChwflo() {
            return chwflo;
        }

        public double getKw() {
            return kw;
        }

        public double getTons() {
            return tons;
        }

        public double getKwPerTon() {
            return kwPerTon;
        }
    }
}
