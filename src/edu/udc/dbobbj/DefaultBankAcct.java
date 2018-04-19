package edu.udc.dbobbj;

import edu.udc.bank.AcctType;
import edu.udc.bank.BankAcctInterface;
import edu.udc.bank.Depositor;
import java.util.Date;

/**
 *  A representation of a Bank Account
 *
 * @author Michael Kennedy and Melaku Bogale
 */
public class DefaultBankAcct implements BankAcctInterface {
    private String uniqueID;
    private String acctHolder;
    private Depositor depositor;
    private AcctType acctType;
    private double acctValue;
    private Date dateOpened;
    private String specialInfo;

    private static int uniqueIDSeed;

    static {
        uniqueIDSeed = 100000;
    }

    public DefaultBankAcct() {
        uniqueID = ((Integer)(++uniqueIDSeed)).toString();
    }

    public DefaultBankAcct(String acctHolder,
                           Depositor depositor,
                           AcctType acctType,
                           double acctValue,
                           Date dateOpend,
                           String specialInfo) {
        uniqueID = ((Integer)(++uniqueIDSeed)).toString();
        this.acctHolder = acctHolder;
        this.depositor = depositor;
        this.acctType = acctType;
        this.acctValue = acctValue;
        this.dateOpened = dateOpend;
        this.specialInfo = specialInfo;
    }

    public DefaultBankAcct(String uniqueID,
                           String acctHolder,
                           Depositor depositor,
                           AcctType acctType,
                           double acctValue,
                           Date dateOpend,
                           String specialInfo) {
        this.uniqueID = uniqueID;
        this.acctHolder = acctHolder;
        this.depositor = depositor;
        this.acctType = acctType;
        this.acctValue = acctValue;
        this.dateOpened = dateOpend;
        this.specialInfo = specialInfo;
    }

    public static AcctType stringToAcctType(String type) {
        switch (type) {
            case "ch":
                return AcctType.ACCT_CHECKING;
            case "sav":
                return AcctType.ACCT_SAVINGS;
            case "cd":
                return AcctType.ACCT_CD;
            default:
                return AcctType.ACCT_BUS;
        }
    }

    // Accessors
    @Override
    public String getUniqueID() {
        return uniqueID;
    }

    @Override
    public String getAcctHolder() {
        return acctHolder;
    }

    @Override
    public Depositor getDepositor() {
        return depositor;
    }

    @Override
    public AcctType getAcctType() {
        return acctType;
    }

    @Override
    public double getAcctValue() {
        return acctValue;
    }

    @Override
    public Date getDateOpened() {
        return dateOpened;
    }

    @Override
    public String getSpecialInfo() {
        return specialInfo;
    }
}
