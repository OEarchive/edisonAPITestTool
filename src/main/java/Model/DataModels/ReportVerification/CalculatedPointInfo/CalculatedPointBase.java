
package Model.DataModels.ReportVerification.CalculatedPointInfo;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;


public class CalculatedPointBase implements iCalculatedPoint {
    
    public Map<String, DatapointsAndMetadataResponse> nameToPointMap;
    public List<String> columnNames;
    public Map<String, EnumCalcColumnType > columnType;
    public List<DateTime> timeStamps;
    public Map< DateTime, Map<String, Object>> timestampToNameToValueMap;
    
    public CalculatedPointBase(){
        this.nameToPointMap = new HashMap<>();
        this.columnNames = new ArrayList<>();
        this.timeStamps = new ArrayList<>();
        this.timestampToNameToValueMap = new HashMap<>();
    }
    
    
    protected void setFilteredList( Map<String, DatapointsAndMetadataResponse> nameToPointMap ){
        this.nameToPointMap = nameToPointMap;
    }
    
    protected String getShortName(String sid, String name) {
        String uName = name;
        String[] pieces = sid.split("\\.");

        if (pieces.length > 2) {
            uName = pieces[2] + "." + uName;
        }
        return uName;
    }

    @Override
    public Map<String, DatapointsAndMetadataResponse> getNameToPointsMap() {
        return nameToPointMap;
    }

    @Override
    public List<String> computedColumnNames() {
        return columnNames;
    }

    @Override
    public List<DateTime> getTimeStamps() {
        return timeStamps;
    }

    @Override
    public Map<DateTime, Map<String, Object>> getTimestampToNameToValuesMap() {
        return timestampToNameToValueMap;
    }
}
