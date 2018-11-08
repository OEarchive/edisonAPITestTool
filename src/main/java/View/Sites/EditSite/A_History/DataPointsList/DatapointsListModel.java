
package View.Sites.EditSite.A_History.DataPointsList;

import java.util.List;
import javax.swing.AbstractListModel;


public class DatapointsListModel extends AbstractListModel {
    
    private List<String> dataPointNames;
    
    public DatapointsListModel( List<String> dataPointNames ){
        this.dataPointNames = dataPointNames;
    }

    @Override
    public int getSize() {
        return dataPointNames.size();
    }

    @Override
    public Object getElementAt(int index) {
        try{
            String k = dataPointNames.get(index);
        }
        catch( Exception ex ){
            System.out.println(index);
        }
        
        return dataPointNames.get(index);
    }
}
