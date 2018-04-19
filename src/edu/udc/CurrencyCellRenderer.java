package edu.udc;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Formats doubles representing money for tables.
 *
 * @author mkenn
 */
public class CurrencyCellRenderer extends DefaultTableCellRenderer {
    private NumberFormat format;
    private Border padding;

    public CurrencyCellRenderer(int horizontalPadding) {
        super();
        setHorizontalAlignment(RIGHT);
        padding = BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding);
    }

    @Override
    public void setValue(Object value) {
        if (format == null) {
            //format = new DecimalFormat("#,##0.00; (#,##0.00)");
            format = NumberFormat.getCurrencyInstance();
        }

        if ((double)value < 0) {
            setText(format.format(value));
        }
        else {
            setText(format.format(value) + " ");
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        if (table == null) {
            return this;
        }

        JTable.DropLocation dropLocation = table.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsertRow()
                && !dropLocation.isInsertColumn()
                && dropLocation.getRow() == row
                && dropLocation.getColumn() == column) {
            isSelected = true;
        }

        if (isSelected) {
            super.setForeground((double)value < 0 ? Color.RED
                    : Color.BLACK);
            super.setBackground(table.getSelectionBackground());
        } else {
            Color background = table.getBackground();
            if (background == null || background instanceof javax.swing.plaf.UIResource) {
                Color alternateColor = UIManager.getColor("Table.alternateRowColor");
                if (alternateColor != null && row % 2 != 0) {
                    background = alternateColor;
                }
            }
            super.setForeground((double)value < 0 ? Color.RED
                    : Color.BLACK);
            super.setBackground(background);
        }

        setFont(table.getFont());

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = UIManager.getColor("Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
            }
        } else {
            setBorder(UIManager.getBorder("Table.cellNoFocusBorder"));
        }
        setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));

        setValue(value);

        return this;
    }
}
