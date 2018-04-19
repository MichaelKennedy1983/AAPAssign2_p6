package edu.udc;

import edu.udc.bank.AcctType;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Formats AcctType for tables.
 *
 * @author mkenn
 */
public class AcctTypeCellRenderer extends DefaultTableCellRenderer {
    Border padding;

    public AcctTypeCellRenderer(int horizontalPadding) {
        super();
        padding = BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding);
    }

    @Override
    protected void setValue(Object value) {
        switch ((AcctType)value) {
            case ACCT_BUS:
                setText("Business");
                break;
            case ACCT_CC:
                setText("Credit Card");
                break;
            case ACCT_CD:
                setText("Certificate of Deposit");
                break;
            case ACCT_CHECKING:
                setText("Checking");
                break;
            case ACCT_LOAN:
                setText("Loan");
                break;
            case ACCT_SAVINGS:
                setText("Savings");
                break;
            default:
                setText("Bad Data");
                break;
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));

        return this;
    }
}
