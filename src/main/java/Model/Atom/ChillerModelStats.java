package Model.Atom;

public final class ChillerModelStats {

    private final double min;
    private final double q1;
    private final double median;
    private final double q3;
    private final double max;

    public ChillerModelStats(
            double min,
            double q1,
            double median,
            double q3,
            double max) {
        this.min = min;
        this.q1 = q1;
        this.median = median;
        this.q3 = q3;
        this.max = max;
    }

    public double getMin() {
        return this.min;
    }

    public double getQ1() {
        return this.q1;
    }

    public double getMedian() {
        return this.median;
    }

    public double getQ3() {
        return this.q3;
    }

    public double getMax() {
        return this.max;
    }

}
