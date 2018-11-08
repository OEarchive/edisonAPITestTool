package Model.DataModels.Sites;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrendOptimizationGraph {

    @JsonProperty("bascommfail")
    private int bascommfail;

    @JsonProperty("disabled")
    private int disabled;

    @JsonProperty("partial")
    private int partial;

    @JsonProperty("off")
    private int off;

    @JsonProperty("full")
    private int full;

    public int getBascommfail() {
        return bascommfail;
    }

    public int getDisabled() {
        return disabled;
    }

    public int getPartial() {
        return partial;
    }

    public int getOff() {
        return off;
    }

    public int getFull() {
        return full;
    }

}
