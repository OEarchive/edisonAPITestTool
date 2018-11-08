package Model.DataModels.Sites.TestSiteTemplates;

import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import java.util.Map;

public class SiteTemplateInfo {

    private final Map<EnumChillerTypes, Integer> chillerTypeAndCounts;
    private final int coolingTowerCount;
    private final int condenserCount;
    private final int primaryChilledWaterPumpsCount;
    private final int secondaryChilledWaterPumpsCount;
    private final int heatExchangerCount;

    public SiteTemplateInfo(
            Map<EnumChillerTypes, Integer> chillerTypeAndCounts,
            int coolingTowerCount,
            int condenserCount,
            int primaryChilledWaterPumpsCount,
            int secondaryChilledWaterPumpsCount,
            int heatExchangerCount
    ) {

        this.chillerTypeAndCounts = chillerTypeAndCounts;
        this.coolingTowerCount = coolingTowerCount;
        this.condenserCount = condenserCount;
        this.primaryChilledWaterPumpsCount = primaryChilledWaterPumpsCount;
        this.secondaryChilledWaterPumpsCount = secondaryChilledWaterPumpsCount;
        this.heatExchangerCount = heatExchangerCount;
    }

    public Map<EnumChillerTypes, Integer> getChillerTypeAndCounts() {
        return this.chillerTypeAndCounts;
    }

    public int getCoolingTowerCount() {
        return this.coolingTowerCount;
    }

    public int getCondenserCount() {
        return this.condenserCount;
    }

    public int getPrimaryChilledWaterPumpsCount() {
        return this.primaryChilledWaterPumpsCount;
    }

    public int getSecondaryChilledWaterPumpsCount() {
        return this.secondaryChilledWaterPumpsCount;
    }

    public int getHeatExchangerCount() {
        return this.heatExchangerCount;
    }

}
