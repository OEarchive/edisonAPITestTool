package Model.DataModels.Sites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SitePlainTemplate {

    private Site site;

    public SitePlainTemplate(String customerSid) {
        this.site = new Site();

        site.setSid(customerSid);
        site.setName("");

        site.setProduct(EnumProducts.edge.getName());

        site.setSFOppotunity("");
        site.setExtSFID("");

        site.setStationLicense(getLicense());
        site.setSubscription(getSubscription());
        site.setEnhancements(getEnhancements());
        site.setAddress(getAddress());
        site.setContacts(getContacts());
        site.setEdge(getEdgePlantInfo());
        site.setEquipment(getEquipment());

    }

    private StationLicense getLicense() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

        DateTime enabledAt = DateTime.now();
        DateTime expiresAtDate = enabledAt.plusMonths(10);

        String expiresAtDateString = expiresAtDate.toString(fmt) + ".000Z";
        String softwareEnabledAtString = enabledAt.toString(fmt) + ".000Z";
        String activatedAtDateAsString = enabledAt.toString(fmt) + ".000Z";

        //2016-05-18T16:50:49.378Z
        StationLicense license = new StationLicense("subscription", softwareEnabledAtString, activatedAtDateAsString, expiresAtDateString);

        return license;

    }

    private Map<String, String> getSubscription() {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

        DateTime activatedAtDate = DateTime.now().minus(DateTime.now().getMillisOfDay()).plusMillis(1000);
        activatedAtDate = activatedAtDate.minusWeeks(2);
        DateTime expiresAtDate = activatedAtDate.plusMonths(10);

        String activatedAtDateString = activatedAtDate.toString(fmt) + ".000Z";
        String expiresAtDateString = expiresAtDate.toString(fmt) + ".000Z";

        Map<String, String> subscription = new HashMap<>();

        subscription.put("activatedAt", activatedAtDateString);
        subscription.put("expiresAt", expiresAtDateString);

        return subscription;
    }

    private List<Enhancement> getEnhancements() {
        List<Enhancement> enhancements = new ArrayList<>();
        return enhancements;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setStreet("");
        address.setCity("");
        address.setState("");
        address.setCountry("");
        address.setPostCode("");
        address.setTimezone("");
        address.setLatitude(0);
        address.setLongitude(0);
        return address;
    }

    private List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        return contacts;
    }

    private EdgePlantInfo getEdgePlantInfo() {
        EdgePlantInfo pi = new EdgePlantInfo();
        pi.setCondenserWaterDistType("DedicatedCDWP_CommonSumpCT");
        pi.setChilledWaterDistType("fill me with a string!");

        return pi;

    }

    private PlantEquipment getEquipment() {
        PlantEquipment pe = new PlantEquipment();

        pe.setCommProtocol(EnumCommProtocols.bacnet.getName());

        List<Chiller> chillers = new ArrayList<>();
        pe.setChillers(chillers);

        pe.setCoolingTowers(new ArrayList<Pump>());
        pe.setCondenserWaterPumps(new ArrayList<Pump>());
        pe.setPrimaryWaterPumps(new ArrayList<Pump>());
        pe.setSecondaryWaterPumps(new ArrayList<Pump>());

        pe.setHeatExchangers(new ArrayList<HeatExchanger>());

        return pe;

    }

    public Site getSite() {
        return site;
    }
}
