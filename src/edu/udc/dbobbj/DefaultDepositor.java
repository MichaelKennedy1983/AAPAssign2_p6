package edu.udc.dbobbj;

import edu.udc.bank.Depositor;
import edu.udc.bank.PhoneInterface;

/**
 * Info about the person behind the bank account.
 *
 * @author mkenn
 */
public class DefaultDepositor implements Depositor {
    private String personID;
    private String streetAddr;
    private String postalCode;
    private PhoneInterface[] phones;

    public DefaultDepositor() {
        personID = null;
        streetAddr = null;
        postalCode = null;
        phones = null;
    }

    public DefaultDepositor(String personID,
                            String streetAddr,
                            String postalCode,
                            PhoneInterface[] phones) {
        this.personID = personID;
        this.streetAddr = streetAddr;
        this.postalCode = postalCode;
        this.phones = phones;
    }

    @Override
    public String getPersonID() {
        return personID;
    }

    @Override
    public String getStreetAddr() {
        return streetAddr;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public PhoneInterface[] getPhones() {
        return phones;
    }
}
