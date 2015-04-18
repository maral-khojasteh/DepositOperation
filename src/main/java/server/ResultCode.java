package server;

/**
 * @author Maral Khojasteh
 */
public enum ResultCode {

    TRANSACTION_IS_DONE("Transaction is done.", false),
    TRANSACTION_IS_NEGATIVE("Transaction amount is negative!", true),
    NOT_ENOUGH_BALANCE("Not enough credits!", true),
    UPPER_BOUND_EXCEEDED("Upper Bound exceeded", true),
    INVALID_TRANSACTION_TYPE("Invalid transaction type", true);


    private boolean failed;
    private String message;

    private ResultCode(String message, boolean failed){
        this.failed = failed;
        this.message = message;
    }


    public boolean isFailed() {
        return failed;
    }

    public String getMessage() {
        return message;
    }
}
