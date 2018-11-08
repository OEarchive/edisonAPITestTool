package Model.DataModels.Sites;

import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class SiteTemplate {

    private Site site;

    public SiteTemplate(String customerSid, String extSfId, String siteName) {
        this.site = new Site();

        site.setSid(customerSid);
        site.setName(siteName);

        site.setProduct(EnumProducts.edge.getName());

        site.setSFOppotunity("sf_opportunity_string");
        site.setExtSFID(extSfId);
        site.setCurrencyCode("Gold Doubloons");

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

        String softwareEnabledAtDateAsString = enabledAt.toString(fmt) + ".000Z";
        String activatedAtDateAsString = enabledAt.toString(fmt) + ".000Z";
        String expiresAtDateString = expiresAtDate.toString(fmt) + ".000Z";

        //2016-05-18T16:50:49.378Z
        StationLicense license = new StationLicense("subscription", softwareEnabledAtDateAsString, activatedAtDateAsString, expiresAtDateString);

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
        enhancements.add(new Enhancement("ChillerDiagnostics", true));
        enhancements.add(new Enhancement("PredictiveFreeCooling", true));
        enhancements.add(new Enhancement("ChillerSequencing", true));
        return enhancements;

    }

    private Address getAddress() {
        Address address = new Address();
        address.setStreet("2000 Park Street");
        address.setCity("Seattle");
        address.setState("WA");
        address.setCountry("USA");
        address.setPostCode("98200");
        address.setTimezone("America/Los_Angeles");
        address.setLatitude(100);
        address.setLongitude(200);
        return address;

    }

    private List<Contact> getContacts() {

        String role = "sales";

        Phone phone = new Phone();
        phone.setPhone("123-456-7890");
        phone.setPhoneType("cell");

        List<Phone> phones = new ArrayList<>();
        phones.add(phone);

        Contact contactA = new Contact(role, "hal.wilkinson@optimumenergyco.com", "Hal", "Hal", "Wilkinson", "halw", phones);
        Contact contactB = new Contact(role, "clark.matthys@optimumenergyco.com", "Clark", "Clark", "Matthys", "clarkm", phones);
        Contact contactC = new Contact(role, "larry.fetsch@optimumenergyco.com", "Larry", "Larry", "Fetsch", "larryf", phones);

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contactA);
        contacts.add(contactB);
        contacts.add(contactC);

        return contacts;
    }

    private EdgePlantInfo getEdgePlantInfo() {
        EdgePlantInfo pi = new EdgePlantInfo();

        pi.setCO2EmissionFactor(1.5);
        pi.setMinimumChilledWaterFlow(840.0);
        pi.setBlendedUtilityRate(10.2);
        pi.setChillerPlantCoolingCapacity(1570.0);
        pi.setChilledWaterDistType("Primary only with Headered CHW");
        pi.setCondenserWaterDistType("DedicatedCDWP_CommonSumpCT");
        pi.setCurrentDpSetPoint(11);
        pi.setChilledWaterMinimumDp(4);
        pi.setChilledWaterMaximumDp(20);
        pi.setChilledWaterDpOsaMin(54);
        pi.setChilledWaterDpOsaMax(80);
        pi.setChilledWaterStResetEnable(true);
        pi.setChilledWaterSupplyTempMin(44);
        pi.setChilledWaterSupplyTempMax(48);
        pi.setCurrentChilledWaterStSetPoint(46);
        pi.setSecondaryChilledWaterPumpMinSpeed(50);
        pi.setPrimaryChilledWaterPumpMinSpeed(50);
        pi.setCurrentEnteringCondenserWaterSetPoint(72);
        pi.setMinimumCondWaterEnteringTemp(60);
        pi.setMaximumCondWaterEnteringTemp(94);
        pi.setCondWaterPumpMinSpeed(60);

        return pi;

    }

    private PlantEquipment getEquipment() {
        PlantEquipment pe = new PlantEquipment();

        pe.setCommProtocol(EnumCommProtocols.bacnet.getName());

        List<Chiller> chillers = new ArrayList<>();

        int chillerNumber = 0;
        for (EnumChillerTypes ct : EnumChillerTypes.values()) {
            chillerNumber++;
            String chillerName = "Chiller_" + Integer.toString(chillerNumber);
            chillers.add(new Chiller(chillerName, ct.getMake(), ct.getModel(), 1980 + chillerNumber, 1000 + (100 * chillerNumber), 200 - (chillerNumber * 10), chillerNumber * 10));
        }

        pe.setChillers(chillers);

        pe.setCoolingTowers(getPumpList("CoolingTower"));
        pe.setCondenserWaterPumps(getPumpList("CondenserPump"));
        pe.setPrimaryWaterPumps(getPumpList("PrimaryPump"));
        pe.setSecondaryWaterPumps(getPumpList("SecondaryPump"));
        pe.setHeatExchangers(getHeatExchangers("HeatExchanger"));

        return pe;

    }

    private List<Pump> getPumpList(String name) {
        List<Pump> pumps = new ArrayList<>();
        pumps.add(new Pump(name + "_1", 210, 310));
        pumps.add(new Pump(name + "_2", 220, 320));
        pumps.add(new Pump(name + "_3", 230, 330));
        return pumps;
    }

    private List<HeatExchanger> getHeatExchangers(String name) {
        List<HeatExchanger> heaters = new ArrayList<>();
        heaters.add(new HeatExchanger(name + "_1"));
        heaters.add(new HeatExchanger(name + "_2"));
        heaters.add(new HeatExchanger(name + "_3"));
        return heaters;
    }

    public Site getSite() {
        return site;
    }

}
