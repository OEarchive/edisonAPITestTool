package View.Sites.EditSite.A_History.PushToTesla.MappingTable;

import Model.DataModels.TeslaModels.MappingTableRow;
import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.TeslaModels.TeslaDataPoint;
import Model.DataModels.TeslaModels.TeslaEquipment;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.AbstractTableModel;

public class DataPointsTableModel extends AbstractTableModel {

    private final List<MappingTableRow> mappingTableRows;

    public DataPointsTableModel(List<DatapointsAndMetadataResponse> edisonPoints, String selectedSid, TeslaStationInfo stationInfo, boolean showAllTesla, boolean ignoreGarbage) {
        super();

        List<DatapointsAndMetadataResponse> filteredList = new ArrayList<>();

        for (DatapointsAndMetadataResponse point : edisonPoints) {

            if (!isEdisonGarbage(point)) {
                filteredList.add(point);
            }

            /*
            String uName = getUName(point.getSid(), point.getName());

            if (filter.length() == 0) {
                filteredList.add(point);
            } else if (!this.jCheckBoxUseRegEx.isSelected() && uName.contains(filter)) {
                filteredList.add(point);
            } else if (this.jCheckBoxUseRegEx.isSelected()) {
                Pattern r = Pattern.compile(filter);
                Matcher m = r.matcher(uName);
                if (m.find()) {
                    filteredList.add(point);
                }
            }
             */
        }

        List<MappingTableRow> allRows = new ArrayList<>();

        for (DatapointsAndMetadataResponse edisonPoint : filteredList) {
            MappingTableRow row = new MappingTableRow();
            row.setEdisonShortName(edisonPoint.getName());
            row.setEdisonSid(edisonPoint.getSid());
            allRows.add(row);
        }

        if (stationInfo != null) {
            for (TeslaDataPoint teslaPoint : stationInfo.getDatapoints()) {
                setMappingTableRow(teslaPoint, allRows, showAllTesla);
            }

            for (TeslaEquipment te : stationInfo.getequipments()) {
                for (TeslaDataPoint teslaPoint : te.getDatapoints()) {
                    setMappingTableRow(teslaPoint, allRows, showAllTesla);
                }
            }
        }

        mappingTableRows = new ArrayList<>();
        for (MappingTableRow tableRow : allRows) {

            if (selectedSid.compareTo("All") == 0 || tableRow.getEdsionSid().compareTo(selectedSid) == 0 || tableRow.getEdsionSid().compareTo("?") == 0) {
                mappingTableRows.add(tableRow);
            }
        }
        
        for (MappingTableRow tableRow : mappingTableRows) {
            
            if( tableRow.getEdsionSid().contentEquals("?") && tableRow.getTeslaID().contentEquals("?") ){
                tableRow.setMapStatus(EnumMapStatus.NoInfo);
            }
            
            if( tableRow.getEdsionSid().contentEquals("?") && !tableRow.getTeslaID().contentEquals("?") ){
                tableRow.setMapStatus(EnumMapStatus.NoEdisonInfo);
            }
                        
            if( !tableRow.getEdsionSid().contentEquals("?") && tableRow.getTeslaID().contentEquals("?") ){
                tableRow.setMapStatus(EnumMapStatus.NoTeslaInfo);
            }
                                    
            if( !tableRow.getEdsionSid().contentEquals("?") && !tableRow.getTeslaID().contentEquals("?") ){
                tableRow.setMapStatus(EnumMapStatus.Mapped);
            }
            
        }

    }

    private boolean isEdisonGarbage(DatapointsAndMetadataResponse edisonPoint) {

        String filter = ".*_COV$";

        Pattern r = Pattern.compile(filter);
        Matcher m = r.matcher(edisonPoint.getName());
        if (m.find()) {
            return true;
        }

        return false;
    }

    private void setMappingTableRow(TeslaDataPoint teslaPoint, List<MappingTableRow> allRows, boolean showAllTesla) {

        for (MappingTableRow mtr : allRows) {
            if (mtr.getEdsionShortName().compareToIgnoreCase(teslaPoint.getShortName()) == 0) {
                mtr.setTeslaName(teslaPoint.getShortName());
                mtr.setTeslaID(teslaPoint.getId());
                mtr.setTeslaType(teslaPoint.getPointType());
                return;
            }
        }

        if (showAllTesla ){ //&& teslaPoint.getPointType().contentEquals("raw")) {
            MappingTableRow row = new MappingTableRow();
            row.setTeslaName(teslaPoint.getShortName());
            row.setTeslaID(teslaPoint.getId());
            row.setTeslaType(teslaPoint.getPointType());
            allRows.add(row);
        }

    }

    public MappingTableRow getRow(int modelNumber) {
        return mappingTableRows.get(modelNumber);
    }
    
    public List<MappingTableRow> getMappedRows(){
        List<MappingTableRow> mappedRows = new ArrayList<>();
        for( MappingTableRow row : mappingTableRows ){ 
            if( row.getMapStatus() == EnumMapStatus.Mapped && row.getTeslaType().contentEquals("raw")){
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
        return EnumDatpointsTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumDatpointsTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object val = "?";

        EnumDatpointsTableColumns enumCol = EnumDatpointsTableColumns.getColumnFromColumnNumber(columnIndex);

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
