
package View.Sites.EditSite.E_Equipment.EditEquipment;

import Model.DataModels.Graph.EdisonNode;
import Model.DataModels.Graph.GetChildrenResponse;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class ChildrenTableModel extends AbstractTableModel {

    private List<EdisonNode> edisonNodes;

    public ChildrenTableModel(GetChildrenResponse children) {
        super();
        
        this.edisonNodes = children.getNodes();
        
        if( edisonNodes == null ){
            edisonNodes = new ArrayList<EdisonNode>();
        }
    }

    @Override
    public int getRowCount() {
        return edisonNodes.size();
    }

    @Override
    public String getColumnName(int col) {
        return EnumChildrenTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumChildrenTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EdisonNode edisonNode = edisonNodes.get(rowIndex);

        EnumChildrenTableColumns colEnum = EnumChildrenTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Name:
                val = edisonNode.getName();
                break;
            case Sid:
                val = edisonNode.getSid();
                break;
            case nodeType:
                val = edisonNode.getNodeType();
                break;
        }
        return val;
    }

}

