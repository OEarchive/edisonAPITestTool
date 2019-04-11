package Model.DataModels.Datapoints.simulator;

import Model.DataModels.Datapoints.EnumEdisonResolutions;
import org.joda.time.DateTime;
import org.joda.time.Hours;

public class DGArgs {

    private DateTime startDate;
    private DateTime endDate;
    private final String stationId;
    private final String stationName;
    private final String sendingSid;
    private EnumEdisonResolutions res;

    public DGArgs(
            DateTime startDate,
            DateTime endDate,
            String stationId,
            String stationName,
            String sendingSid,
            EnumEdisonResolutions res
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.stationId = stationId;
        this.stationName = stationName;
        this.sendingSid = sendingSid;
        this.res = res;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getSendingSid() {
        return sendingSid;
    }

    public EnumEdisonResolutions getRes() {
        return res;
    }

    public void setRes(EnumEdisonResolutions res) {
        this.res = res;
    }


    public int getNumberOfHours() {
        Hours hoursPeriod = Hours.hoursBetween(startDate, endDate);
        return hoursPeriod.getHours();
    }
}
