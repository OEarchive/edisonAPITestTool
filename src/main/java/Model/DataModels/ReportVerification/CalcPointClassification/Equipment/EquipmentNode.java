
package Model.DataModels.ReportVerification.CalcPointClassification.Equipment;

import Model.DataModels.Graph.EnumGraphNodeTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EquipmentNode {


    private final String sid;
    private final EnumGraphNodeTypes nodeType;
    private final String name;
    private final List<Map> datapoints;
    private final List<String> tags;

    public EquipmentNode( String sid, EnumGraphNodeTypes nodeType, String name ){
        this.sid = sid;
        this.nodeType = nodeType;
        this.name = name;
        
        this.datapoints = new ArrayList<>();
        this.tags = new ArrayList<>();
    }
    
    public String getSid() {
        return sid;
    }
    
    public EnumGraphNodeTypes getNodeType() {
        return nodeType;
    }

    public String getName() {
        return name;
    }

    public List<Map> getDatapoints() {
        return datapoints;
    }
    
    public List<String> getTags() {
        return tags;
    }
}