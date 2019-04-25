
package View.Sites.EditSite.A_History.Tesla.TeslaHistory.HistoryPoints;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.TeslaModels.MappingTableRow;
import Model.DataModels.TeslaModels.TeslaDPServiceDatapoint;
import Model.DataModels.TeslaModels.EnumMapStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.AbstractTableModel;


public class HistoryPointsTableModel extends AbstractTableModel {

    private final List<MappingTableRow> mappingTableRows;

    public HistoryPointsTableModel(List<DatapointsAndMetadataResponse> edisonPoints,
            String selectedSid, List<TeslaDPServiceDatapoint> listOfStationDatapoints,
            boolean showAllTesla,
            boolean ignoreGarbage,
            boolean useRegEx,
            String edisonFilter) {
        super();

        List<MappingTableRow> allRows = getAllRows(edisonPoints, listOfStationDatapoints );

        List<MappingTableRow> filteredList = new ArrayList<>();
        for (MappingTableRow row : allRows) {

            if (matchesFilter(row, edisonFilter, ignoreGarbage, useRegEx )) {
                filteredList.add(row);
            }

        }


        for (MappingTableRow tableRow : filteredList) {

            if (tableRow.getEdsionSid().contentEquals("?") && tableRow.getTeslaID().contentEquals("?")) {
                tableRow.setMapStatus(EnumMapStatus.NoInfo);
            }

            if (tableRow.getEdsionSid().contentEquals("?") && !tableRow.getTeslaID().contentEquals("?")) {
                tableRow.setMapStatus(EnumMapStatus.NoEdisonInfo);
            }

            if (!tableRow.getEdsionSid().contentEquals("?") && tableRow.getTeslaID().contentEquals("?")) {
                tableRow.setMapStatus(EnumMapStatus.NoTeslaInfo);
            }

            if (!tableRow.getEdsionSid().contentEquals("?") && !tableRow.getTeslaID().contentEquals("?")) {
                tableRow.setMapStatus(EnumMapStatus.Mapped);
            }

        }
        
        mappingTableRows = new ArrayList<>();
        for (MappingTableRow tableRow : filteredList) {
            if (selectedSid.compareTo("All") == 0 || tableRow.getEdsionSid().compareTo(selectedSid) == 0 || tableRow.getEdsionSid().compareTo("?") == 0) {
                
                if( tableRow.getMapStatus() == EnumMapStatus.NoEdisonInfo && !showAllTesla ){
                    continue;
                }
                mappingTableRows.add(tableRow);
            }
        }
        
        
        for (MappingTableRow tableRow : mappingTableRows) {

            if (tableRow.getMapStatus() == EnumMapStatus.NoTeslaInfo) {

                String overriddenName = getTeslaNameOverride(tableRow.getEdsionShortName());

                if (overriddenName.contentEquals("Ignore")) {
                    tableRow.setTeslaName("Ignore");
                    tableRow.setMapStatus(EnumMapStatus.Overridden);
                } else if (!overriddenName.contentEquals("?")) {
                    if (listOfStationDatapoints != null) {
                        for (TeslaDPServiceDatapoint tdp : listOfStationDatapoints) {
                            if (tdp.getShortName().contentEquals(overriddenName)) {
                                tableRow.setTeslaName(tdp.getShortName());
                                tableRow.setTeslaID(tdp.getPointType());
                                tableRow.setTeslaID(tdp.getId());
                                tableRow.setMapStatus(EnumMapStatus.Overridden);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private List<MappingTableRow> getAllRows( List<DatapointsAndMetadataResponse> edisonPoints, List<TeslaDPServiceDatapoint> listOfStationDatapoints ){
                    List<MappingTableRow> allRows = new ArrayList<>();

        for (DatapointsAndMetadataResponse edisonPoint : edisonPoints) {
            MappingTableRow row = new MappingTableRow();
            row.setEdisonShortName(edisonPoint.getName());
            row.setEdisonSid(edisonPoint.getSid());
            allRows.add(row);
        }

        if (listOfStationDatapoints != null) {
            for (TeslaDPServiceDatapoint teslaPoint : listOfStationDatapoints) {
                setTeslaInfoOrAddRow(teslaPoint, allRows);
            }
        }
        
        return allRows;
        
    }



    private boolean matchesFilter(MappingTableRow row, String filter, boolean ignoreGarbage, boolean useRegEx ) {

        if (ignoreGarbage && isEdisonCOVPoint(row.getEdsionShortName())) {
            return false;
        }
        
        if (filter.length() == 0) {
            return true;
        }
        
        if( matchesOnName( useRegEx, filter, row.getEdsionShortName() )){
            return true;
        }
        
        if( matchesOnName( useRegEx, filter, row.getTeslaName() )){
            return true;
        }

        return false;
    }

    private boolean matchesOnName(boolean useRegEx, String filter, String pointName) {

        if (!useRegEx && pointName.contains(filter)) {
            return true;
        }

        if (useRegEx) {
            Pattern r = Pattern.compile(filter);
            Matcher m = r.matcher(pointName);
            if (m.find()) {
                return true;
            }
        }

        return false;
    }
    
    private boolean isEdisonCOVPoint(String pointName) {

        String filter = ".*_COV$";

        Pattern r = Pattern.compile(filter);
        Matcher m = r.matcher(pointName);
        if (m.find()) {
            return true;
        }

        return false;
    }

    private String getTeslaNameOverride(String edisonName) {

        Map<String, String> overrides = new HashMap<>();
        overrides.put("Status", "Ignore");
        overrides.put("OEWATCHDOG", "Ignore");
        overrides.put("OptimumControl", "OptimizationControlDisabled");
        overrides.put("EDGEMODE", "Ignore");
        overrides.put("ChillerCount", "Ignore");
        overrides.put("EDGEREADY", "CLGMODE");
        overrides.put("BASCommunicationFailure", "COMLOSSBAS");

        overrides.put("OptimizationStatusEnum", "Ignore");
        overrides.put("SiteCommunicationFailure", "Ignore");
        overrides.put("ChilledWaterSupplyTemp_EventLog", "Ignore");
        overrides.put("StaleHistory", "Ignore");
        overrides.put("TestAlarm", "Ignore");
        overrides.put("CHWSTSPNotOptimized", "Ignore");
        //overrides.put("OptimumControl", "Ignore");

        if (overrides.containsKey(edisonName)) {
            return overrides.get(edisonName);
        }

        return "?";

    }



    private void setTeslaInfoOrAddRow(TeslaDPServiceDatapoint teslaPoint, List<MappingTableRow> allRows) {

        for (MappingTableRow mtr : allRows) {
            if (mtr.getEdsionShortName().compareToIgnoreCase(teslaPoint.getShortName()) == 0) {
                mtr.setTeslaName(teslaPoint.getShortName());
                mtr.setTeslaID(teslaPoint.getId());
                mtr.setTeslaType(teslaPoint.getPointType());
                return;
            }
        }

        MappingTableRow row = new MappingTableRow();
        row.setTeslaName(teslaPoint.getShortName());
        row.setTeslaID(teslaPoint.getId());
        row.setTeslaType(teslaPoint.getPointType());
        allRows.add(row);

    }

    public MappingTableRow getRow(int modelIndex) {
        return mappingTableRows.get(modelIndex);
    }

    public List<MappingTableRow> getMappedRows() {
        List<MappingTableRow> mappedRows = new ArrayList<>();
        for (MappingTableRow row : mappingTableRows) {
            if (row.getMapStatus() == EnumMapStatus.Mapped && row.getTeslaType().contentEquals("raw")) {
                mappedRows.add(row);
            }
        }

        return mappedRows;
    }

    @Override
    public int getRowCount() {
        return mappingTableRows.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumHistoryPointsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumHistoryPointsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumHistoryPointsTableColumns enumCol = EnumHistoryPointsTableColumns.getColumnFromColumnNumber(columnIndex);

        MappingTableRow dataRow = mappingTableRows.get(rowIndex);

        switch (enumCol) {
            case MapStatus:
                val = dataRow.getMapStatus().name();
                break;
            case EdisonName:
                val = dataRow.getEdsionShortName();
                break;
            case EdsionSid:
                val = dataRow.getEdsionSid();
                break;
            case TeslaName:
                val = dataRow.getTeslaName();
                break;
            case TeslaType:
                val = dataRow.getTeslaType();
                break;
            case TeslaID:
                val = dataRow.getTeslaID();
                break;

        }

        return val;
    }
}
