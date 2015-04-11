package server;

import model.Deposit;
import model.Transaction;
import model.TransactionType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class DepositManager {

    private static DepositManager instance = new DepositManager();
    private HashMap<String, Deposit> depositsMap;

    private DepositManager() {
    }

    public static DepositManager getInstance(){
        return instance;
    }

    public void  loadInitialData(List<Deposit> deposits){
         depositsMap = new HashMap<String, Deposit>();
         for(Deposit deposit: deposits){
             depositsMap.put(deposit.getId(), deposit);
         }
    }

    public Transaction process(Transaction transaction){
        String depositId = transaction.getDepositId();
        Deposit deposit = depositsMap.get(depositId);
        String message = "";
        BigDecimal initialBalance = deposit.getInitialBalance();
        BigDecimal depositBalance;
        if(deposit == null){
            message = "Deposit with id: " + transaction.getDepositId() + " is not found";
        }
        else{
            synchronized (deposit){
                if(transaction.getAmount().compareTo(BigDecimal.ZERO) == -1){
                    message = "Amount is negative";
                }
                else{
                    if(transaction.getType() == TransactionType.DEPOSIT){
                        depositBalance = deposit.getInitialBalance().add(transaction.getAmount());
                        if(depositBalance.compareTo(deposit.getUpperBound()) == 1){
                            message =  "Initial balance become bigger than upper bound";
                        }
                        else{
                            message =  "Deposit is done";
                            initialBalance = depositBalance;
                            deposit.setInitialBalance(depositBalance);
                            //update deposit
                            ServerConfig serverConfig = new ServerConfig();
                            serverConfig.update("core.json", transaction.getDepositId(), depositBalance);
                        }
                    }
                    else if(transaction.getType() == TransactionType.WITHDRAW){
                        depositBalance = deposit.getInitialBalance().subtract(transaction.getAmount());
                        if(depositBalance.compareTo(BigDecimal.ZERO) == -1){
                            message =  "Initial balance is not enough";
                        }
                        else{
                            message =  "Withdraw is done";
                            initialBalance = depositBalance;
                            deposit.setInitialBalance(depositBalance);
                            //update deposit
                            ServerConfig serverConfig = new ServerConfig();
                            serverConfig.update("core.json", transaction.getDepositId(), depositBalance);
                        }
                    }
                    else{
                        message = "Transaction type " + transaction.getType() + " is not valid";
                    }
                }
            }
        }
        transaction.setResultMessage(message);
        transaction.setDepositBalance(initialBalance);
        return transaction;
    }
}
