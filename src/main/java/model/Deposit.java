package model;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Maral ito
 * Date: 4/10/15
 * Time: 5:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Deposit {

    private String id;
    private String customerName;
    private BigDecimal initialBalance;
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

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }
}
