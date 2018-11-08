
package View.Sites;

import Model.DataModels.Sites.Site;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class SiteListTableModel extends AbstractTableModel {

    private final List<Site> sites;

    public SiteListTableModel(List<Site> sites) {
        super();

        this.sites = sites;

    }

    @Override
    public int getRowCount() {
        return sites.size();
    }

    public String getColumnName(int col) {
        return EnumSitesListTableColumns.getColumnFromColumnNumber(col).getFriendlyName();
    }

    @Override
    public int getColumnCount() {
        return EnumSitesListTableColumns.getColumnNames().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Site site = this.sites.get(rowIndex);

        EnumSitesListTableColumns colEnum = EnumSitesListTableColumns.getColumnFromColumnNumber(columnIndex);

        Object val = "?";
        switch (colEnum) {
            case Sid:
                val = site.getSid();
                break;
            case Name:
                val = site.getName();
                break;
            case Product:
                val = site.getProduct();
                break;

        }
        
        return val;
    }
    
    public Site getSiteAtIndex( int modelIndex ){
        return sites.get(modelIndex);
    }

}