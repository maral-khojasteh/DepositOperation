package server;

import model.Deposit;

import java.util.List;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class ServerConfig {

    private int port;
    private List<Deposit> deposits;
    private String logFile;

    public void load(String input){
        //TODO: load from json
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
}
