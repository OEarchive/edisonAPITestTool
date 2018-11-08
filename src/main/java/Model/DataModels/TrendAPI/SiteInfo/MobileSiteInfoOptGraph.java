package Model.DataModels.TrendAPI.SiteInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MobileSiteInfoOptGraph {

    @JsonProperty("full")
    private int fullFlag;

    @JsonProperty("partial")
    private int partialFlag;

    @JsonProperty("not")
    private int notFlag;

    @JsonProperty("off")
    private int offFlag;

    public int getFullFlag() {
        return this.fullFlag;
    }

    public int getPartialFlag() {
        return this.partialFlag;
    }

    public int getNotFlag() {
        return this.notFlag;
    }

    public int getOffFlag() {
        return this.offFlag;
    }

}
