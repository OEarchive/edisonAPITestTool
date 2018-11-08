package Model.DataModels.ReportVerification.CalcPointClassification.Equipment;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.Graph.EnumGraphNodeTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquipmentInfo {

    //List<DatapointsAndMetadataResponse> listOfMetadata;
    String name;
    String sid;

    private Map< EnumGraphNodeTypes, List<EquipmentNode>> equipTypeToNodesMap;
    private Map< EnumGraphNodeTypes, List<String>> equipTypeToSidsMap;
    private Map< String, List<String>> pointNameToSidsMap;

    public EquipmentInfo(List<DatapointsAndMetadataResponse> listOfMetadata) {

        equipTypeToNodesMap = new HashMap<>();
        equipTypeToSidsMap = new HashMap<>();
        pointNameToSidsMap = new HashMap<>();

        for (DatapointsAndMetadataResponse resp : listOfMetadata) {

            for (Map map : resp.getDatapointAssociations()) {

                String assocPointName = (String) map.get("name");
                String sid = (String) map.get("sid");

                Pattern pattern = Pattern.compile("(.*e:)(\\D+)(\\d+)");

                Matcher m = pattern.matcher(sid);

                try {
                    if (m.find()) {

                        String equipSid = m.group(0);
                        //String equipNum = m.group(1);
                        String equipName = m.group(2);
                        String equipNumStr = m.group(3);

                        for (EnumGraphNodeTypes eType : EnumGraphNodeTypes.values()) {
                           
                            
                            if( !eType.getEdisonName().contentEquals(equipName) ){
                                continue;
                            }

                            String eName = eType.getEdisonName();

                            if (!equipTypeToSidsMap.containsKey(eType)) {
                                equipTypeToSidsMap.put(eType, new ArrayList<String>());
                            }

                            if (!equipTypeToSidsMap.get(eType).contains(equipSid)) {
                                equipTypeToSidsMap.get(eType).add(equipSid);
                            }

                            if (!pointNameToSidsMap.containsKey(assocPointName)) {
                                pointNameToSidsMap.put(assocPointName, new ArrayList<String>());
                            }

                            if (!pointNameToSidsMap.get(assocPointName).contains(equipSid)) {
                                pointNameToSidsMap.get(assocPointName).add(equipSid);
                            }

                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Problem patthern matching in Equipment Info");
                }

            }
        }
    }

    public Map< EnumGraphNodeTypes, List<String>> getEquipTypeToSidsMap() {
        return equipTypeToSidsMap;
    }

    public Map< String, List<String>> getPointNameToSidsMap() {
        return pointNameToSidsMap;
    }

}
