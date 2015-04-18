package model;

import java.math.BigDecimal;

/**
 *@author Maral Khojasteh
 */
public class Deposit {

    private String id;
    private String customerName;
    private BigDecimal balance;
    private BigDecimal upperBound;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }
}
