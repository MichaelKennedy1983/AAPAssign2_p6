package edu.udc;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Adds a little padding to table cells
 *
 * @author mkenn
 */
public class AccountsTableCellRender extends DefaultTableCellRenderer {
        private Border padding;

    public AccountsTableCellRender(int horizontalPadding) {
        super();
        padding = BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));

        return this;
    }
}
