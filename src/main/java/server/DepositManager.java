package server;

import model.Deposit;
import model.Transaction;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class DepositManager {

    private static DepositManager instance;
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

    public void process(Transaction transaction){

    }
}
