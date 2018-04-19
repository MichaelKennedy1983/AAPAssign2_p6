package edu.udc;

import edu.udc.bank.AcctType;
import edu.udc.dbobbj.BankServer;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

/**
 * Main Class.
 * Contains the core of the GUI and the main method.
 *
 * @author Michael Kennedy and Melaku Bogale
 */
public class BankAcctsGuiDB extends JFrame {
    private final int cellPadding = 3;
    private String frameTitle = "Bank Accounts";
    private String dbURL = "db/bank_db";

    // Containers and Components
    private JPanel pnlMain = new JPanel();
    private JScrollPane scpnAccounts = new JScrollPane();
    private JTable tblAccounts = new JTable();
 
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
    
    private static void createAndShowGUI() {
        SwingUtilities.invokeLater(BankAcctsGuiDB::new);
    }

    private void initData() {
        bankServer = BankServer.getInstance();
        bankServer.connect(dbURL);
        atmAccountsTable = new AccountsTableModel(bankServer.getAllAccts());
    }

    private void initComponents() {
        tblAccounts.setModel(atmAccountsTable);
        rowSorter = new TableRowSorter<>(atmAccountsTable);
        initRowSorter();
        tblAccounts.setRowSorter(rowSorter);
        tblAccounts.setDefaultRenderer(String.class, new AccountsTableCellRender(cellPadding));
        tblAccounts.setDefaultRenderer(AcctType.class, new AcctTypeCellRenderer(cellPadding));
        tblAccounts.getColumnModel().getColumn(1).setCellRenderer(new CurrencyCellRenderer(cellPadding));
        tblAccounts.getColumnModel().getColumn(4).setCellRenderer(new DateCellRenderer(cellPadding));
        //tblAccounts.getColumnModel().getColumn(2).setCellEditor(new HolderCellEditor(new JTextField(), cellPadding));
        scpnAccounts.add(tblAccounts);
        scpnAccounts.setViewportView(tblAccounts);

        // Main Layout
        GroupLayout mainLayout = new GroupLayout(pnlMain);
        pnlMain.setLayout(mainLayout);

        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scpnAccounts)
        );

        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
            .addComponent(scpnAccounts)
        );

        this.setContentPane(pnlMain);
        this.setSize(800, 600);
        this.setTitle(frameTitle);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initControls() {

    }

    /**
     * Do not call until after rowSorter is initialized
     */
    private void initRowSorter() {
        int valueColIndex = tblAccounts.convertColumnIndexToView(1);
        int holderNameColIndex = tblAccounts.convertColumnIndexToView(2);
        
        List<RowSorter.SortKey> sortList = new ArrayList<>();
        sortList.add(new RowSorter.SortKey(valueColIndex, SortOrder.DESCENDING));
        sortList.add(new RowSorter.SortKey(holderNameColIndex, SortOrder.ASCENDING));

        rowSorter.setSortKeys(sortList);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        createAndShowGUI();
    }
    
    // Listeners

}
