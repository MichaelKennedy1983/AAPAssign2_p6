package edu.udc;

import edu.udc.bank.BankAcctInterface;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *  Handles the data for the Accounts table.
 *
 * @author Michael Kennedy and Melaku Bogale
 */
public class AccountsTableModel extends AbstractTableModel {
    private List<BankAcctInterface> data;
    private final String[] COLNAMES = {"Acct #", "Balance", "Holder", "Address", "Date Opened"};

    public AccountsTableModel(List<BankAcctInterface> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLNAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLNAMES[columnIndex];
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        if (data == null) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return data.get(rowIndex).getUniqueID();
            case 1:
                return data.get(rowIndex).getAcctValue();
            case 2:
                return data.get(rowIndex).getAcctHolder();
            case 3:
                return data.get(rowIndex).getDepositor().getStreetAddr();
            case 4:
                return data.get(rowIndex).getDateOpened();
            default:
                return "Bad data";
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    public List<BankAcctInterface> getData() {
        return data;
    }

    public void setData(List<BankAcctInterface> data) {
        this.data = data;
        fireTableDataChanged();
    }
}
