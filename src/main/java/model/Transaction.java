package model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Transaction implements Serializable {

    private String id;
    private TransactionType type;
    private BigDecimal amount;
    private String depositId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }
}
