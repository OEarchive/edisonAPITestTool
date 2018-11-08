package Model.DataModels.DataPusherConfig;

import java.util.ArrayList;
import java.util.List;

public enum EnumDataPusherConfigs {

    DEMO("Demo on OEDEV",
            "c:whatcomindustries.s:demo-edge.st:1",
            "demo_f0258b66",
            "livepoints.xlsx",
            "THPH.THC.THCEDGE.THCEDGE.",
            "OEDEV"
    ),
    DEMO2("DEMO2 on PROD",
            "c:testcustomerdemo.s:demo2-edge.st:1",
            "demo2_358025ae",
            "livepointsDemo2.xlsx",
            "THPH.THC.THCEDGE.THCEDGE.",
            "PROD"
    ),
    
    ATOM("Push Atom data to Omnibus",
            "c:greenstreetindustries.s:greentree-edge.st:1",
            "greentree_803b03de",
            "VistakonAtomPoints.xlsx",
            "THPH.THC.THCEDGE.THCEDGE.",
            "OMNIBUS"
    );

    private final String ddName;
    private final String stationSid;
    private final String stationID;
    private final String fileName;
    private final String sqlPrefix;
    private final String target;

    EnumDataPusherConfigs(String ddName, String stationSid, String stationID, String fileName, String sqlPrefix, String target) {
        this.ddName = ddName;
        this.stationSid = stationSid;
        this.stationID = stationID;
        this.fileName = fileName;
        this.sqlPrefix = sqlPrefix;
        this.target = target;
    }

    public String getDDName() {
        return this.ddName;
    }

    public String getStationSid() {
        return this.stationSid;
    }

    public String getStationID() {
        return this.stationID;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getSqlPrefix() {
        return this.sqlPrefix;
    }
    
    public String getTarget(){
        return this.target;
    }

    static public List<String> geDDNames() {
        List<String> names = new ArrayList<>();
        for (EnumDataPusherConfigs u : EnumDataPusherConfigs.values()) {
            names.add(u.ddName);
        }
        return names;
    }

    static public EnumDataPusherConfigs getEnumFromDDNameString(String nameAsString) {
        for (EnumDataPusherConfigs u : EnumDataPusherConfigs.values()) {
            if (u.ddName.contains(nameAsString)) {
                return u;
            }
        }
        return null;
    }

}
