package View.Sites.EditSite.A_History.PushToTesla.MappingTable;

import Model.DataModels.Datapoints.DatapointsAndMetadataResponse;
import Model.DataModels.TeslaModels.TeslaDataPoint;
import Model.DataModels.TeslaModels.TeslaEquipment;
import Model.DataModels.TeslaModels.TeslaStationInfo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DataPointsTableModel extends AbstractTableModel {

    private final List<MappingTableRow> mappingTableRows;

    public DataPointsTableModel(List<DatapointsAndMetadataResponse> listOfMetadata, TeslaStationInfo stationInfo) {
        super();

        mappingTableRows = new ArrayList<>();

        for (DatapointsAndMetadataResponse edisonPoint : listOfMetadata) {
            MappingTableRow row = new MappingTableRow();
            row.setEdisonShortName(edisonPoint.getName());
            row.setEdisonSid(edisonPoint.getSid());
            mappingTableRows.add(row);
        }

        for (TeslaDataPoint teslaPoint : stationInfo.getDatapoints()) {
            setMappingTableRow(teslaPoint);
        }

        for (TeslaEquipment te : stationInfo.getequipments()) {
            for (TeslaDataPoint teslaPoint : te.getDatapoints()) {
                setMappingTableRow(teslaPoint);
            }
        }
    }

    private void setMappingTableRow(TeslaDataPoint teslaPoint) {

        for (MappingTableRow mtr : mappingTableRows) {
            if (mtr.getEdsionShortName().compareToIgnoreCase(teslaPoint.getShortName()) == 0) {
                mtr.setTeslaName(teslaPoint.getShortName());
                mtr.setTeslaID(teslaPoint.getId());
                return;
            }
        }

        MappingTableRow row = new MappingTableRow();
        row.setTeslaName(teslaPoint.getShortName());
        row.setTeslaID(teslaPoint.getId());
        mappingTableRows.add(row);

    }

    public MappingTableRow getRow(int modelNumber) {
        return mappingTableRows.get(modelNumber);
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
            case EdisonName:
                val = dataRow.getEdsionShortName();
                break;
            case EdsionSid:
                val = dataRow.getEdsionSid();
                break;
            case TeslaName:
                val = dataRow.getTeslaName();
                break;
            case TeslaID:
                val = dataRow.getTeslaID();
                break;

        }

        return val;
    }
}
