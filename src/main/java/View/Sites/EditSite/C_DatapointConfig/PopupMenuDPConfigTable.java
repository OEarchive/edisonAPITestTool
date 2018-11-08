package View.Sites.EditSite.C_DatapointConfig;

import Controller.OptiCxAPIController;
import Model.DataModels.Live.Subscriptions.CreateSubscriptionRequest;
import Model.DataModels.Sites.SiteDPConfig.SiteDPConfigPoint;
import Model.RestClient.RRObj;
import Model.RestClient.RequestsResponses;
import View.RequestResponse.RRTableModel;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class PopupMenuDPConfigTable extends JPopupMenu {

    public PopupMenuDPConfigTable(java.awt.event.MouseEvent evt, final OptiCxAPIController controller, final String siteSid, final JTable table) {

        JMenuItem item = new JMenuItem("Copy");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRowIndicies = table.getSelectedRows();

                int numrows = selectedRowIndicies.length;
                int numcols = table.getColumnCount();

                StringBuilder sbf = new StringBuilder();

                for (int i = 0; i < table.getColumnCount(); i++) {
                    sbf.append(table.getColumnName(i));
                    sbf.append("\t");
                }

                sbf.append("\n");

                for (int i = 0; i < numrows; i++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {

                        Object value = table.getValueAt(selectedRowIndicies[i], j);

                        sbf.append(value);

                        if (j < numcols - 1) {
                            sbf.append("\t");
                        }
                    }
                    sbf.append("\n");
                }

                Toolkit tk = Toolkit.getDefaultToolkit();
                Clipboard cb = tk.getSystemClipboard();
                StringSelection s = new StringSelection(sbf.toString());
                cb.setContents(s, null);
            }
        });

        this.add(item);

        JMenuItem selectAllItem = new JMenuItem("Select All");
        selectAllItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                table.selectAll();
            }

        });
        this.add(selectAllItem);

        JMenuItem liveDataRequestStatement = new JMenuItem("Create Live Data Request");
        liveDataRequestStatement.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (table.getSelectedRows().length > 0) {
                    DPConfigTableModel model = (DPConfigTableModel) table.getModel();

                    Map<String,List<String>> datapoints = new HashMap<>();

                    int[] selectedRowIndicies = table.getSelectedRows();
                    for (int row : selectedRowIndicies) {
                        int modelNumber = table.convertRowIndexToModel(row);
                        SiteDPConfigPoint siteDatapoint = model.getSiteDatapoint(modelNumber);
                        
                        if( !datapoints.containsKey( siteSid)){
                            datapoints.put( siteSid, new ArrayList<String>());
                        }                 
                        datapoints.get(siteSid).add(siteDatapoint.getStationPointName());
                    }
                    
                    DateTime ts = DateTime.now();
                    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
                    String timeStampString = ts.toString(fmt) + ".000Z";
             
                    CreateSubscriptionRequest req = new CreateSubscriptionRequest( timeStampString, datapoints );

                    controller.postNewSubscription(req);

                }
            }
        });
        this.add(liveDataRequestStatement);

        this.show(evt.getComponent(), evt.getX(), evt.getY());

    }
}
