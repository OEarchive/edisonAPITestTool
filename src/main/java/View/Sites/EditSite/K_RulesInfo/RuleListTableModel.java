
package View.Sites.EditSite.K_RulesInfo;

import Model.DataModels.Graph.RuleInRulesResponse;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class RuleListTableModel extends AbstractTableModel {

    private final List<RuleInRulesResponse> ruleList;

    public RuleListTableModel(List<RuleInRulesResponse> ruleList) {
        super();

        this.ruleList = ruleList;

    }
    
    public RuleInRulesResponse getRuleFromTable( int row ){
        return ruleList.get(row);
    }

    @Override
    public int getRowCount() {
        return ruleList.size();
    }

    public String getColumnName(int col) {

        return EnumRulesListTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumRulesListTableColumns.getColumnNames().size(); 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        EnumRulesListTableColumns colEnum = EnumRulesListTableColumns.getColumnFromColumnNumber(columnIndex);
        
        RuleInRulesResponse rule = ruleList.get( rowIndex);

        Object val = "?";


            switch (colEnum) {

                case TableIndex:
                    val = rowIndex;
                    break;
                    
                case  RuleId:
                    val = rule.getRuleId();
                    break;

                case RuleName:
                    val = rule.getName();
                    break;

                case RuleOwner:
                    val = rule.getOwner();
                    break;
                    
                case TimeStamp:
                    val = "ts"; //rule.getDefinition().getConfiguration().
                    break;

                default:
                    val = "?";
            }

        return val;

    }
    
}
