
package View.CommonLibs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class PopupMenuForMapTables extends JPopupMenu {

    public PopupMenuForMapTables(java.awt.event.MouseEvent evt, final JTable table) {

        JMenuItem item = new JMenuItem("Delete");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRowIndicies = table.getSelectedRows();
                if (selectedRowIndicies != null && selectedRowIndicies.length > 0) {
                    MapTableModel mod = (MapTableModel) table.getModel();
                    mod.removeRow(selectedRowIndicies[0]);
                }

            }
        });
        this.add(item);

        JMenuItem selectAllItem = new JMenuItem("Add");
        selectAllItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MapTableModel mod = (MapTableModel) table.getModel();
                mod.addBlankRow();
            }

        });
        this.add(selectAllItem);

        this.show(evt.getComponent(), evt.getX(), evt.getY());

    }
}

