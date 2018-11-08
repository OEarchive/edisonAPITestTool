package Model.DataModels.Atom;

import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class AtomInfo {

    private final String customerSid;
    private final String siteSid;
    private final DateTimeZone timeZoneId;
    private final int numOfHours;
    private final DateTime commissionDate;

    private final String OATWBName;
    private final List<AtomChillerInfo> chillersInfo;

    public AtomInfo(
            String customerSid,
            String siteSid,
            DateTimeZone timeZoneId,
            int numOfHours,
            DateTime commissionDate,
            String OATWBName,
            List<AtomChillerInfo> chillersInfo) {

        this.customerSid = customerSid;
        this.siteSid = siteSid;
        this.timeZoneId = timeZoneId;
        this.numOfHours = numOfHours;
        this.commissionDate = commissionDate;
        this.OATWBName = OATWBName;
        this.chillersInfo = chillersInfo;

    }

    public String getCustomerSid() {
        return this.customerSid;
    }

    public String getSiteSid() {
        return this.siteSid;
    }

    public DateTimeZone getTimeZoneID() {
        return this.timeZoneId;
    }

    public int getNumOfHours() {
        return this.numOfHours;
    }

    public DateTime getCommissionDate() {
        return this.commissionDate;
    }

    public String getOATBName() {
        return this.OATWBName;
    }

    public List<AtomChillerInfo> getChillersInfo() {
        return this.chillersInfo;
    }

}
