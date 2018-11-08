
package View.Sites.NewSite.Equipment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class PopUpMenuPumpTables extends JPopupMenu {

    public PopUpMenuPumpTables(java.awt.event.MouseEvent evt, final JTable table) {

        JMenuItem item = new JMenuItem("Delete");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRowIndicies = table.getSelectedRows();
                if (selectedRowIndicies != null && selectedRowIndicies.length > 0) {
                    PumpTableModel mod = (PumpTableModel) table.getModel();
                    mod.removeRow(selectedRowIndicies[0]);
                }

            }
        });
        this.add(item);

        JMenuItem selectAllItem = new JMenuItem("Add");
        selectAllItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PumpTableModel mod = (PumpTableModel) table.getModel();
                mod.addBlankRow();
            }

        });
        this.add(selectAllItem);

        this.show(evt.getComponent(), evt.getX(), evt.getY());

    }
}
