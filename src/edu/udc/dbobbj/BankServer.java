package edu.udc.dbobbj;

import edu.udc.bank.AcctType;
import edu.udc.bank.BankAcctInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Gets the data from the database to the program.
 *
 * @author mkenn
 */
public class BankServer {
    private Connection connection = null;
    private static BankServer instance;

    private String acctQuery = "SELECT ba_id, ba_holder, ba_value, ba_type, ba_dtopen, ba_special_info," +
            " dp_id, dp_addr, dp_postal_code" +
            " FROM bankacct" +
            " LEFT JOIN depositor ON ba_id = dp_ba_id";

    private BankServer() {

    }

    public static BankServer getInstance() {
        if (instance == null) instance = new BankServer();

        return instance;
    }

    public void connect(String dbUrl) {
        if (connection != null) return;

        try {
            if (dbUrl == null) {
                throw new SQLException("Bad database url.");
            }

            String dbFullUrl = "jdbc:derby:" + dbUrl;

            if (dbFullUrl.endsWith(":")) {
                throw new SQLException("Bad database url.");
            }

            connection = DriverManager.getConnection(dbFullUrl);
            connection.setAutoCommit(false);
        } catch (SQLException sexc) {
            System.err.println(sexc);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.rollback();
                connection.close();
            }
        } catch (SQLException sexc) {
            System.err.println(sexc);
        }
    }

    public void commit() {
        try {
            if (connection != null) {
                connection.commit();
            }
        } catch (SQLException sexc) {
            System.err.println(sexc);
        }
    }

    public ArrayList<BankAcctInterface> getAllAccts() {
        Statement st = null;
        ResultSet rs = null;

        ArrayList<BankAcctInterface> accounts = new ArrayList<>();

        try {
            st = connection.createStatement();
            rs = st.executeQuery(acctQuery);

            DefaultBankAcct bankAcct = null;
            DefaultDepositor depositor = null;
            while (rs.next()) {
                depositor = new DefaultDepositor(
                        rs.getString("dp_id"),
                        rs.getString("dp_addr"),
                        rs.getString("dp_postal_code"),
                        null);

                bankAcct = new DefaultBankAcct(
                        rs.getString("ba_id"),
                        rs.getString("ba_holder"),
                        depositor,
                        DefaultBankAcct.stringToAcctType(rs.getString("ba_type")),
                        rs.getDouble("ba_value"),
                        rs.getDate("ba_dtopen"),
                        rs.getString("ba_special_info"));

                accounts.add(bankAcct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return accounts;
    }

    public ArrayList<BankAcctInterface> getSampleAcct() {
        ArrayList<BankAcctInterface> samples = new ArrayList<>();

        BankAcctInterface sampleOne =
                new DefaultBankAcct("Frank",
                        new DefaultDepositor("P001",
                                "123 Fake St.",
                                "20001",
                                null),
                        AcctType.ACCT_CHECKING,
                        1000.55,
                        new GregorianCalendar(1989, 2, 11).getTime(),
                        "");

        BankAcctInterface sampleTwo =
                new DefaultBankAcct("Frannie",
                        new DefaultDepositor("P002",
                                "456 Fake St.",
                                "20001",
                                null),
                        AcctType.ACCT_CD,
                        10234.55,
                        new GregorianCalendar(1983, 2, 26).getTime(),
                        "");

        BankAcctInterface sampleThree =
                new DefaultBankAcct("Paul",
                        new DefaultDepositor("P003",
                                "789 Fake St.",
                                "20001",
                                null),
                        AcctType.ACCT_CC,
                        -134.55,
                        new GregorianCalendar(1991, 4, 30).getTime(),
                        "Always in debt");

        BankAcctInterface sampleFour =
                new DefaultBankAcct("Zoe",
                        new DefaultDepositor("P004",
                                "123 Real St.",
                                "20002",
                                null),
                        AcctType.ACCT_SAVINGS,
                        180950.98,
                        new GregorianCalendar(1987, 5, 30).getTime(),
                        "Hunts big game");

        BankAcctInterface sampleFive =
                new DefaultBankAcct("Frank",
                        new DefaultDepositor("P005",
                                "456 Real St.",
                                "20002",
                                null),
                        AcctType.ACCT_SAVINGS,
                        999.43,
                        new GregorianCalendar(1951, 7, 26).getTime(),
                        "Friendly Frank");

        BankAcctInterface sampleSix =
                new DefaultBankAcct("Bob",
                        new DefaultDepositor("P006",
                                "789 Real St.",
                                "20002",
                                null),
                        AcctType.ACCT_CHECKING,
                        180950.98,
                        new GregorianCalendar(1952, 1, 29).getTime(),
                        "He's really old");

        BankAcctInterface sampleSeven =
                new DefaultBankAcct("Kim",
                        new DefaultDepositor("P007",
                                "123 Limbo Ave.",
                                "20003",
                                null),
                        AcctType.ACCT_LOAN,
                        -59975.00,
                        new GregorianCalendar(1922, 2, 31).getTime(),
                        "");

        samples.add(sampleOne);
        samples.add(sampleTwo);
        samples.add(sampleThree);
        samples.add(sampleFour);
        samples.add(sampleFive);
        samples.add(sampleSix);
        samples.add(sampleSeven);

        return samples;
    }
}
