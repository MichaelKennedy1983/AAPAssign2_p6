package edu.udc;

import java.awt.Component;
import java.text.DateFormat;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Formats dates for tables.
 *
 * @author mkenn
 */
public class DateCellRenderer extends DefaultTableCellRenderer {
    DateFormat format;
    Border padding;

    public DateCellRenderer(int horizontalPadding) {
        super();
        padding = BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding);
    }

    @Override
    public void setValue(Object value) {
        if (format == null) {
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        }

        setText(format.format(value));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));

        return this;
    }
}
