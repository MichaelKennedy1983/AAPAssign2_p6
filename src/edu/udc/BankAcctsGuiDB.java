package edu.udc;

import edu.udc.bank.AcctInterface;
import edu.udc.bank.BankAcctInterface;
import edu.udc.dbobbj.BankServer;
import edu.udc.dbobbj.DefaultBankAcct;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableRowSorter;

/**
 * Main Class.
 * Contains the core of the GUI and the main method.
 *
 * @author Michael Kennedy and Melaku Bogale
 */
public class BankAcctsGuiDB extends JFrame {
    private String frameTitle = "Bank Accounts";
    private String dbURL = "db/bank_db";

    // Containers and Components
    private JPanel pnlMain = new JPanel();
    private JScrollPane scpnAccounts = new JScrollPane();
    private JTable tblAccounts = new JTable();
    private JButton bttnRefresh = new JButton("Refresh");
    private JButton bttnDelete = new JButton("Delete");
    private JButton bttnClose = new JButton("Close");
 
    // Data and Models
    private AccountsTableModel atmAccountsTable;
    private TableRowSorter<AccountsTableModel> rowSorter;
    private BankServer bankServer;

    private BankAcctsGuiDB() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }
        initData();
        initComponents();
        initControls();
    }

    private void initData() {
        bankServer = BankServer.getInstance();
        bankServer.connect(dbURL);
        atmAccountsTable = new AccountsTableModel(bankServer.getAllAccts());
    }

    private void initComponents() {
        tblAccounts.setModel(atmAccountsTable);
        tblAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initRowSorter();
        tblAccounts.setRowSorter(rowSorter);
        tblAccounts.getColumnModel().getColumn(1).setCellRenderer(new CurrencyCellRenderer());
        scpnAccounts.add(tblAccounts);
        scpnAccounts.setViewportView(tblAccounts);

        // Main Layout
        GroupLayout mainLayout = new GroupLayout(pnlMain);
        pnlMain.setLayout(mainLayout);

        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);

        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(scpnAccounts)
            .addGroup(mainLayout.createSequentialGroup()
                .addComponent(bttnRefresh)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                .addComponent(bttnDelete)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                .addComponent(bttnClose)
            )
        );

        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
            .addComponent(scpnAccounts)
            .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(bttnRefresh)
                .addComponent(bttnDelete)
                .addComponent(bttnClose)
            )
        );

        this.setContentPane(pnlMain);
        this.pack();
        this.setTitle(frameTitle);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initControls() {
        bttnRefresh.addActionListener(alRefresh);
        bttnDelete.addActionListener(alDelete);
        bttnClose.addActionListener(alClose);

        tblAccounts.getDefaultEditor(String.class).addCellEditorListener(celHolder);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> bankServer.close()));
    }


    private void initRowSorter() {
        rowSorter = new TableRowSorter<>(atmAccountsTable);

        int valueColIndex = tblAccounts.convertColumnIndexToView(1);
        int holderNameColIndex = tblAccounts.convertColumnIndexToView(2);
        
        List<RowSorter.SortKey> sortList = new ArrayList<>();
        sortList.add(new RowSorter.SortKey(valueColIndex, SortOrder.DESCENDING));
        sortList.add(new RowSorter.SortKey(holderNameColIndex, SortOrder.ASCENDING));

        rowSorter.setSortKeys(sortList);
    }

    private static void createAndShowGUI() {
        SwingUtilities.invokeLater(BankAcctsGuiDB::new);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createAndShowGUI();
    }

    private void refresh() {
        atmAccountsTable.setData(bankServer.getAllAccts());
        atmAccountsTable.fireTableDataChanged();
    }

    private void deleteSelectedRow() {
        if (tblAccounts.getSelectedRow() < 0) {
            return;
        }

        int row = tblAccounts.convertRowIndexToModel(tblAccounts.getSelectedRow());

        BankAcctInterface acct = atmAccountsTable.getData().get(row);

        try {
            int deletedRows = bankServer.removeAcct(acct);

            bankServer.commit();
            JOptionPane.showMessageDialog(this, deletedRows + " rows deleted from database.");
        } catch (SQLException e) {
            bankServer.rollback();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        refresh();
    }

    private void updateSelectedHolderName(String newHolderName) {
        if (tblAccounts.getSelectedRow() < 0) {
            return;
        }

        int row = tblAccounts.convertRowIndexToModel(tblAccounts.getSelectedRow());

        BankAcctInterface acct = atmAccountsTable.getData().get(row);

        try {
            int rowsUpdated = bankServer.updateAcct(acct, newHolderName);
            bankServer.commit();
            atmAccountsTable.fireTableCellUpdated(row, 2);
        } catch (SQLException e) {
            bankServer.rollback();
            e.printStackTrace();
        }
    }

    // Listeners
    private ActionListener alRefresh = (ae) -> refresh();

    private  ActionListener alDelete = (ae) -> deleteSelectedRow();

    private ActionListener alClose = (ae) -> this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    private CellEditorListener celHolder = new CellEditorListener() {
        @Override
        public void editingStopped(ChangeEvent e) {
            updateSelectedHolderName((String)((CellEditor)e.getSource()).getCellEditorValue());
        }

        @Override
        public void editingCanceled(ChangeEvent e) {

        }
    };
}
