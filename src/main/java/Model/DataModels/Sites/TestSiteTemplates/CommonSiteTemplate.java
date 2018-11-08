package Model.DataModels.Sites.TestSiteTemplates;

import Model.DataModels.Sites.Address;
import Model.DataModels.Sites.Chiller;
import Model.DataModels.Sites.ChillerTypes.EnumChillerTypes;
import Model.DataModels.Sites.Contact;
import Model.DataModels.Sites.EdgePlantInfo;
import Model.DataModels.Sites.Enhancement;
import Model.DataModels.Sites.EnumCommProtocols;
import Model.DataModels.Sites.EnumProducts;
import Model.DataModels.Sites.HeatExchanger;
import Model.DataModels.Sites.Phone;
import Model.DataModels.Sites.PlantEquipment;
import Model.DataModels.Sites.Pump;
import Model.DataModels.Sites.Site;
import Model.DataModels.Sites.StationLicense;
import Model.DataModels.Users.TestUsers.EnumTestUsers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CommonSiteTemplate  {
    
    private final SiteTemplateInfo siteInfo;
    private final Site site;

    public CommonSiteTemplate(String customerSid, String siteName, String extSfId, SiteTemplateInfo siteInfo ) {

        this.site = new Site();
        
        this.siteInfo = siteInfo;
        
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

        Contact contactA = new Contact("sales", "hal.wilkinson@optimumenergyco.com", "Hal", "Hal", "Wilkinson", "halw", phones);
        Contact contactB = new Contact("support", "clark.matthys@optimumenergyco.com", "Clark", "Clark", "Matthys", "clarkm", phones);
        Contact contactC = new Contact("site", "larry.fetsch@optimumenergyco.com", "Larry", "Larry", "Fetsch", "larryf", phones);

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

        //chillerTypeAndCounts
        for (EnumChillerTypes ct : siteInfo.getChillerTypeAndCounts().keySet()) {
            int chillerCount = siteInfo.getChillerTypeAndCounts().get(ct);
            for (int i = 0; i < chillerCount; i++) {
                String chillerName = "Chiller_" + Integer.toString(i);
                chillers.add(new Chiller(chillerName, ct.getMake(), ct.getModel(), 1980 + i, 1000 + (100 * i), 200 - (i * 10), i * 10));
            }
        }

        pe.setChillers(chillers);

        pe.setCoolingTowers(getPumpList("CoolingTower", siteInfo.getCoolingTowerCount()));
        pe.setCondenserWaterPumps(getPumpList("CondenserPump", siteInfo.getCondenserCount()));
        pe.setPrimaryWaterPumps(getPumpList("PrimaryPump", siteInfo.getPrimaryChilledWaterPumpsCount()));
        pe.setSecondaryWaterPumps(getPumpList("SecondaryPump", siteInfo.getSecondaryChilledWaterPumpsCount() ));
        pe.setHeatExchangers(getHeatExchangers("HeatExchanger", siteInfo.getHeatExchangerCount()));

        return pe;

    }

    private List<Pump> getPumpList(String name, int count) {
        List<Pump> pumps = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            pumps.add(new Pump(name + "_" + Integer.toString(i), 200 + 10 * (i + 1), 300 + 10 * (i + 1)));
        }
        return pumps;
    }

    private List<HeatExchanger> getHeatExchangers(String name, int count) {
        List<HeatExchanger> heaters = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            heaters.add(new HeatExchanger(name + "_" + Integer.toString(i)));

        }
        return heaters;
    }

    public Site getSite() {
        return site;
    }

}
