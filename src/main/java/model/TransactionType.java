package model;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public enum  TransactionType{

    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private String type;

    private TransactionType(String t) {
        type = t;
    }

    public String geTransactionType() {
        return type;
    }
}
