package server;

import model.Deposit;
import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maral Khojasteh
 */
public class DepositManager {

    private static DepositManager instance = new DepositManager();
    private HashMap<String, Deposit> depositsMap;
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

    public Transaction process(Transaction transaction){
        String depositId = transaction.getDepositId();
        Deposit deposit = depositsMap.get(depositId);
        String message = "";
        BigDecimal initialBalance = deposit.getInitialBalance();
        BigDecimal depositBalance;
        if (deposit == null){
            message = "Deposit with id: " + transaction.getDepositId() + " is not found";
            logger.log(Level.WARNING, "Deposit with id: {0} is not found" , new Object[]{transaction.getDepositId()});
        } else {
            synchronized (deposit){
                if (transaction.getAmount().compareTo(BigDecimal.ZERO) == -1){
                    message = "Amount is negative";
                    logger.log(Level.WARNING, "Amount is negative");
                }
                else {
                    if (transaction.getType() == TransactionType.DEPOSIT){
                        depositBalance = deposit.getInitialBalance().add(transaction.getAmount());
                        if(depositBalance.compareTo(deposit.getUpperBound()) == 1){
                            message =  "Initial balance become bigger than upper bound";
                            logger.log(Level.WARNING, "Initial balance become bigger than upper bound");
                        }
                        else{
                            message =  "Deposit is done";
                            logger.log(Level.INFO,  "Deposit is done");
                            initialBalance = depositBalance;
                            deposit.setInitialBalance(depositBalance);
                            //update deposit
                            ServerConfig serverConfig = new ServerConfig();
                            serverConfig.update("src/main/resources/core.json", transaction.getDepositId(), depositBalance);

                        }
                    }
                    else if(transaction.getType() == TransactionType.WITHDRAW){
                        depositBalance = deposit.getInitialBalance().subtract(transaction.getAmount());
                        if(depositBalance.compareTo(BigDecimal.ZERO) == -1){
                            message =  "Initial balance is not enough";
                            logger.log(Level.WARNING,  "Initial balance is not enough");
                        }
                        else{
                            message =  "Withdraw is done";
                            logger.log(Level.INFO,  "Withdraw is done");
                            initialBalance = depositBalance;
                            deposit.setInitialBalance(depositBalance);
                            //update deposit
                            ServerConfig serverConfig = new ServerConfig();
                            serverConfig.update("src/main/resources/core.json", transaction.getDepositId(), depositBalance);
                        }
                    }
                    else{
                        message = "Transaction type " + transaction.getType() + " is not valid";
                        logger.log(Level.WARNING, "Transaction type {0} is not valid" , new Object[]{transaction.getType()});
                    }
                }
            }
        }
        transaction.setResultMessage(message);
        transaction.setDepositBalance(initialBalance);
        return transaction;
    }
}
