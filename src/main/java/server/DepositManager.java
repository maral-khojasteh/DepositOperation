package server;

import model.Deposit;
import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maral Khojasteh
 */
public class DepositManager {

    private static DepositManager instance = new DepositManager();
    private Map<String, Deposit> depositsMap;
    private static Logger logger = Logger.getLogger(ServerConfig.class.getName());

    private DepositManager() {
    }

    public static DepositManager getInstance(){
        return instance;
    }

    public void  loadInitialData(List<Deposit> deposits){
         depositsMap = new HashMap<String, Deposit>();
         for (Deposit deposit: deposits){
             depositsMap.put(deposit.getId(), deposit);
         }
         logger.log(Level.INFO, "Deposits are loaded...");
    }

    public Transaction process(Transaction transaction) {

        String depositId = transaction.getDepositId();
        Deposit deposit = depositsMap.get(depositId);
        if (deposit == null) {
            String message = "Deposit with id: " + transaction.getDepositId() + " is not found";
            logger.log(Level.WARNING, "Deposit with id: {0} is not found", new Object[]{transaction.getDepositId()});
            transaction.setResultMessage(message);
            return transaction;
        }
        synchronized (deposit){
            ResultCode resultCode = applyDeposit(deposit, transaction);
            transaction.setResultMessage(resultCode.getMessage());
            Level logLevel = resultCode.isFailed() ? Level.WARNING : Level.INFO;
            logger.log(logLevel, resultCode.getMessage());
            transaction.setDepositBalance(deposit.getBalance());
            return transaction;
        }
    }

    private ResultCode applyDeposit(Deposit deposit, Transaction transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0){
            return ResultCode.TRANSACTION_IS_NEGATIVE;
        }
        BigDecimal result;
        if (transaction.getType() == TransactionType.DEPOSIT) {
            result = deposit.getBalance().add(transaction.getAmount());
            if (result.compareTo(deposit.getUpperBound()) == 1) {
                return ResultCode.UPPER_BOUND_EXCEEDED;
            }
        } else if (transaction.getType() == TransactionType.WITHDRAW) {
            result = deposit.getBalance().subtract(transaction.getAmount());
            if (result.compareTo(BigDecimal.ZERO) == -1) {
                return ResultCode.NOT_ENOUGH_BALANCE;
            }
        } else {
            return ResultCode.INVALID_TRANSACTION_TYPE;
        }
        deposit.setBalance(result);
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.update("src/main/resources/core.json", transaction.getDepositId(), result);
        return ResultCode.TRANSACTION_IS_DONE;
    }


//    public Transaction process2(Transaction transaction, HashMap<String, Deposit> depositsMap){
//        String depositId = transaction.getDepositId();
//        Deposit deposit = depositsMap.get(depositId);
//        BigDecimal initialBalance = deposit.getBalance();
//        BigDecimal depositBalance;
//        String message = "";
//        if (deposit == null){
//            // deposit is not valid
//            message = "Deposit with id: " + transaction.getDepositId() + " is not found";
//            logger.log(Level.WARNING, "Deposit with id: {0} is not found" , new Object[]{transaction.getDepositId()});
//        } else {
//            // deposit is valid; now apply it!
//            synchronized (deposit){
//                if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0){
//                    message = "Amount is negative";
//                    logger.log(Level.WARNING, "Amount is negative");
//                    initialBalance = deposit.getBalance();
//                }
//                else {
//                    if (transaction.getType() == TransactionType.DEPOSIT){
//                        depositBalance = deposit.getBalance().add(transaction.getAmount());
//                        if(depositBalance.compareTo(deposit.getUpperBound()) == 1){
//                            message =  "Initial balance become bigger than upper bound";
//                            logger.log(Level.WARNING, "Initial balance become bigger than upper bound");
//                            initialBalance = deposit.getBalance();
//                        }
//                        else{
//                            message =  "Deposit is done";
//                            logger.log(Level.INFO,  "Deposit is done");
//                            initialBalance = depositBalance;
//                            deposit.setBalance(depositBalance);
//                            //update deposit
//                            ServerConfig serverConfig = new ServerConfig();
//                            serverConfig.update("src/main/resources/core.json", transaction.getDepositId(), depositBalance);
//
//                        }
//                    }
//                    else if(transaction.getType() == TransactionType.WITHDRAW){
//                        depositBalance = deposit.getBalance().subtract(transaction.getAmount());
//                        if(depositBalance.compareTo(BigDecimal.ZERO) == -1){
//                            message =  "Initial balance is not enough";
//                            logger.log(Level.WARNING,  "Initial balance is not enough");
//                            initialBalance = deposit.getBalance();
//                        }
//                        else{
//                            message =  "Withdraw is done";
//                            logger.log(Level.INFO,  "Withdraw is done");
//                            initialBalance = depositBalance;
//                            deposit.setBalance(depositBalance);
//                            //update deposit
//                            ServerConfig serverConfig = new ServerConfig();
//                            serverConfig.update("src/main/resources/core.json", transaction.getDepositId(), depositBalance);
//                        }
//                    }
//                    else{
//                        message = "Transaction type " + transaction.getType() + " is not valid";
//                        logger.log(Level.WARNING, "Transaction type {0} is not valid" , new Object[]{transaction.getType()});
//                        initialBalance = deposit.getBalance();
//                    }
//                }
//            }
//        }
//        transaction.setResultMessage(message);
//        transaction.setDepositBalance(initialBalance);
//        return transaction;
//    }
}
