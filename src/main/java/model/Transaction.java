package model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Maral Khojasteh
 */
public class Transaction implements Serializable {

    private String id;
    private TransactionType type;
    private BigDecimal amount;
    private String depositId;
    private String resultMessage;
    private BigDecimal depositBalance;

    public BigDecimal getDepositBalance() {
        return depositBalance;
    }
    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }
    public String getResultMessage() {
        return resultMessage;
    }
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

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
