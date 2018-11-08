package View.Sites.EditSite.E_Views.ViewLiveData;

import Model.DataModels.Views.Point;
import org.joda.time.DateTime;

public class ViewLiveDataTableRow {

    private final String sid;
    private final String name;
    private final String stationName;
    private final String uom;
    private final String measure;

    private Object pushValue;
    private Object liveValue;
    
    private DateTime lastUpdated;
    private EnumStaleValues lastUpdateStaleness;

    private DateTime lastChanged;
    private EnumStaleValues lastChangedStaleness;

    public ViewLiveDataTableRow(String sid, Point point) {

        this.sid = sid;
        this.name = point.getName();
        this.stationName = point.getStationName();
        this.uom = point.getUOM();
        this.measure = point.getMeasure();

        this.pushValue = null;
        this.liveValue = "";
        
        this.lastUpdated = null;
        this.lastUpdateStaleness = EnumStaleValues.Green;
        this.lastChanged = null;
        this.lastChangedStaleness = EnumStaleValues.Green;
        
    }

    public String getSid() {
        return this.sid;
    }

    public String getName() {
        return this.name;
    }

    public String getStationName() {
        return this.stationName;
    }

    public String getUOM() {
        return this.uom;
    }

    public String getMeasure() {
        return this.measure;
    }
    
    public Object getPushValue(){
        return this.pushValue;
    }
    
    public void setPushValue( Object pushValue ){
        this.pushValue = pushValue;
    }

    public Object getLiveValue() {
        return this.liveValue;
    }

    public void setLiveValue(Object liveValue) {
        this.liveValue = liveValue;
    }
    
    public DateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(DateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public EnumStaleValues getLastUpdateStaleness() {
        return this.lastUpdateStaleness;
    }

    public void setLastUpdateStaleness(EnumStaleValues staleValue) {
        this.lastUpdateStaleness = staleValue;
    }
    

    
    public DateTime getLastChanged() {
        return this.lastChanged;
    }

    public void setLastChanged(DateTime lastChanged) {
        this.lastChanged = lastChanged;
    }
    
    public EnumStaleValues getLastChangedStaleness() {
        return this.lastChangedStaleness;
    }

    public void setLastChangedStaleness(EnumStaleValues lastChangedStaleness) {
        this.lastChangedStaleness = lastChangedStaleness;
    }

}
